package com.tfswx.component.tailfweb.sse.service.Impl;

import com.tfswx.component.tailfweb.sse.service.TailFService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author CYVATION-LXL
 */
@Slf4j
@Service
public class TailFServiceImpl implements TailFService {

    private static Map<String, SseEmitter> sseEmitterMap = new ConcurrentHashMap<>();

    /**
     * 连接sse
     *
     * @param userId
     * @return
     */
    @Override
    public SseEmitter connect(String userId) {
        SseEmitter sseEmitter = new SseEmitter();
        String logs = "这是测试日志;这是测试日志;这是测试日志;这是测试日志这是测试日志;这是测试日志;这是测试日志;这是测试日志;这是测试日志";

        // 连接成功需要返回数据，否则会出现待处理状态
        try {
            sseEmitter.send(logs, MediaType.APPLICATION_JSON);
            log.info("【WEB控制台】有新的连接：{}", userId);
        } catch (IOException e) {
            log.error("日志数据发送失败: {}", logs, e.getCause());
        }

        // 连接断开
        sseEmitter.onCompletion(() -> {
            sseEmitterMap.remove(userId);
            log.info("【WEB控制台】连接断开：{}", userId);
        });

        // 连接超时
        sseEmitter.onTimeout(() -> {
            sseEmitterMap.remove(userId);
            log.error("【WEB控制台】连接超时：{}", userId);
        });

        // 连接报错
        sseEmitter.onError((e) -> {
            sseEmitterMap.remove(userId);
            log.error("【WEB控制台】连接异常：{}", userId, e);
        });

        sseEmitterMap.put(userId, sseEmitter);
        return sseEmitter;
    }

}