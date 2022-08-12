package com.devkuma.bean.injection.ex2


import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

@Configuration
class FooBeanConfig2 {

    @Primary
    @Bean
    fun fooBean20() =
        FooBean2(
            id = 20,
            name = "fooBean20"
        )

    @Qualifier("fooBean21")
    @Bean
    fun fooBean21() =
        FooBean2(
            id = 21,
            name = "fooBean21"
        )
}