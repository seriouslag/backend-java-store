package com.chiefsretro.controllers;

import com.chiefsretro.entities.Order;
import com.chiefsretro.services.StripeService;
import com.google.firebase.database.*;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/firebase")
public class FirebaseController {




    @CrossOrigin(origins = {"http://seriouslag.com:80", "http://seriouslag.com", "http://seriouslag.com:4200", "http://localhost:4200", "http://chiefsretro.com"})
    @GetMapping("/process")
    public String process() {

        return "{\"Listening Started\": 1}";
    }

}
