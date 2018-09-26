package com.landongavin.controllers;

import com.landongavin.services.StripeService;
import com.stripe.model.*;
import com.stripe.net.ApiResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
public class StripeController {
    @PostMapping("/webhookEndpoint")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(
            consumes="application/json",
            produces="application/json",
            method=RequestMethod.POST,
            value="webhookEndpoint")
    public void webhookEndpoint(@RequestBody String request){
        try {
            Event event = ApiResource.GSON.fromJson(request, Event.class);
            String status = "";
            String chargeId = "";
            String message = null;

            switch (event.getType()) {
                case "charge.succeeded":
                    chargeId = ((Charge) event.getData().getObject()).getId();
                    message = "Payment Successful";
                    status = "paid";
                    break;
                case "charge.failed":
                    chargeId = ((Charge) event.getData().getObject()).getId();
                    message = "Payment failed to process.";
                    status = "declined";
                    break;
                case "charge.refunded":
                    chargeId = ((Charge) event.getData().getObject()).getId();
                    message = "Payment was refunded.";
                    status = "refunded";
                    break;
                case "charge.pending":
                    chargeId = ((Charge) event.getData().getObject()).getId();
                    message = "Payment is pending.";
                    status = "pending";
                    break;
                case "charge.updated":
                    chargeId = ((Charge) event.getData().getObject()).getId();
                    message = "This payment was recently updated...";
                    status = "processing_updated";
                    break;
                case "charge.dispute.closed":
                    chargeId = ((Dispute) event.getData().getObject()).getCharge();
                    message = "This order was disputed and closed";
                    status = "disputed_closed";
                    StripeService.createDispute((Dispute) event.getData().getObject());
                    break;
                case "charge.dispute.opened":
                    chargeId = ((Dispute) event.getData().getObject()).getCharge();
                    message = "This order is disputed";
                    status = "disputed_open";
                    StripeService.createDispute((Dispute) event.getData().getObject());
                    break;
                case "charge.dispute.created":
                    chargeId = ((Dispute) event.getData().getObject()).getCharge();
                    message = "This order is disputed";
                    status = "disputed_created";
                    StripeService.createDispute((Dispute) event.getData().getObject());
                    break;
                case "charge.dispute.funds_reinstated":
                    chargeId = ((Dispute) event.getData().getObject()).getCharge();
                    message = "Payment Successful/Disputed/Closed";
                    status = "disputed_closed";
                    StripeService.createDispute((Dispute) event.getData().getObject());
                    break;
                case "charge.dispute.funds_withdrawn":
                    chargeId = ((Dispute) event.getData().getObject()).getCharge();
                    message = "Order cancelled/Payment returned";
                    status = "disputed_closed_lost";
                    StripeService.createDispute((Dispute) event.getData().getObject());
                    break;
                case "charge.dispute.updated":
                    chargeId = ((Dispute) event.getData().getObject()).getCharge();
                    message = "This order is disputed";
                    status = "disputed_updated";
                    StripeService.createDispute((Dispute) event.getData().getObject());
                    break;
                case "charge.refund.updated":
                    chargeId = ((Refund) event.getData().getObject()).getCharge();
                    message = "This order was refunded";
                    status = "refunded";
                    StripeService.createRefund((Refund) event.getData().getObject());
                    break;
            }

            System.out.println(message);

            if (message != null) {
                StripeService.changeOrderStatus(chargeId, status, message);
            }


        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not parse, " + request);
        }
    }
}
