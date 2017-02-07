package com.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by jwlee on 2017. 1. 25..
 */
@RestController
public class TestController {


    @Autowired
    private TestService testService;


    @Autowired
    private ThreadPoolTaskExecutor threadPoolExecutor;


    @RequestMapping(path = "/test", produces = "application/json")
    public String test() {

        CompletableFuture<String> cf = testService.getDealAsync();

        doElse();

        try {
            String result = cf.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        System.out.println("do main");

        return "test";
    }

    private void doElse() {
        System.out.println("do else.");
    }



    @RequestMapping(path = "/thread", produces = "application/json")
    public Map getThreadInfo() {
        Map map = new LinkedHashMap<String, Integer>();

        ThreadPoolExecutor executor = threadPoolExecutor.getThreadPoolExecutor();

        // http://www.journaldev.com/1069/threadpoolexecutor-java-thread-pool-example-executorservice
        map.put("poolSize", executor.getPoolSize());
        map.put("corePoolSize", executor.getCorePoolSize());
        map.put("activeCount", executor.getActiveCount());
        map.put("completedTaskCount", executor.getCompletedTaskCount());
        map.put("taskCount", executor.getTaskCount());
        map.put("isShutdown", executor.isShutdown());
        map.put("isTerminated", executor.isTerminated());
        map.put("queueSize", executor.getQueue().size());


        ;


        return map;
    }

    @ExceptionHandler(Throwable.class)
    public void exceptionHandler(Throwable e) {
        e.printStackTrace();
    }


}
