package com.cinema.booking.config;

import com.cloudinary.Cloudinary;
import java.util.HashMap;
import java.util.Map;

public class CloudinaryConfig {
    private static Cloudinary cloudinary;

    public static Cloudinary getInstance() {
        if (cloudinary == null) {
            // Direct configuration instead of using .env
            Map<String, String> config = new HashMap<>();
            config.put("cloud_name", "dzytk7ias");
            config.put("api_key", "397753459987231");
            config.put("api_secret", "prB8VV1mnyuFQTRqB5Z3TYuuevo");
            cloudinary = new Cloudinary(config);
        }
        return cloudinary;
    }
}
