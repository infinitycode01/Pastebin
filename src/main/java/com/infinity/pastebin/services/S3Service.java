package com.infinity.pastebin.services;

public interface S3Service<T> {

    void upload(T content, String key, long expirationTimeDays);

    T get(String key);

    void update();

    void delete(String key);
}
