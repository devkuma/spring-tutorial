package com.devkuma.batch.config

import mu.KotlinLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


private val log = KotlinLogging.logger {}

@Configuration
class ParameterJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun simpleJob(): Job? {
        return jobBuilderFactory["parameterJob"]
            .start(simpleStep1(null))
            .build()
    }

    @Bean
    @JobScope
    fun simpleStep1(@Value("#{jobParameters[requestDate]}") requestDate: String?): Step {
        return stepBuilderFactory["parameterStep"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                log.info("parameter Step!! requestDate = {}", requestDate)
                RepeatStatus.FINISHED
            }
            .build()
    }
}