package com.devkuma.cache.caffeine.service

import com.devkuma.cache.caffeine.dto.Category
import com.devkuma.cache.caffeine.repository.CategoryRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository
) {

    fun getById(id: Long): Category {
        log.info("Call function categoryRepository.findById(${id})")
        return categoryRepository.findById(id)
    }
}