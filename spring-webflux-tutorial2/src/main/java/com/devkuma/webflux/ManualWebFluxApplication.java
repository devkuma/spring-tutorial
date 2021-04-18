package com.devkuma.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.http.server.HttpServer;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import reactor.core.publisher.Mono;
import reactor.ipc.netty.NettyContext;
import reactor.ipc.netty.http.server.HttpServer;

@SpringBootApplication
public class ManualWebFluxApplication {

	public static void main(String[] args) {
		HttpHandler httpHandler = RouterFunctions.toHttpHandler(routes());

		HttpServer httpServer = HttpServer.create("0.0.0.0", 8080);
		Mono<? extends NettyContext> handler = httpServer
				.newHandler(new ReactorHttpHandlerAdapter(httpHandler));
	}

	static RouterFunction<ServerResponse> routes() {
		return route(GET("/"), req -> ok().syncBody("Hello World!"));
	}
}
