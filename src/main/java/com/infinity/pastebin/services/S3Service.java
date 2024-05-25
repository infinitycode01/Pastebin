package com.infinity.pastebin.services;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
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
public class S3Service {
    @Value("${s3BucketName}")
    private String bucketName;
    private final AmazonS3 s3Client;

    @Autowired
    public S3Service(AmazonS3 s3Client) {
        this.s3Client = s3Client;
    }

    public void uploadText(String content, String key, long expirationTimeDays) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(content.length());

        if (expirationTimeDays > 0) {
            objectMetadata.setExpirationTime(new Date(TimeUnit.DAYS.toMillis(expirationTimeDays)));
        } else {
            objectMetadata.setExpirationTime(new Date(TimeUnit.DAYS.toMillis(7L)));
        }

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

    public String getText(String key) {
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

    public void updateText() {
        // TODO
    }

    public void deleteText(String key) {
        try {
            s3Client.deleteObject(bucketName, key);
        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to delete text from S3 bucket", e);
        }
    }
}
