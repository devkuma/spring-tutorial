package com.devkuma.bean.injection

import com.devkuma.bean.injection.ex1.FooService1
import com.devkuma.bean.injection.ex2.BadFooService2
import com.devkuma.bean.injection.ex2.GoodFooService2
import com.devkuma.bean.injection.ex3.FooService3
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BeanInjectionApplication(
    private val fooService1: FooService1,

    private val badFooService2: BadFooService2,
    private val goodFooService2: GoodFooService2,

    private val fooService3: FooService3
) : CommandLineRunner {

    override fun run(vararg args: String?) {
        println("Ex 1) fooService1 ---------")
        fooService1.printBean()

        println("Ex 2) badFooService2 ------")
        badFooService2.printBean()

        println("Ex 2) goodFooService2 -----")
        goodFooService2.printBean()

        println("Ex 3) fooService3 ---------")
        fooService3.printBean()
    }
}

fun main(args: Array<String>) {
    runApplication<BeanInjectionApplication>(*args)
}

