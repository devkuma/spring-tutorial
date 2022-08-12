package com.devkuma.bean.injection.ex2

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class GoodFooService2(
    @Qualifier("fooBean20") private val fooBean20: FooBean2,
    @Qualifier("fooBean21") private val fooBean21: FooBean2
) {
    fun printBean() {
        println("fooBean20: id=${fooBean20.id}, name=${fooBean20.name}")
        println("fooBean21: id=${fooBean21.id}, name=${fooBean21.name}")
    }
}