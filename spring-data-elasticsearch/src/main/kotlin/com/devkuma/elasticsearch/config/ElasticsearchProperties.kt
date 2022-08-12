package com.devkuma.elasticsearch.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "elasticsearch")
class ElasticsearchProperties(
    private val host: String,
    private val port: Integer
) {
    fun getHostAndPort(): String {
        return "$host:$port"
    }
}