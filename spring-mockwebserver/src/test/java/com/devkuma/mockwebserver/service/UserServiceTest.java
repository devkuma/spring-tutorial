package com.devkuma.mockwebserver.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import com.devkuma.mockwebserver.dto.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class UserServiceTest {

    public static MockWebServer mockWebServer;
    private UserService userService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @AfterAll
    static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @BeforeEach
    void initialize() throws JsonProcessingException {
        final String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        final WebClient webClient = WebClient.create(baseUrl);
        userService = new UserService(webClient);
    }

    @Test
    void getUserById() throws Exception {
        // given
        final User mockUser = new User(3, "devkuma");
        mockWebServer.enqueue(new MockResponse()
                                      .setBody(objectMapper.writeValueAsString(mockUser))
                                      .addHeader("Content-Type", "application/json"));

        // when
        final Mono<User> userMono = userService.getUserById(3);

        // then
        final RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertAll(
                () -> assertEquals("GET", recordedRequest.getMethod()),
                () -> assertEquals("/users/3", recordedRequest.getPath())
        );

        StepVerifier.create(userMono)
                    .expectNextMatches(employee -> mockUser.getName().equals("devkuma"))
                    .verifyComplete();
    }

    @Test
    void getUserById2() throws Exception {
        // given
        final User mockUser = new User(3, "devkuma");
        mockWebServer.enqueue(new MockResponse()
                                      .setBody(objectMapper.writeValueAsString(mockUser))
                                      .addHeader("Content-Type", "application/json"));

        // when
        final Mono<User> userMono = userService.getUserById(3);

        // then
        StepVerifier.create(userMono)
                    .expectNextMatches(employee -> mockUser.getName().equals("devkuma"))
                    .verifyComplete();

        final RecordedRequest recordedRequest = mockWebServer.takeRequest();
        assertAll(
                () -> assertEquals("GET", recordedRequest.getMethod()),
                () -> assertEquals("/users/3", recordedRequest.getPath())
        );
    }
}

