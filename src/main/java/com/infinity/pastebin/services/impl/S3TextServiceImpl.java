package com.infinity.pastebin.services.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.infinity.pastebin.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class S3TextServiceImpl implements S3Service<String> {
    @Value("${s3BucketName}")
    private String bucketName;
    private final AmazonS3 s3Client;

    @Autowired
    public S3TextServiceImpl(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public void upload(String content, String key, long expirationTimeDays) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(content.length());
        objectMetadata.setExpirationTime(new Date(TimeUnit.DAYS.toMillis(expirationTimeDays)));

        try {
            s3Client.putObject(new PutObjectRequest(
                    bucketName,
                    key,
                    new ByteArrayInputStream(content.getBytes()),
                    objectMetadata));
        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to upload text to S3 bucket", e);
        }

    }

    @Override
    public String get(String key) {
        try (S3Object s3Object = s3Client.getObject(bucketName, key);
             S3ObjectInputStream inputStream = s3Object.getObjectContent();
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to retrieve text from S3 bucket", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to close resources", e);
        }
    }

    @Override
    public void update() {
        // TODO
    }

    @Override
    public void delete(String key) {
        try {
            s3Client.deleteObject(bucketName, key);
        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to delete text from S3 bucket", e);
        }
    }
}
