package com.infinity.pastebin.util;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class KeyGenerator {
    public static String generate(String inputText) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(inputText.getBytes());

            return Base64
                    .getUrlEncoder()
                    .withoutPadding()
                    .encodeToString(hash)
                    .substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }
}
