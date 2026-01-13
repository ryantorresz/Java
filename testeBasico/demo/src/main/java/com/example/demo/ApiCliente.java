package com.example.demo;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class ApiCliente {

    private final WebClient webClient;

    public ApiCliente() {
        this.webClient = WebClient.builder()
                .baseUrl("https://pokeapi.co/api/v2")
                .build();
    }

    public String get(String url) {
        return webClient.get()
                .uri(url)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}