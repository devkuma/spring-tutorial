package com.devkuma.elasticsearch.controller

import com.devkuma.elasticsearch.document.Phone
import com.devkuma.elasticsearch.service.ElasticsearchService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(ElasticsearchController::class)
class ElasticsearchControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc
    @MockBean
    private lateinit var elasticsearchService: ElasticsearchService

    val phone = Phone(1, "010-0000-0000", "devkuma")

    @Test
    fun findById() {
        // given
        given(elasticsearchService.findById(anyInt())).willReturn(phone)

        // when
        var resultActions = mockMvc.perform(get("/phones/{id}", phone.id))
            .andDo(print())

        // then
        resultActions
            .andExpect(status().is2xxSuccessful)
            .andExpect(jsonPath("$.id").value(phone.id))
            .andExpect(jsonPath("$.number").value(phone.number))
            .andExpect(jsonPath("$.author").value(phone.author))
            .andDo(print())
    }

    @Test
    fun add() {
        // given ma")
        given(elasticsearchService.add(phone)).willReturn(phone)

        // when
        var resultActions = mockMvc.perform(post("/phones")
            .contentType(APPLICATION_JSON)
            .content(ObjectMapper().writeValueAsString(phone)))
            .andDo(print())

        // then
        resultActions
            .andExpect(status().is2xxSuccessful)
            .andExpect(redirectedUrlTemplate("/phones/{id}", phone.id))
            .andDo(print())
    }

    @Test
    fun delete() {
        // when
        var resultActions = mockMvc.perform(delete("/phones/{id}", phone.id))
            .andDo(print())

        // then
        resultActions
            .andExpect(status().is2xxSuccessful)
            .andDo(print())
    }
}