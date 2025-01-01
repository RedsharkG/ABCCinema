package com.cinema.booking.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.apache.commons.io.FileUtils;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
// import java.io.IOException;
import com.cinema.booking.config.CloudinaryConfig;

public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService() {
        this.cloudinary = CloudinaryConfig.getInstance();
    }

    public String uploadFile(Part filePart, String folder) {
        try {
            File tempFile = createTempFile(filePart);
            Map uploadResult = cloudinary.uploader().upload(tempFile,
                    ObjectUtils.asMap(
                            "folder", folder,
                            "resource_type", "auto"));
            tempFile.delete();
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw new RuntimeException("Upload failed: " + e.getMessage());
        }
    }

    private File createTempFile(Part filePart) throws Exception {
        String fileName = UUID.randomUUID().toString();
        File tempFile = File.createTempFile(fileName, null);
        try (InputStream input = filePart.getInputStream()) {
            FileUtils.copyInputStreamToFile(input, tempFile);
        }
        return tempFile;
    }

    public void deleteFile(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId,
                    ObjectUtils.asMap("resource_type", "auto"));
        } catch (Exception e) {
            throw new RuntimeException("Delete failed: " + e.getMessage());
        }
    }
}
