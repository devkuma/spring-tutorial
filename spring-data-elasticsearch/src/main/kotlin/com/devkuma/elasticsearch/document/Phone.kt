package com.devkuma.elasticsearch.document

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document

@Document(indexName = "phone")
class Phone(
    @Id
    var id: Int,
    val number: String,
    val author: String
)