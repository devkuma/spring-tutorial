package com.devkuma.elasticsearch.controller

import com.devkuma.elasticsearch.document.Phone
import com.devkuma.elasticsearch.service.ElasticsearchService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI


@RestController
@RequestMapping("/phones")
class ElasticsearchController(
    private val elasticsearchService: ElasticsearchService
) {

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): ResponseEntity<Phone> {
        return ResponseEntity.ok().body(elasticsearchService.findById2(id))
    }

    @PostMapping
    fun add(@RequestBody phone: Phone): ResponseEntity<Phone> {
        elasticsearchService.add(phone);
        return ResponseEntity.created(URI.create("/phones/${phone.id}")).build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int): ResponseEntity<Object> {
        elasticsearchService.delete(id)
        return ResponseEntity.noContent().build()
    }
}