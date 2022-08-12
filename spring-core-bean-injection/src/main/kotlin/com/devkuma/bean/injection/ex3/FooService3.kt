package com.devkuma.bean.injection.ex3

import org.springframework.stereotype.Service

@Service
class FooService3(
    private var fooBean31: FooBean3,
    private var fooBean32: FooBean3
) {
    fun printBean() {
        println("fooBean31: id=${fooBean31.id}, name=${fooBean31.name}")
        println("fooBean30: id=${fooBean32.id}, name=${fooBean32.name}")
    }
}