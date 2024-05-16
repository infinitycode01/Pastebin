package com.infinity.pastebin.services;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.infinity.pastebin.util.KeyGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
public class S3Service {
    @Value("${s3BucketName}")
    private String bucketName;
    private final AmazonS3 s3Client;

    @Autowired
    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadText(String content, String key) {
        s3Client.putObject(new PutObjectRequest(
                bucketName,
                key,
                new ByteArrayInputStream(content.getBytes()),
                new ObjectMetadata()));
    }

    public String getText(String key) {
        S3Object s3Object = s3Client.getObject(bucketName, key);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read content from S3 object", e);
        }
    }

    public void deleteText(String key) {
        s3Client.deleteObject(bucketName, key);
    }
}
