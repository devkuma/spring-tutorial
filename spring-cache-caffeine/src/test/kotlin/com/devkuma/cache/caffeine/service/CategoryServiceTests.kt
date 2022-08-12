package com.devkuma.cache.caffeine.service

import com.devkuma.cache.caffeine.config.CacheConfig
import com.devkuma.cache.caffeine.repository.CategoryRepository
import com.ninjasquad.springmockk.SpykBean
import io.mockk.verify
import mu.KotlinLogging
import org.junit.jupiter.api.Test

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

private val log = KotlinLogging.logger {}

@SpringBootTest(classes = [CategoryService::class, CategoryRepository::class, CacheConfig::class])
class CategoryServiceTests {

    @Autowired
    private lateinit var categoryService: CategoryService

    @SpykBean
    private lateinit var categoryRepository: CategoryRepository

    @Test
    fun `카테고리 ID로 조회시 캐시 사용 여부 테스트`() {

        // When
        val category11 = categoryService.getById(1)
        val category21 = categoryService.getById(2)
        val category12 = categoryService.getById(1)
        val category22 = categoryService.getById(2)

        log.info { "category1=$category11" }
        log.info { "category2=$category21" }
        log.info { "category3=$category12" }
        log.info { "category4=$category22" }

        // Then
        verify(exactly = 2) { categoryRepository.findById(any()) }
    }
}