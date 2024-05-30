package com.infinity.pastebin.services.impl;

import com.infinity.pastebin.config.WebClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HashGeneratorService {
    private final WebClientConfig webClientConfig;

    @Autowired
    public HashGeneratorService(WebClientConfig webClientConfig) {
        this.webClientConfig = webClientConfig;
    }

    public String getNewHash() {
        WebClient client = webClientConfig.getHashGeneratorServiceClient();
        return client.get()
                .uri("/generator/new")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
