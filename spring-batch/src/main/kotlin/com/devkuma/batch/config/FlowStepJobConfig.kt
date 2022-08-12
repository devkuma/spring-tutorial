package com.devkuma.batch.config

import mu.KotlinLogging
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

private val log = KotlinLogging.logger {}

@Configuration
class FlowStepJobConfig(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory
) {

    @Bean
    fun flowStepJob(): Job {
        return jobBuilderFactory["flowStepJob"]

            .start(flowStartStep())
            .on(ExitStatus.COMPLETED.exitCode) // "flowStartStep"의 "ExitStatus"가 "COMPLETED"인 경우
            .to(flowProcessStep()) // "flowProcessStep"을 실행 시킨다
            .on("*") // "flowProcessStep"의 결과와 상관없이
            .to(flowWriteStep()) // "writeStep"을 실행 시킨다.
            .on("*") // "flowWriteStep"의 결과와 상관없이
            .end() // "Flow"를 종료 시킨다.

            .from(flowStartStep())
            .on(ExitStatus.FAILED.exitCode) // "flowStartStep"의 "ExitStatus"가 "FAILED"일 경우
            .to(flowFailOverStep()) // "flowFailOverStep"을 실행 시킨다.
            .on("*") // "flowFailOverStep"의 결과와 상관없이
            .to(flowWriteStep()) //  "flowWriteStep"을 실행 시킨다.
            .on("*") //  // "flowWriteStep"의 결과와 상관없이
            .end() // "Flow"를 종료시킨다.

            .from(flowStartStep())
            .on("*") // "flowStartStep"의 "ExitStatus"가 "FAILED", "COMPLETED"가 아닌 모든 경우
            .to(flowWriteStep()) // "flowWriteStep"을 실행시킨다.
            .on("*") // "flowWriteStep"의 결과와 상관없이
            .end() // "Flow"를 종료시킨다.

            .end()
            .build()
    }

    @Bean
    fun flowStartStep(): Step {
        return stepBuilderFactory["flowStartStep"]
            .tasklet { contribution: StepContribution, _: ChunkContext ->
                log.info("Flow Start Step!")

                val result = "COMPLETED"
                // val result = "FAIL";
                // val result = "UNKNOWN";

                // "Flow"에서 "on"은 "RepeatStatus"가 아닌 "ExitStatus"를 바라본다.
                if (result == "COMPLETED") {
                    contribution.exitStatus = ExitStatus.COMPLETED
                } else if (result == "FAIL") {
                    contribution.exitStatus = ExitStatus.FAILED
                } else if (result == "UNKNOWN") {
                    contribution.exitStatus = ExitStatus.UNKNOWN
                }
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun flowProcessStep(): Step {
        return stepBuilderFactory["flowProcessStep"]
            .tasklet { _: StepContribution?, _: ChunkContext? ->
                log.info("Flow Process Step!")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun flowFailOverStep(): Step {
        return stepBuilderFactory.get("flowFailOverStep")
            .tasklet { _: StepContribution, _: ChunkContext ->
                log.info { "Flow FailOver Step!!" }
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun flowWriteStep(): Step {
        return stepBuilderFactory["flowWriteStep"]
            .tasklet { _: StepContribution?, _: ChunkContext? ->
                log.info("Flow Write Step!")
                RepeatStatus.FINISHED
            }
            .build()
    }
}
