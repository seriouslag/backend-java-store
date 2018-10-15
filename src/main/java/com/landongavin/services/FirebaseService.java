package com.landongavin.services;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.landongavin.interfaces.UserRoleCallback;
import com.google.firebase.database.*;
import com.landongavin.services.Interfaces.IFirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class FirebaseService implements IFirebaseService {

    private static FirebaseDatabase database;
    private final StripeService stripeService;


   // private static boolean isInit = false;

    @Autowired
    public FirebaseService(StripeService stripeService) {
        this.stripeService = stripeService;
        database = FirebaseDatabase.getInstance();
    }

    public static void init() {


//        FirebaseApp app = FirebaseApp.getInstance();
        // FirebaseApp.getApps().forEach((k) ->System.out.println("app: " + k.getName()));

//        database = FirebaseDatabase.getInstance();
//        isInit = true;
//        System.out.println("Firebase init");
    }

//    @Scheduled(fixedRate=300000, initialDelay=1000)
//    public void maintenance() {
//        if(!isInit) {
//            init();
//        }
//        //System.out.println(new Date(System.currentTimeMillis()) + " Starting maintenance");
//        //orderCheck();
//    }

    private void checkOrder() {
        DatabaseReference ref = database.getReference("orders/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child: dataSnapshot.getChildren()) {
                    stripeService.requestOrderProcessing(child);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addListenerForUserRole(String uid, UserRoleCallback userRoleCallback) {
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

    public boolean firebaseUserExists(String uid) {
        try {
            UserRecord user = FirebaseAuth.getInstance().getUser(uid);

            return user != null;
        } catch(FirebaseAuthException fbae) {
            System.out.println("Firebase Auth Exception. Error code: " + fbae.getErrorCode() + "; " + fbae.getMessage());
            return false;
        }
    }

    public boolean addRoleToFirebaseUser(String uid, String role) {
        try {
            final CountDownLatch firebaseLatch = new CountDownLatch(1);
            DatabaseReference ref = database.getReference("roles/");
            Map<String, Object> roleMap = new HashMap<>();
            roleMap.put(uid, role);
            ref.setValue(roleMap, (error, ref1) -> {
                if (error != null) {
                    System.out.println("Data could not be saved " + error.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
                firebaseLatch.countDown();
            });
            firebaseLatch.await(1L, TimeUnit.SECONDS);
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}
