package com.devkuma.grpc

import com.devkuma.grpc.client.MyGrpcClientService
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class GrpcIntegrationTest {

    @Autowired
    lateinit var client: MyGrpcClientService

    @Test
    fun testSayHello() = runBlocking {
        val result = client.sayHello("devkuma")
        assert(result.contains("devkuma"))
    }
}