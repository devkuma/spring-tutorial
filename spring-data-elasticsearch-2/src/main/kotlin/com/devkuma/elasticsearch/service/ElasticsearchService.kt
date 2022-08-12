package com.devkuma.elasticsearch.service

import com.devkuma.elasticsearch.document.Phone
//import com.devkuma.elasticsearch.repository.ElasticsearchRepository
//import com.devkuma.elasticsearch.repository.PhoneElasticsearchOperations
import com.devkuma.elasticsearch.repository.PhoneElasticsearchRepository
import org.springframework.stereotype.Service

@Service
class ElasticsearchService(
//    private val elasticsearchRepository: ElasticsearchRepository,
//    private val phoneElasticsearchOperations: PhoneElasticsearchOperations,
    private val phoneElasticsearchRepository: PhoneElasticsearchRepository,
) {
//    fun findById(id: Int): Phone {
//        return phoneElasticsearchRepository.findById(id).orElseThrow()
//    }

    fun findById2(id: Int): Phone? {
        return phoneElasticsearchRepository.searchOneById(id)
    }

    fun add(phone: Phone): Phone {
        return phoneElasticsearchRepository.save(phone)
    }

    fun delete(id: Int) {
        val phone =  phoneElasticsearchRepository.searchOneById(id)
        phoneElasticsearchRepository.delete(id)
    }
}
