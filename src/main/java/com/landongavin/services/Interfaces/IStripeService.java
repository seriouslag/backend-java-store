package com.landongavin.services.Interfaces;

import com.google.firebase.database.DataSnapshot;
import com.stripe.model.Dispute;
import com.stripe.model.Refund;

import javax.annotation.Nullable;

public interface IStripeService {
    void createDispute(Dispute dispute);
    void createRefund(Refund refund);
    void changeOrderStatus(String chargeId, String status, @Nullable String message);
    void requestOrderProcessing(DataSnapshot dataSnapshot);
}
