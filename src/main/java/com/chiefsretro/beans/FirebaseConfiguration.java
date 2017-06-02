package com.chiefsretro.beans;

import com.chiefsretro.services.FirebaseService;
import com.chiefsretro.services.StripeService;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseCredentials;
import com.stripe.Stripe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FirebaseConfiguration {
    @Bean
    public FirebaseApp firebaseAppConfig() {
        try {
            ClassPathResource defaultCPR = new ClassPathResource("firebase.json");
            ClassPathResource adminCPR = new ClassPathResource("admin.json");

            // Initialize the app with a custom auth variable, limiting the server's access
            Map<String, Object> auth = new HashMap<String, Object>();
            auth.put("uid", "backend-service-worker");

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(defaultCPR.getInputStream()))
                    .setDatabaseUrl("https://chiefsretro-163916.firebaseio.com")
                    .setDatabaseAuthVariableOverride(auth)
                    .build();

            FirebaseApp defaultApp = FirebaseApp.initializeApp(options);
            System.out.println("Firebase default is init");

            FirebaseOptions adminOptions = new FirebaseOptions.Builder()
                    .setCredential(FirebaseCredentials.fromCertificate(adminCPR.getInputStream()))
                    .setDatabaseUrl("https://chiefsretro-admin.firebaseio.com")
                    .setDatabaseAuthVariableOverride(auth)
                    .build();

            FirebaseApp adminApp = FirebaseApp.initializeApp(adminOptions, "admin");
            System.out.println("Firebase admin is init");

            System.out.println("adminApp: " + adminApp.getName() + "; app: " + defaultApp.getName());
            Stripe.setConnectTimeout(30*1000);
            Stripe.setReadTimeout(80*1000);
            StripeService.init();
            FirebaseService.init();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}