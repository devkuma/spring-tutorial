package com.devkuma.ai.mcp.client

import io.modelcontextprotocol.client.McpClient
import io.modelcontextprotocol.client.transport.HttpClientStreamableHttpTransport
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest

import java.net.http.HttpRequest

fun main() {
    val request = HttpRequest.newBuilder()
        .header("Content-Type", "application/json")
        .header("X-API-key", "api01.mycustomapikey")

    val transport = HttpClientStreamableHttpTransport.builder("http://localhost:8080")
        .requestBuilder(request)
        .build()

    val client = McpClient.sync(transport)
        .build()

    client.initialize()

    client.ping()

    // List and demonstrate tools
    val toolsList = client.listTools()
    println("Available Tools = $toolsList")

    val alertResult = client.callTool(CallToolRequest("get_weather", mapOf("city" to "seoul")))
    println("get_weather Response = $alertResult")

    client.closeGracefully()
}