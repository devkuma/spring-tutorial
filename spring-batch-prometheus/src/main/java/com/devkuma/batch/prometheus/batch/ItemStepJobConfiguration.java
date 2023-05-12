package com.devkuma.batch.prometheus.batch;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ItemStepJobConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ItemStepJobConfiguration.class);

    private Random random;

    public ItemStepJobConfiguration() {
        this.random = new Random();
    }

    @Bean
    public Job itemStepJob(JobBuilderFactory jobBuilderFactory, Step itemStep) {
        return jobBuilderFactory.get("itemStepJob")
                                .start(itemStep)
                                .build();
    }

    @Bean
    public Step itemStep(StepBuilderFactory stepBuilderFactory) {
        return stepBuilderFactory.get("itemStep").<Integer, Integer>chunk(3)
                                 .reader(itemReader())
                                 .writer(itemWriter())
                                 .build();
    }

    @Bean
    @StepScope
    public ListItemReader<Integer> itemReader() {
        List<Integer> items = new LinkedList<>();
        // read a random number of items in each run
        for (int i = 0; i < random.nextInt(100); i++) {
            items.add(i);
        }
        return new ListItemReader<>(items);
    }

    @Bean
    public ItemWriter<Integer> itemWriter() {
        return items -> {
            for (Integer item : items) {
                int nextInt = random.nextInt(1000);
                Thread.sleep(nextInt);
                // simulate write failure
                if (nextInt % 57 == 0) {
                    throw new Exception("Boom!");
                }
                LOGGER.info("item = " + item);
            }
        };
    }
}
