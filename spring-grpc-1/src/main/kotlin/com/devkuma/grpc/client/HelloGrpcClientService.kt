package com.devkuma.grpc.client

import com.devkuma.grpc.HelloRequest
import com.devkuma.grpc.HelloServiceGrpc
import org.springframework.stereotype.Service

@Service
class HelloGrpcClientService(
    private val helloStub: HelloServiceGrpc.HelloServiceBlockingStub,
) {

    fun sayHello(name: String): String {
        val request = HelloRequest.newBuilder()
            .setName(name)
            .build()

        val response = helloStub.sayHello(request)
        return response.message
    }
}