package com.prathamesh.taskmanager.Config;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail jobDetail() {
        String job = UUID.randomUUID().toString();
        return JobBuilder.newJob(EmailJob.class)
                .withIdentity(job)
                .storeDurably()
                .build();
    }
}

