package com.tfswx.component.tailfweb.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;

@SpringBootApplication
@EnableScheduling
public class ServerSseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerSseApplication.class, args);

        Thread printCurrentTimeThread = new Thread(() -> {
            while (true) {
                try {
                    LocalDateTime currentTime = LocalDateTime.now();
                    System.out.println("Current time: " + currentTime);
                    Thread.sleep(1000); // 暂停一秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        printCurrentTimeThread.start();
    }
}