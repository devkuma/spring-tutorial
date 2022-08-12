package com.devkuma.hello.service

import org.springframework.stereotype.Service

@Service
class HelloService {

    fun getHello(): String {
        return "hello service"
    }
}