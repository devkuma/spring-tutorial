package com.devkuma.hello.controller

import com.devkuma.hello.dto.HelloDto
import com.devkuma.hello.service.HelloService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController(val helloService: HelloService) {

    @GetMapping("/hello")
    fun hello(): String {
        return "hello world"
    }

    @GetMapping("/hello-service")
    fun helloService(): String {
        return helloService.getHello()
    }

    @GetMapping("/hello-dto")
    fun helloDto(): HelloDto {
        return HelloDto("hello dto")
    }
}