package com.devkuma.bean.injection.ex1

import org.springframework.stereotype.Service

@Service
class FooService1(
    private var fooBean: FooBean1
) {
    fun printBean() {
        println("fooBean: id=${fooBean.id}, name=${fooBean.name}")
    }
}