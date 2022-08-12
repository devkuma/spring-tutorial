package com.devkuma.elasticsearch

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class ElasticsearchApplication

fun main(args: Array<String>) {
	runApplication<ElasticsearchApplication>(*args)
}
