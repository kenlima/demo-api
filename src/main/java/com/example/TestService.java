package com.example;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Created by jwlee on 2017. 2. 6..
 */
@Service
public class TestService {
    @Async
    public CompletableFuture<String> getDealAsync() {
        delay();
        System.out.println("do getDealAsync(), " + Thread.currentThread().getName());

        return CompletableFuture.completedFuture("test");
    }

    private void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
