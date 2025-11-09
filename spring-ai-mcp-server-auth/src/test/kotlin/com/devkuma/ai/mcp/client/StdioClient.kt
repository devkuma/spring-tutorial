package com.devkuma.ai.mcp.client

import io.modelcontextprotocol.client.McpClient
import io.modelcontextprotocol.client.transport.ServerParameters
import io.modelcontextprotocol.client.transport.StdioClientTransport
import io.modelcontextprotocol.json.McpJsonMapper
import io.modelcontextprotocol.spec.McpSchema.CallToolRequest

fun main() {

    val stdioParams = ServerParameters.builder("java")
        .args(
            "-Dspring.ai.mcp.server.stdio=true",
            "-Dspring.main.web-application-type=none",
            "-Dlogging.pattern.console=",
            "-jar",
            "build/libs/spring-ai-mcp-server-0.0.1-SNAPSHOT.jar"
        )
        .build()

    val transport = StdioClientTransport(stdioParams, McpJsonMapper.createDefault())
    val client = McpClient.sync(transport).build()

    client.initialize()

    // List and demonstrate tools
    val toolsList = client.listTools()
    println("Available Tools = $toolsList")

    val alertResult = client.callTool(CallToolRequest("get_weather", mapOf("city" to "seoul")))
    println("get_weather Response = $alertResult")

    client.closeGracefully()
}