package com.devkuma.elasticsearch.service

import com.devkuma.elasticsearch.document.Phone
import com.devkuma.elasticsearch.repository.ElasticsearchRepository
import org.springframework.stereotype.Service

@Service
class ElasticsearchService(
    private val elasticsearchRepository: ElasticsearchRepository
) {
    fun findById(id: Int): Phone {
        return elasticsearchRepository.findById(id).orElseThrow()
    }

    fun add(phone: Phone): Phone {
        return elasticsearchRepository.save(phone)
    }

    fun delete(id: Int) {
        val phone =  elasticsearchRepository.findById(id).orElseThrow()
        elasticsearchRepository.delete(phone)
    }
}