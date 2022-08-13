package com.devkuma.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class HelloWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloWebFluxApplication.class, args);
	}

	@Bean
	RouterFunction<ServerResponse> routes(HelloHandler helloHandler) {
		return helloHandler.routes();
	}
}
