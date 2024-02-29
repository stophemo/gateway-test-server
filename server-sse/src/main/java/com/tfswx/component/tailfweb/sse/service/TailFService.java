package com.tfswx.component.tailfweb.sse.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public interface TailFService {

    SseEmitter connect();
}
