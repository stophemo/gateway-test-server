package com.tfswx.component.tailfweb.sse.service;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @author: huojie
 * @date: 2024/02/27 17:01
 **/
public interface TailFService {

    SseEmitter connect(String userId);
}
