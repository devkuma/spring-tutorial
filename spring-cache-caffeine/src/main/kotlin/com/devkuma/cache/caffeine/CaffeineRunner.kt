package com.devkuma.cache.caffeine

import com.devkuma.cache.caffeine.service.CategoryService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CaffeineRunner: CommandLineRunner {

    @Autowired
    private lateinit var categoryService: CategoryService

    @Throws(Exception::class)
    override fun run(vararg args: String?) {
        categoryService.getById(1) // No hit , since this is the first request.
        categoryService.getById(2) // No hit , since this is the first request.
        categoryService.getById(1) // hit , since it is already in the cache.
        categoryService.getById(1) // hit , since it is already in the cache.
    }
}