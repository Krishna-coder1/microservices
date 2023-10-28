package org.heapminds.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    public WebClient.Builder webClientBuiler(){
        return WebClient.builder();
    }
}
