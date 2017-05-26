package com.chiefsretro.services;

import com.chiefsretro.entities.MyCharge;
import com.chiefsretro.entities.Order;
import com.google.firebase.database.*;
import com.stripe.Stripe;
import com.stripe.exception.*;
import com.stripe.model.Charge;
import com.stripe.model.Dispute;
import com.stripe.model.Refund;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

    private FirebaseDatabase database;

    public void init() {
        database = FirebaseDatabase.getInstance();
        setupOrderListener();
    }

    @Scheduled(fixedRate=300000, initialDelay=1000)
    public void maintenance() {
        System.out.println(new Date(System.currentTimeMillis()) + " Starting maintenance");
        orderCheck();
    }


    public void orderCheck() {
        Stripe.apiKey = "sk_test_d2dFpCbIu2GxhtDpP240KhNs";
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("orders/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    requestOrderProcessing(child);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setupOrderListener() {
        Stripe.apiKey = "sk_test_d2dFpCbIu2GxhtDpP240KhNs";
        database = FirebaseDatabase.getInstance();
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

        System.out.println("Firebase DB Is init");
    }

    public void createDispute(Dispute dispute) {
        database = FirebaseDatabase.getInstance();
        try {
            DatabaseReference ref = database.getReference("disputes/");
            Map<String, Object> disputeUpdates = new HashMap<String, Object>();
            disputeUpdates.put(dispute.getCharge(), dispute);
            ref.setValue(disputeUpdates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void createRefund(Refund refund) {
        database = FirebaseDatabase.getInstance();
        try {
            DatabaseReference ref = database.getReference("refunds/");
            Map<String, Object> refundUpdates = new HashMap<String, Object>();
            refundUpdates.put(refund.getCharge(), refund);
            ref.setValue(refundUpdates);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void changeOrderStatus(String chargeId, String status, @Nullable String message) {
        database = FirebaseDatabase.getInstance();

        try {
            DatabaseReference ref = database.getReference("charges/" + chargeId);
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MyCharge myCharge = dataSnapshot.getValue(MyCharge.class);
                    DatabaseReference orderRef = database.getReference("orders/" + myCharge.getId() + "/" + myCharge.getOrderId());
                    if(myCharge.getId() != null && myCharge.getOrderId() != null) {
                        Map<String, Object> orderParams = new HashMap<String, Object>();
                        orderParams.put("status", status);
                        orderParams.put("orderMessage", message);
                        orderRef.updateChildren(orderParams);

                        Map<String, Object> params = new HashMap<String, Object>();
                        params.put("status", status);

                        ref.updateChildren(params);
                    } else {
                        //add a log
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

        } catch(Exception e) {
            System.out.println(e);
        }
    }

    private void requestOrderProcessing(DataSnapshot dataSnapshot) {
        for(DataSnapshot child : dataSnapshot.getChildren()) {
            try {
                Order order = child.getValue(Order.class);

                if(order.getStatus().equals("processing")) {
                    System.out.println("Attempting to process");

                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("amount", order.getTotal());
                    params.put("currency", "usd");
                    params.put("source", order.getToken().getId());

                    try {
                        Charge charge = Charge.create(params);

                        System.out.println("charge: " + charge.getId());

                        if(dataSnapshot.getKey() != null && child.getKey() != null) {
                            DatabaseReference childref = database.getReference("orders/" + dataSnapshot.getKey() + "/" + child.getKey());
                            Map<String, Object> childUpdates = new HashMap<String, Object>();
                            childUpdates.put("status", "processed");
                            childUpdates.put("chargeId", charge.getId());
                            childUpdates.put("amount", charge.getAmount());

                            childref.updateChildren(childUpdates);

                            DatabaseReference chargeRef = database.getReference("charges/" + charge.getId());
                            Map<String, Object> chargeUpdates = new HashMap<String, Object>();
                            chargeUpdates.put("status", "waiting");
                            chargeUpdates.put("id", dataSnapshot.getKey());
                            chargeUpdates.put("orderId", child.getKey());
                            chargeUpdates.put("amount", charge.getAmount());

                            chargeRef.updateChildren(chargeUpdates);

                            double total = (order.getTotal() / 100);

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
                    catch( APIConnectionException e ) {
                        // Network communication with Stripe failed
                        // TODO - retry?
                        System.out.println("Need to add retry: Payment failure: " + e);
                    }
                    catch( APIException e ) {
                        System.out.println("API Failure: Payment failure: " + e);
                    }
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


}