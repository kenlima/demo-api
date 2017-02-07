package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@SpringBootApplication
@EnableAsync
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}



	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {

		int coreSize = Runtime.getRuntime().availableProcessors();
		System.out.println("core size : " + coreSize);

		int numberOfThreads = coreSize * 1000;
		System.out.println("max size : " + numberOfThreads);

		ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
		taskExecutor.setCorePoolSize(5);
		taskExecutor.setMaxPoolSize(numberOfThreads);
		taskExecutor.setQueueCapacity(100); // maxpoolsize 를 늘리기 전에 queue 에 task 가 먼저 있는지를 본다. queue capa 가 꽉챴다면 비로소 thread 를 생성 한다 https://blog.outsider.ne.kr/1066, http://ismydream.tistory.com/46
		taskExecutor.setThreadNamePrefix("Executor2222-");
		taskExecutor.setKeepAliveSeconds(20); // core pool size 의 스레드보다 많이 생성된 스레드는 해당 시간에 맞게 사라진다
		taskExecutor.initialize();

		return taskExecutor;
	}


}
