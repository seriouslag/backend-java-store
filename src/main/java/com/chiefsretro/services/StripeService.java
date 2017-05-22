package com.chiefsretro.services;

import com.chiefsretro.entities.Order;
import com.google.firebase.database.*;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class StripeService {

    private FirebaseDatabase database;

    public void setupOrderListener() {
        database = FirebaseDatabase.getInstance();
        Stripe.apiKey = "sk_test_d2dFpCbIu2GxhtDpP240KhNs";
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

                    Charge charge = Charge.create(params);

                    DatabaseReference childref = database.getReference("orders/" + dataSnapshot.getKey() + "/" + child.getKey());
                    Map<String, Object> childUpdates = new HashMap<String, Object>();
                    childUpdates.put("status", "processed");

                    childref.updateChildren(childUpdates);

                    double total = (order.getTotal()/100);

                    System.out.println("A charge of $" + total + " should have been placed");
                }

            } catch(Exception e) {
                System.out.println(e);
            }
        }
    }


}