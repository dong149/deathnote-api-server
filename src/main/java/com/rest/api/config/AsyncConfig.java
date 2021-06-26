package com.rest.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;


//@Configuration
//@EnableAsync
public class AsyncConfig {
//
//    @Bean(name = "threadPoolTaskExecutor")
//    public Executor threadPoolTaskExecutor(){
//        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
//        taskExecutor.setCorePoolSize(700);
//        taskExecutor.setMaxPoolSize(15);
//        taskExecutor.setQueueCapacity(500);
//        taskExecutor.setThreadNamePrefix("Executor~");
//        taskExecutor.initialize();
//        return taskExecutor;
//    }
}
