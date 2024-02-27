package com.tfswx.component.tailfweb.sse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerSseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerSseApplication.class, args);
    }
}