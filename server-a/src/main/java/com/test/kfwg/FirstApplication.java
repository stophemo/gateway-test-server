package com.test.kfwg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;

/**
 * 启动类
 *
 * @author 霍杰
 * @date 2024/01/31
 */
@SpringBootApplication
public class FirstApplication {
    public static void main(String[] args) {
        SpringApplication.run(FirstApplication.class, args);


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