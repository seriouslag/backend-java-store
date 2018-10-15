package com.landongavin.beans;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.stripe.Stripe;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FirebaseConfiguration {
    @Bean
    public FirebaseApp FirebaseAppConfig() {
        try {

            // Initialize the app with a custom auth variable, limiting the server's access
            Map<String, Object> auth = new HashMap<>();
            auth.put("uid", "backend-service-worker");

            InputStream is = new ClassPathResource("firebase.json").getInputStream();

            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(is))
                    .setDatabaseUrl("https://surruh-ed451.firebaseio.com")
                    .setDatabaseAuthVariableOverride(auth)
                    .build();

            FirebaseApp.initializeApp(options);
            System.out.println("Firebase default is init");

            Stripe.setConnectTimeout(30*1000);
            Stripe.setReadTimeout(80*1000);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}