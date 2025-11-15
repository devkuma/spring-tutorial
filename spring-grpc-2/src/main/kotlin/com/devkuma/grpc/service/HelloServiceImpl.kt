package com.devkuma.grpc.service

import com.devkuma.grpc.HelloRequest
import com.devkuma.grpc.HelloResponse
import com.devkuma.grpc.HelloServiceGrpcKt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

@Service
class HelloServiceImpl : HelloServiceGrpcKt.HelloServiceCoroutineImplBase() {

    override suspend fun sayHello(request: HelloRequest): HelloResponse =
        withContext(Dispatchers.Default) {
            val message = "Hello, ${request.name}! (from Spring gRPC Kotlin)"

            HelloResponse.newBuilder()
                .setMessage(message)
                .build()
        }
}