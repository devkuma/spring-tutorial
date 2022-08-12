package com.devkuma.bean.injection.ex3

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FooBeanConfig3 {

    @Bean
    fun fooBean31() =
        FooBean3(
            id = 30,
            name = "fooBean31"
        )

    @Bean
    fun fooBean32() =
        FooBean3(
            id = 31,
            name = "fooBean32"
        )
}