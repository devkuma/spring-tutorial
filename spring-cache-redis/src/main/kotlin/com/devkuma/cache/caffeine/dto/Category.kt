package com.devkuma.cache.caffeine.dto

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.data.redis.core.RedisHash
import java.io.Serializable

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
//@RedisHash(value = "category")
data class Category(
    val id: Long,
    val name: String
) : Serializable