package com.infinity.pastebin.exceptions;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.infinity.pastebin.util.PasteErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * AmazonServiceException
     */
    @ExceptionHandler(AmazonServiceException.class)
    public ResponseEntity<PasteErrorResponse> handleAmazonServiceException(
            AmazonServiceException e) {

        log.warn(e.getMessage());

        PasteErrorResponse response = new PasteErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * SdkClientException
     */
    @ExceptionHandler(SdkClientException.class)
    public ResponseEntity<PasteErrorResponse> handleSdkClientException(
            SdkClientException e) {

        log.warn(e.getMessage());

        PasteErrorResponse response = new PasteErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * PasteNotCreatedException
     */
    @ExceptionHandler(PasteNotCreatedException.class)
    public ResponseEntity<PasteErrorResponse> handlePasteNotCreatedException(
            PasteNotCreatedException e) {

        log.warn(e.getMessage());

        PasteErrorResponse response = new PasteErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    /**
     * PasteNotFoundException
     */
    @ExceptionHandler(PasteNotFoundException.class)
    public ResponseEntity<PasteErrorResponse> handlePasteNotFoundException(
            PasteNotFoundException e) {

        log.warn(e.getMessage());

        PasteErrorResponse response = new PasteErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
