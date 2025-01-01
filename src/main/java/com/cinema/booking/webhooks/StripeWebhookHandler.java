package com.cinema.booking.webhooks;

import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;

public class StripeWebhookHandler {
    private static final String WEBHOOK_SECRET = "whsec_your_webhook_secret";

    public void handleWebhookEvent(String payload, String sigHeader) throws SignatureVerificationException {
        Event event = Webhook.constructEvent(payload, sigHeader, WEBHOOK_SECRET);

        switch (event.getType()) {
            case "payment_intent.succeeded":
                handleSuccessfulPayment(event);
                break;
            case "payment_intent.payment_failed":
                handleFailedPayment(event);
                break;
        }
    }

    private void handleSuccessfulPayment(Event event) {
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        PaymentIntent paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();
        // Implement success logic
    }

    private void handleFailedPayment(Event event) {
        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        PaymentIntent paymentIntent = (PaymentIntent) dataObjectDeserializer.getObject().get();
        // Implement failure logic
    }
}
