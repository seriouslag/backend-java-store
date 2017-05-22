package com.chiefsretro.beans;

import com.chiefsretro.services.StripeService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.google.firebase.database.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@ComponentScan
public class FirebaseConfiguration {
    @Bean
    public FirebaseApp firebaseAppConfig() {
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            File file = new File(classLoader.getResource("firebase.json").getFile());
            try {
                FileInputStream serviceAccount = new FileInputStream(file);

                // Initialize the app with a custom auth variable, limiting the server's access
                Map<String, Object> auth = new HashMap<String, Object>();
                auth.put("uid", "backend-service-worker");

                try {
                    FirebaseOptions options = new FirebaseOptions.Builder()
                            .setCredential(FirebaseCredentials.fromCertificate(serviceAccount))
                            .setDatabaseUrl("https://chiefsretro-163916.firebaseio.com")
                            .setDatabaseAuthVariableOverride(auth)
                            .build();

                    FirebaseApp.initializeApp(options);
                    System.out.println("Firebase is init");

                    StripeService stripeService = new StripeService();
                    stripeService.setupOrderListener();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
