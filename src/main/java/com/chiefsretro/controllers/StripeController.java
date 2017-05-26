package com.chiefsretro.controllers;

import com.chiefsretro.services.StripeService;
import com.stripe.model.*;
import com.stripe.net.APIResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stripe")
public class StripeController {

    StripeService stripeService = new StripeService();

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
            Event event = APIResource.GSON.fromJson(request, Event.class);
            String status = "";
            String chargeId = "";
            String message = null;

            if (event.getType().equals("charge.succeeded")) {
                chargeId = ((Charge) event.getData().getObject()).getId();
                message = "Payment Successful";
                status = "paid";
            } else if (event.getType().equals("charge.failed")) {
                chargeId = ((Charge) event.getData().getObject()).getId();
                message = "Payment failed to process.";
                status = "declined";
            } else if (event.getType().equals("charge.refunded")) {
                chargeId = ((Charge) event.getData().getObject()).getId();
                message = "Payment was refunded.";
                status = "refunded";
            } else if (event.getType().equals("charge.pending")) {
                chargeId = ((Charge) event.getData().getObject()).getId();
                message = "Payment is pending.";
                status = "pending";
            } else if (event.getType().equals("charge.updated")) {
                chargeId = ((Charge) event.getData().getObject()).getId();
                message = "This payment was recently updated...";
                status = "processing_updated";
            } else if (event.getType().equals("charge.dispute.closed")) {
                chargeId = ((Dispute) event.getData().getObject()).getCharge();
                message = "This order was disputed and closed";
                status = "disputed_closed";
                stripeService.createDispute((Dispute) event.getData().getObject());
            } else if (event.getType().equals("charge.dispute.opened")) {
                chargeId = ((Dispute) event.getData().getObject()).getCharge();
                message = "This order is disputed";
                status = "disputed_open";
                stripeService.createDispute((Dispute) event.getData().getObject());
            } else if (event.getType().equals("charge.dispute.created")) {
                chargeId = ((Dispute) event.getData().getObject()).getCharge();
                message = "This order is disputed";
                status = "disputed_created";
                stripeService.createDispute((Dispute) event.getData().getObject());
            } else if (event.getType().equals("charge.dispute.funds_reinstated")) {
                chargeId = ((Dispute) event.getData().getObject()).getCharge();
                message = "Payment Successful/Disputed/Closed";
                status = "disputed_closed";
                stripeService.createDispute((Dispute) event.getData().getObject());
            } else if (event.getType().equals("charge.dispute.funds_withdrawn")) {
                chargeId = ((Dispute) event.getData().getObject()).getCharge();
                message = "Order cancelled/Payment returned";
                status = "disputed_closed_lost";
                stripeService.createDispute((Dispute) event.getData().getObject());
            } else if (event.getType().equals("charge.dispute.updated")) {
                chargeId = ((Dispute) event.getData().getObject()).getCharge();
                message = "This order is disputed";
                status = "disputed_updated";
                stripeService.createDispute((Dispute) event.getData().getObject());
            } else if (event.getType().equals("charge.refund.updated")) {
                chargeId = ((Refund) event.getData().getObject()).getCharge();
                message = "This order was refunded";
                status = "refunded";
                stripeService.createRefund((Refund) event.getData().getObject());
            }

            System.out.println(message);

            if (message != null) {
                stripeService.changeOrderStatus(chargeId, status, message);
            }
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Could not parse, " + request);
        }
    }
}
