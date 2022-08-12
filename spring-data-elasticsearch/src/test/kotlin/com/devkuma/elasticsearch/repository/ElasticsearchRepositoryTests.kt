package com.devkuma.elasticsearch.repository

import com.devkuma.elasticsearch.document.Phone
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest


@SpringBootTest
class ElasticsearchRepositoryTests() {

    @Autowired
    private lateinit var elasticsearchRepository: ElasticsearchRepository

    @Test
    fun save() {
        val phone = Phone(1, "010-0000-0000", "devkuma")
        val savedPhone = elasticsearchRepository.save(phone)

        assertAll(
            { assertNotNull(savedPhone) },
            { assertEquals(savedPhone.id, phone.id) },
            { assertEquals(savedPhone.number, phone.number) },
            { assertEquals(savedPhone.author, phone.author) }
        )
    }

    @Test
    fun findById() {
        // save
        val savePhone = Phone(1, "010-0000-0000", "devkuma")
        elasticsearchRepository.save(savePhone)

        // when
        val searchedPhone = elasticsearchRepository.findById(1).orElse(null)

        // then
        assertAll(
            { assertNotNull(searchedPhone) },
            { assertEquals(savePhone.id, searchedPhone.id) },
            { assertEquals(savePhone.number, searchedPhone.number) },
            { assertEquals(savePhone.author, searchedPhone.author) }
        )
    }

    @Test
    fun delete() {
        // save
        val phone = Phone(2, "010-1111-1111", "devkuma")
        elasticsearchRepository.save(phone)

        // delete
        elasticsearchRepository.delete(phone);

        // when
        val searchedPhone = elasticsearchRepository.findById(phone.id).orElse(null)

        // 테스트
        assertNull(searchedPhone)
    }
}