package com.devkuma.cache.caffeine.repository

import com.devkuma.cache.caffeine.dto.Category
import mu.KotlinLogging
import org.springframework.cache.annotation.CacheConfig
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Repository

private val log = KotlinLogging.logger {}

@Repository

class CategoryRepository {

    @Cacheable("category")
    fun findById(id: Long): Category {
        log.info("category[${id}] cache is not used.")
        return Category(id,"Book")
    }

    fun createCategory(id: Long): Category {
        return Category(id,"Book")
    }
}