package com.kleidung.service;

import com.kleidung.model.Image;
import com.kleidung.repository.ImageRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.UUID;

public class ImageService {

    private final ImageRepository imageRepository = new ImageRepository();
    private static final String UPLOAD_DIR = "uploads/";

    public void uploadImage(int listingId, InputStream imageStream, String filename) throws SQLException, IOException {
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String uniqueFilename = UUID.randomUUID() + "_" + filename;
        Path filePath = uploadPath.resolve(uniqueFilename);

        Files.copy(imageStream, filePath);

        Image image = new Image(0, listingId, UPLOAD_DIR + uniqueFilename);
        imageRepository.save(image);
    }

    public Image getByListingId(int listingId) throws SQLException {
        return imageRepository.findByListingId(listingId);
    }
}