package com.devkuma.batch.prometheus.batch;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class JobScheduler {

	private final Job taskletStepJob;

	private final Job itemStepJob;

	private final JobLauncher jobLauncher;

	@Autowired
	public JobScheduler(Job taskletStepJob, Job itemStepJob, JobLauncher jobLauncher) {
		this.taskletStepJob = taskletStepJob;
		this.itemStepJob = itemStepJob;
		this.jobLauncher = jobLauncher;
	}

	@Scheduled(cron = "*/10 * * * * *")
	public void launchJob1() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
																.toJobParameters();

		jobLauncher.run(taskletStepJob, jobParameters);
	}

	@Scheduled(cron = "*/15 * * * * *")
	public void launchJob2() throws Exception {
		JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
																.toJobParameters();

		jobLauncher.run(itemStepJob, jobParameters);
	}

}
