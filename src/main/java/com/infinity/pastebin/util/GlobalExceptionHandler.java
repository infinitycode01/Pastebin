package com.infinity.pastebin.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.infinity.pastebin.exceptions.PasteNotCreatedException;
import com.infinity.pastebin.exceptions.PasteNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<PasteErrorResponse> buildErrorResponseEntity(String message, HttpStatus status) {
        log.warn(message);
        PasteErrorResponse response = new PasteErrorResponse(message, LocalDateTime.now());
        return ResponseEntity.status(status).body(response);
    }

    /**
     * AmazonServiceException
     */
    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<PasteErrorResponse> handleAmazonServiceException(AmazonServiceException e) {
        return buildErrorResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * SdkClientException
     */
    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<PasteErrorResponse> handleSdkClientException(SdkClientException e) {
        return buildErrorResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * PasteNotCreatedException
     */
    @ExceptionHandler(PasteNotCreatedException.class)
    public ResponseEntity<PasteErrorResponse> handlePasteNotCreatedException(PasteNotCreatedException e) {
        return buildErrorResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * PasteNotFoundException
     */
    @ExceptionHandler(PasteNotFoundException.class)
    public ResponseEntity<PasteErrorResponse> handlePasteNotFoundException(PasteNotFoundException e) {
        return buildErrorResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
