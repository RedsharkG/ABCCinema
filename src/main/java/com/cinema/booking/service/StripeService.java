package com.cinema.booking.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Refund;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.RefundCreateParams;
import com.cinema.booking.config.StripeConfig;

public class StripeService {

    public StripeService() {
        Stripe.apiKey = StripeConfig.getSecretKey();
    }

    public PaymentIntent createPaymentIntent(Long amount, String currency) throws StripeException {
        try {
            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency(currency)
                    .setAutomaticPaymentMethods(
                            PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                    .setEnabled(true)
                                    .build())
                    .build();

            return PaymentIntent.create(params);
        } catch (StripeException e) {
            System.err.println("Stripe error: " + e.getMessage());
            throw e;
        }
    }

    public Refund createRefund(String paymentIntentId) throws StripeException {
        RefundCreateParams params = RefundCreateParams.builder()
                .setPaymentIntent(paymentIntentId)
                .build();

        return Refund.create(params);
    }
}
