package com.landongavin.services;

import com.landongavin.entities.MyCharge;
import com.landongavin.entities.Order;
import com.google.firebase.database.*;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Dispute;
import com.stripe.model.Refund;
import org.springframework.stereotype.Service;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    private static FirebaseDatabase database;

    public static void init() {
        Stripe.apiKey = "sk_test_d2dFpCbIu2GxhtDpP240KhNs";
        database = FirebaseDatabase.getInstance();
        setupOrderListener();
        System.out.println("Stripe is init");
    }

    private static void setupOrderListener() {
        DatabaseReference ref = database.getReference("orders/");
        ref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
                requestOrderProcessing(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String prevChildKey) {
                requestOrderProcessing(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) { }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String prevChildKey) { }

            @Override
            public void onCancelled(DatabaseError databaseError){ }
        });
    }

    public static void createDispute(Dispute dispute) {
        try {
            DatabaseReference ref = database.getReference("disputes/");
            Map<String, Object> disputeUpdates = new HashMap<>();
            disputeUpdates.put(dispute.getCharge(), dispute);
            ref.setValueAsync(disputeUpdates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createRefund(Refund refund) {
        try {
            DatabaseReference ref = database.getReference("refunds/");
            Map<String, Object> refundUpdates = new HashMap<>();
            refundUpdates.put(refund.getCharge(), refund);
            ref.setValueAsync(refundUpdates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void changeOrderStatus(String chargeId, String status, @Nullable String message) {
        try {
            DatabaseReference ref = database.getReference("charges/" + chargeId);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MyCharge myCharge = dataSnapshot.getValue(MyCharge.class);
                    DatabaseReference orderRef = database.getReference("orders/" + myCharge.getId() + "/" + myCharge.getOrderId());
                    if(myCharge.getId() != null && myCharge.getOrderId() != null) {
                        Map<String, Object> orderParams = new HashMap<>();
                        orderParams.put("status", status);
                        orderParams.put("orderMessage", message);
                        orderRef.updateChildrenAsync(orderParams);

                        Map<String, Object> params = new HashMap<>();
                        params.put("status", status);

                        ref.updateChildrenAsync(params);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    static void requestOrderProcessing(DataSnapshot dataSnapshot) {
        for(DataSnapshot child : dataSnapshot.getChildren()) {
            try {
                Order order = child.getValue(Order.class);

                if(order.getStatus().equals("processing")) {
                    System.out.println("Attempting to process");

                    Map<String, Object> params = new HashMap<>();
                    params.put("amount", order.getTotal());
                    params.put("currency", "usd");
                    params.put("source", order.getToken().getId());

                    try {
                        Charge charge = Charge.create(params);

                        System.out.println("charge: " + charge.getId());

                        if(dataSnapshot.getKey() != null && child.getKey() != null) {
                            DatabaseReference childref = database.getReference("orders/" + dataSnapshot.getKey() + "/" + child.getKey());
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("status", "processed");
                            childUpdates.put("chargeId", charge.getId());
                            childUpdates.put("amount", charge.getAmount());

                            childref.updateChildrenAsync(childUpdates);

                            DatabaseReference chargeRef = database.getReference("charges/" + charge.getId());
                            Map<String, Object> chargeUpdates = new HashMap<>();
                            chargeUpdates.put("status", "waiting");
                            chargeUpdates.put("id", dataSnapshot.getKey());
                            chargeUpdates.put("orderId", child.getKey());
                            chargeUpdates.put("amount", charge.getAmount());

                            chargeRef.updateChildrenAsync(chargeUpdates);
                            double orderTotal = order.getTotal();
                            double total = (orderTotal / 100);

                            System.out.println("A charge of $" + total + " should have been placed");
                        }
                    }
                    catch( CardException e ) {
                        // Since it's a decline, CardException will be caught
                        System.out.println("status is - " + e.getCode());
                        System.out.println("message is - " + e.getParam());
                        System.out.println("Card error: Payment failure " + e);
                    }
                    catch( InvalidRequestException e ) {
                        // Invalid parameters were supplied to Stripe's API
                        System.out.println("Invalid request: Payment failure: " + e);
                    }
                    catch( AuthenticationException e ) {
                        // Authentication with Stripe's API failed (maybe the API key is wrong)
                        System.out.println("Stripe auth error: Payment failure: " + e);
                    }
                    catch( ApiConnectionException e ) {
                        // Network communication with Stripe failed
                        // TODO - retry?
                        System.out.println("Need to add retry: Payment failure: " + e);
                    }
                    catch( ApiException e ) {
                        System.out.println("API Failure: Payment failure: " + e);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}