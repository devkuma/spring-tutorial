package com.devkuma.cache.caffeine.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.cache.RedisCacheManager
import org.springframework.data.redis.cache.RedisCacheWriter
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration


@Configuration
@EnableCaching
class CacheConfig(

) {
    @Value("\${spring.redis.host}")
    private lateinit var host: String

    @Value("\${spring.redis.port}")
    private val port = 0

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {
        return LettuceConnectionFactory(host, port)
    }

    @Bean
    fun redisCacheManager(): CacheManager {
        val redisCacheConfiguration =
            RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(
                        StringRedisSerializer()
                    )
                )
                .serializeValuesWith(
                    RedisSerializationContext.SerializationPair.fromSerializer(
                        //GenericJackson2JsonRedisSerializer(jacksonObjectMapper().registerKotlinModule())
                        //GenericJackson2JsonRedisSerializer(objectMapper())
                        //GenericJackson2JsonRedisSerializer()
                        genericJackson2JsonRedisSerializer()
                    )
                ).entryTtl(Duration.ofMinutes(1)) //TTL 적용도 가능하다.
                .disableCachingNullValues()

        return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(redisConnectionFactory())
            .cacheDefaults(redisCacheConfiguration)
            .build()
    }


//    @Bean
//    fun <K, V> redisTemplate(): RedisTemplate<K, V>? {
//        val redisTemplate: RedisTemplate<K, V> = RedisTemplate<K, V>()
//        redisTemplate.setConnectionFactory(redisConnectionFactory())
//        // value serializer
//        redisTemplate.keySerializer = StringRedisSerializer()
//        //redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer()
//        //redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer(jacksonObjectMapper().registerKotlinModule())
//        redisTemplate.valueSerializer = genericJackson2JsonRedisSerializer()
//        //redisTemplate.valueSerializer = StringRedisSerializer()
//
//        // hash value serializer
//        //redisTemplate.hashKeySerializer = StringRedisSerializer()
//        //redisTemplate.valueSerializer = GenericJackson2JsonRedisSerializer(jacksonObjectMapper().registerKotlinModule())
//        return redisTemplate
//    }


    @Bean
    fun genericJackson2JsonRedisSerializer(): GenericJackson2JsonRedisSerializer {


        var objectMapper = jacksonObjectMapper()
            .registerKotlinModule()

            .setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
//            .configure(MapperFeature.USE_ANNOTATIONS, false)
//            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
//            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
//        // This item must be configured, otherwise it will be reported java.lang.ClassCastException : java.util.LinkedHashMap  cannot be cast to XXX
        //objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);


        objectMapper.activateDefaultTyping(
            objectMapper.polymorphicTypeValidator,
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY
        )

//        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);


        return GenericJackson2JsonRedisSerializer(objectMapper)
    }

}
