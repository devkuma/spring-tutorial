package com.devkuma.grpc.client

import com.devkuma.grpc.HelloServiceGrpcKt
import io.grpc.ManagedChannel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.grpc.client.GrpcChannelFactory

@Configuration
class GrpcClientConfig {

    @Bean
    fun helloStub(channelFactory: GrpcChannelFactory): HelloServiceGrpcKt.HelloServiceCoroutineStub {
        val channel: ManagedChannel = channelFactory.createChannel("local")
        return HelloServiceGrpcKt.HelloServiceCoroutineStub(channel)
    }
}