package com.devkuma.batch.prometheus;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@EnableBatchProcessing
public class BatchPrometheusApplication {

	public static void main(String[] args) {
		SpringApplication.run(BatchPrometheusApplication.class, args);
	}
}
