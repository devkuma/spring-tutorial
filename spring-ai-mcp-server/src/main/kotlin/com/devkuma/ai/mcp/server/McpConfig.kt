package com.devkuma.ai.mcp.server

import org.springframework.ai.tool.ToolCallbackProvider
import org.springframework.ai.tool.method.MethodToolCallbackProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class McpConfig {

    @Bean
    fun weatherTools(weatherService: WeatherService): ToolCallbackProvider {
        return MethodToolCallbackProvider.builder().toolObjects(weatherService).build()
    }
}