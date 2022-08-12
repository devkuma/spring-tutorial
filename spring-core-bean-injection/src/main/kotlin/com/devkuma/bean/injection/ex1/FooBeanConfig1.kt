package com.devkuma.bean.injection.ex1

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FooBeanConfig1 {

    @Bean
    fun fooBean1() =
        FooBean1(
            id = 1,
            name = "fooBean1"
        )
}