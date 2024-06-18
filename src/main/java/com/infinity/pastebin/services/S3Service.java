package com.infinity.pastebin.services;

public interface S3Service<T> {

    void upload(T content, String key, long expirationTimeDays);

    T get(String key);

    void update(String content, String key);

    void delete(String key);
}
