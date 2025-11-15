package com.devkuma.grpc.client

import com.devkuma.grpc.HelloServiceGrpc
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class GrpcClientConfig {

    @Bean
    fun helloChannel(): ManagedChannel =
        ManagedChannelBuilder
            .forAddress("localhost", 9090)
            .usePlaintext()
            .build()

    @Bean
    fun helloStub(channel: ManagedChannel): HelloServiceGrpc.HelloServiceBlockingStub =
        HelloServiceGrpc.newBlockingStub(channel)
}