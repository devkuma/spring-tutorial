package com.devkuma.grpc.client

import com.devkuma.grpc.HelloRequest
import com.devkuma.grpc.HelloServiceGrpcKt
import org.springframework.stereotype.Service

@Service
class MyGrpcClientService(
    private val helloStub: HelloServiceGrpcKt.HelloServiceCoroutineStub
) {

    suspend fun sayHello(name: String): String {
        val request = HelloRequest.newBuilder()
            .setName(name)
            .build()

        val response = helloStub.sayHello(request)
        return response.message
    }
}