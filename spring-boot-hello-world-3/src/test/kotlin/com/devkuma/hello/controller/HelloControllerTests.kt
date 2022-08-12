package com.devkuma.hello.controller

import com.devkuma.hello.service.HelloService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@WebMvcTest(HelloController::class)
class HelloControllerTests {

    @Autowired
    lateinit var mockMvc: MockMvc
    @MockBean
    lateinit var helloService: HelloService

    @Test
    @DisplayName("Hello")
    fun hello() {
        // when
        val resultActions: ResultActions = mockMvc.perform(get("/hello"))
            .andDo(print())

        // then
        resultActions
            .andExpect(status().is2xxSuccessful)
            .andExpect(content().string("hello world"))
            .andDo(print())
    }

    @Test
    @DisplayName("Hello Service")
    fun helloService() {
        // given
        given(helloService.getHello()).willReturn("hello service");

        // when
        val resultActions: ResultActions = mockMvc.perform(get("/hello-service"))
            .andDo(print())

        // then
        resultActions
            .andExpect(status().is2xxSuccessful)
            .andExpect(content().string("hello service"))
            .andDo(print())
    }

    @Test
    @DisplayName("Hello DTO")
    fun helloDto() {

        // when
        val resultActions: ResultActions = mockMvc.perform(get("/hello-dto"))
            .andDo(print())

        // then
        resultActions
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("greeting").value("hello dto"))
            .andDo(print())
    }
}