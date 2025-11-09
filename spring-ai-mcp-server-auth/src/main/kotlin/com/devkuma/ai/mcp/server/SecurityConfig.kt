package com.devkuma.ai.mcp.server

import org.springaicommunity.mcp.security.server.apikey.ApiKeyEntityRepository
import org.springaicommunity.mcp.security.server.apikey.memory.ApiKeyEntityImpl
import org.springaicommunity.mcp.security.server.apikey.memory.InMemoryApiKeyEntityRepository
import org.springaicommunity.mcp.security.server.config.McpApiKeyConfigurer.mcpServerApiKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain =
        http.authorizeHttpRequests { authorizeHttpRequestsCustomizer ->
            authorizeHttpRequestsCustomizer.anyRequest().authenticated()
        }.with(
            mcpServerApiKey(),
            { apiKey ->
                apiKey.apiKeyRepository(apiKeyRepository())
            }
        ).build()

    private fun apiKeyRepository(): ApiKeyEntityRepository<ApiKeyEntityImpl> {
        val apiKey = ApiKeyEntityImpl.builder()
            .name("test api key")
            .id("api01")
            .secret("mycustomapikey")
            .build()

        return InMemoryApiKeyEntityRepository(listOf(apiKey))
    }
}