package com.devkuma.batch.prometheus.batch;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TaskletStepJobConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskletStepJobConfiguration.class);

    private Random random;

    public TaskletStepJobConfiguration() {
        this.random = new Random();
    }

    @Bean
    public Job taskletStepJob(JobBuilderFactory jobBuilderFactory, Step taskletStep1, Step taskletStep2) {
        return jobBuilderFactory.get("taskletStepJob")
                                .start(taskletStep1)
                                .next(taskletStep2)
                                .build();
    }

    @Bean
    public Step taskletStep1(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("taskletStep1")
                                 .tasklet((contribution, chunkContext) -> {
                                     LOGGER.info("taskletStep1");
                                     // simulate processing time
                                     Thread.sleep(random.nextInt(3000));
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

    @Bean
    public Step taskletStep2(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("taskletStep2")
                                 .tasklet((contribution, chunkContext) -> {
                                     LOGGER.info("taskletStep2");
                                     // simulate step failure
                                     int nextInt = random.nextInt(3000);
                                     Thread.sleep(nextInt);
                                     if (nextInt % 5 == 0) {
                                         throw new Exception("Boom!");
                                     }
                                     return RepeatStatus.FINISHED;
                                 })
                                 .build();
    }

}
