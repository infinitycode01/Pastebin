package com.infinity.pastebin.services.impl;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.*;
import com.infinity.pastebin.exceptions.PasteNotFoundException;
import com.infinity.pastebin.services.S3Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class S3TextServiceImpl implements S3Service<String> {

    private static final Logger log = LoggerFactory.getLogger(S3TextServiceImpl.class);
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    private final PasteService pasteService;
    private final AmazonS3 s3Client;

    @Value("${s3BucketName}")
    private String bucketName;

    @Autowired
    public S3TextServiceImpl(AmazonS3 s3Client, PasteService pasteService) {
        this.s3Client = s3Client;
        this.pasteService = pasteService;
    }

    public void upload(String content, String key, long expirationTimeDays) {
        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(content.length());

        putObjectToS3(content, key, objectMetadata);
    }

    public String get(String key) {
        if (!pasteService.existsByKey(key)) {
            throw new PasteNotFoundException("Paste with key: " + key + "not found");
        }

        try (S3Object s3Object = s3Client.getObject(bucketName, key);
             S3ObjectInputStream inputStream = s3Object.getObjectContent();
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            return reader.lines().collect(Collectors.joining("\n"));

        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to retrieve text from S3 bucket for key: " + key + " due to service error", e);
        } catch (SdkClientException e) {
            throw new SdkClientException("Failed to retrieve text from S3 bucket for key: " + key + " due to SDK client error", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read text from S3 bucket for key: " + key, e);
        }
    }

    public void update(String content, String key) {
        if (!pasteService.existsByKey(key)) {
            throw new PasteNotFoundException("Paste with key: " + key + "not found");
        }

        try {
            ObjectMetadata objectMetadata = s3Client.getObject(bucketName, key)
                    .getObjectMetadata();
            objectMetadata.setContentLength(content.length());

            putObjectToS3(content, key, objectMetadata);

        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to retrieve object metadata for key: " + key + " due to service error", e);
        } catch (SdkClientException e) {
            throw new SdkClientException("Failed to retrieve object metadata for key: " + key + " due to SDK client error", e);
        }
    }

    public void delete(String key) {
        if (!pasteService.existsByKey(key)) {
            throw new PasteNotFoundException("Paste with key: " + key + "not found");
        }

        try {
            s3Client.deleteObject(bucketName, key);

            pasteService.delete(pasteService.findByKey(key));

            log.info("Object {} was deleted", key);

        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to delete text from S3 bucket for key: " + key + " due to service error", e);
        } catch (SdkClientException e) {
            throw new SdkClientException("Failed to delete text from S3 bucket for key: " + key + " due to SDK client error", e);
        }
    }

    private void putObjectToS3(String content, String key, ObjectMetadata objectMetadata) {
        try {
            s3Client.putObject(new PutObjectRequest(
                    bucketName,
                    key,
                    new ByteArrayInputStream(content.getBytes(DEFAULT_CHARSET)),
                    objectMetadata));

            log.info("Object {} was uploaded/updated", key);

        } catch (AmazonServiceException e) {
            throw new AmazonServiceException("Failed to upload/update text for key: " + key + " due to service error", e);
        } catch (SdkClientException e) {
            throw new SdkClientException("Failed to upload/update text for key: " + key + " due to SDK client error", e);
        }
    }

//    public void setBucketExpirationTimeRule() {
//        List<BucketLifecycleConfiguration.Rule> rules = new ArrayList<>();
//
//        BucketLifecycleConfiguration.Rule rule = new BucketLifecycleConfiguration.Rule()
//                .withId("Expiration time")
//                .withStatus(BucketLifecycleConfiguration.ENABLED)
//                .withExpirationInDays(7);
//        rules.add(rule);
//
//        BucketLifecycleConfiguration bucketLifecycleConfiguration = new BucketLifecycleConfiguration(rules);
//
//        SetBucketLifecycleConfigurationRequest setBucketLifecycleConfigurationRequest = new SetBucketLifecycleConfigurationRequest(
//                bucketName, bucketLifecycleConfiguration);
//
//        s3Client.setBucketLifecycleConfiguration(setBucketLifecycleConfigurationRequest);
//
//    }
}
