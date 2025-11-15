package com.devkuma.grpc

import com.devkuma.grpc.client.HelloGrpcClientService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.text.contains

@SpringBootTest
class GrpcIntegrationTest {

    @Autowired
    lateinit var client: HelloGrpcClientService

    @Test
    fun testSayHello() {
        val result = client.sayHello("devkuma")
        assert(result.contains("devkuma"))

        println(result)
    }
}