package com.onlinetutorialspoint.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ItemController {
    private static Logger log = LoggerFactory.getLogger(ItemController.class);
    @Autowired
    private Job job;
    @Autowired
    private JobLauncher jobLauncher;
    private AtomicInteger atomicInteger = new AtomicInteger(0);

    @GetMapping(value = "/Load", produces = "application/json")
    public ResponseEntity load() throws JobParametersInvalidException
            , JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Map<String, JobParameter> maps = new HashMap<>();
        maps.put("time", new JobParameter(System.currentTimeMillis()));
        JobParameters parameters = new JobParameters(maps);
        JobExecution jobExecution = jobLauncher.run(job, parameters);
        System.out.println("Job Status" + jobExecution.getStatus());
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(value = "/getData", produces = "application/json")
    public ResponseEntity service() throws InterruptedException {
//        Thread.sleep(1000);

        log.info("Distributed Logging {}", atomicInteger.incrementAndGet());
        return new ResponseEntity(HttpStatus.OK);
    }

}
