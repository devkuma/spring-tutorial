package com.devkuma.grpc.service

import com.devkuma.grpc.HelloRequest
import com.devkuma.grpc.HelloResponse
import com.devkuma.grpc.HelloServiceGrpc
import net.devh.boot.grpc.server.service.GrpcService
import io.grpc.stub.StreamObserver

@GrpcService
class HelloServiceImpl : HelloServiceGrpc.HelloServiceImplBase() {

    override fun sayHello(
        request: HelloRequest,
        responseObserver: StreamObserver<HelloResponse>
    ) {
        val reply = "Hello, ${request.name}!"

        val response = HelloResponse.newBuilder()
            .setMessage(reply)
            .build()

        responseObserver.onNext(response)
        responseObserver.onCompleted()
    }
}