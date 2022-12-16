package com.devkuma.mockwebserver.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.devkuma.mockwebserver.dto.User;

import reactor.core.publisher.Mono;

public class UserService {

    private final WebClient webClient;

    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<User> getUserById(Integer userId) {
        return webClient
                .get()
                .uri("/users/{id}", userId)
                .retrieve()
                .bodyToMono(User.class);
    }
}
