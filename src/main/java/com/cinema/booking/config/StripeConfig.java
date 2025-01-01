package com.cinema.booking.config;

public class StripeConfig {
    private static final String STRIPE_SECRET_KEY = "sk_test_51QZm41AeGXnTq4j5xqWUYXEGEUEujN0xj3Y9XXFebiQXTCE8fl6JDPbCMOthp1nWb7yZOAhWqtbs9bxDvuxBtFuT00ffuz1IEt";
    private static final String STRIPE_PUBLIC_KEY = "pk_test_51QZm41AeGXnTq4j5vQ0hoShNG69IoA4yYoGTHLH6Hh6UnbKwlJa5uCtjISU0w0rG01c8wXYuAk0ajNqfTxgHQVkZ00O3ARaIuK";

    public static String getSecretKey() {
        return STRIPE_SECRET_KEY;
    }

    public static String getPublicKey() {
        return STRIPE_PUBLIC_KEY;
    }
}
