package com.chiefsretro.services;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

@Service
public class FirebaseService {

    private static FirebaseApp app;
    private static FirebaseApp adminApp;

    private static FirebaseDatabase database;
    private static FirebaseDatabase adminDatabase;


    private static DataSnapshot products;
    private static DataSnapshot adminProducts;

    private static boolean isInit = false;

    public static void init() {


        app = FirebaseApp.getInstance();
        adminApp = FirebaseApp.getInstance("admin");
        System.out.println("adminApp: " + adminApp.getName());
        FirebaseApp.getApps().forEach((k) ->System.out.println("app: " + k.getName()));

        database = FirebaseDatabase.getInstance();
        adminDatabase = FirebaseDatabase.getInstance(adminApp);
        isInit = true;
        System.out.println("Firebase init");
    }



    @Scheduled(fixedRate=300000, initialDelay=1000)
    public void maintenance() {
        if(!isInit) {
            init();
        }
        System.out.println(new Date(System.currentTimeMillis()) + " Starting maintenance");
        orderCheck();
        compareDatabases();
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

    private void compareDatabases() {
        DatabaseReference product = database.getReference("products/");
        System.out.println(adminApp);
        System.out.println(adminDatabase);
        DatabaseReference adminProduct = adminDatabase.getReference("products/");


        product.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                products = dataSnapshot;
                System.out.println("handle products");
                if(adminProducts.hasChildren()) {
                    compareSnapshots(adminProducts, products);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adminProduct.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                adminProducts = dataSnapshot;
                System.out.println("handle adminProducts");
                if(products.hasChildren()) {
                    compareSnapshots(products, adminProducts);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void compareSnapshots(DataSnapshot ds1, DataSnapshot ds2) {
        try {
            System.out.println("comparing snapshots");
            Gson g = new Gson();
            JsonParser parser = new JsonParser();
            JsonElement jsonElement1 = parser.parse(g.toJson(ds1.getValue()));
            JsonElement jsonElement2 = parser.parse(g.toJson(ds2.getValue()));

            if (!compareJSON(jsonElement1, jsonElement2)) {
                equalizeDatabases(ds1, ds2);
            } else {
                System.out.println("Not equalizing");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void equalizeDatabases(DataSnapshot defaultSnap, DataSnapshot adminSnap) {
        System.out.println("Equalizing Databases");
    }

    private static boolean compareJSON(JsonElement je1, JsonElement je2) {
        System.out.println("comparing json");
        if(je1 != null && je2 != null) {
            if(je1.isJsonObject() && je2.isJsonObject()) {
                Gson g = new Gson();
                Type mapType = new TypeToken<Map<String, Object>>(){}.getType();
                Map<String, Object> firstMap = g.fromJson(je1, mapType);
                Map<String, Object> secondMap = g.fromJson(je2, mapType);

                return firstMap.equals(secondMap);
            }
        }
        return false;
    }
}
