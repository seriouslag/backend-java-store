package com.landongavin.services;

import com.landongavin.interfaces.UserRoleCallback;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    private static FirebaseDatabase database;


    private static boolean isInit = false;

    public static void init() {


        FirebaseApp app = FirebaseApp.getInstance();
        // FirebaseApp.getApps().forEach((k) ->System.out.println("app: " + k.getName()));

        database = FirebaseDatabase.getInstance();
        isInit = true;
        System.out.println("Firebase init");
    }

    @Scheduled(fixedRate=300000, initialDelay=1000)
    public void maintenance() {
        if(!isInit) {
            init();
        }
        //System.out.println(new Date(System.currentTimeMillis()) + " Starting maintenance");
        //orderCheck();
    }

    private static void orderCheck() {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("orders/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    StripeService.requestOrderProcessing(child);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public static void addListenerForUserRole(String uid, UserRoleCallback userRoleCallback) {
        database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("roles/" + uid);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    String value = dataSnapshot.getValue(String.class);
                    System.out.println(value);
                    userRoleCallback.onCallback(value);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("Firebase admin role check cancelled");
            }
        });
    }
}
