package com.infinity.pastebin.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${hashGeneratorUrl}")
    private String hashGeneratorUrl;

    @Bean
    public WebClient getHashGeneratorServiceClient() {
        return WebClient.create(hashGeneratorUrl);
    }
}
