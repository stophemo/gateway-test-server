package com.test.kfwg.api;

import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * @author: huojie
 * @date: 2024/02/26 16:37
 **/
@RestController
public class SseController {

    @GetMapping("/time-stream")
    public Flux<ServerSentEvent<String>> streamTime() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> ServerSentEvent.<String>builder()
                        .id(String.valueOf(seq))
                        .event("time-event")
                        .data(LocalDateTime.now().toString())
                        .build());
    }
}