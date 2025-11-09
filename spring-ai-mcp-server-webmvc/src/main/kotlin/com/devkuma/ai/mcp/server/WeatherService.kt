package com.devkuma.ai.mcp.server

import org.springframework.ai.tool.annotation.Tool
import org.springframework.ai.tool.annotation.ToolParam
import org.springframework.stereotype.Service

@Service
class WeatherService {

    @Tool(name = "get_weather", description = "Return the weather of a given city.")
    fun getWeather(@ToolParam(description = "The city for which to get the weather") city: String): String {
        // 실제 날씨 정보를 가져오는 로직을 추가한다.
        // 여기서는 간단히 "Good"을 반환한다.
        return "The weather in $city is good."
    }
}