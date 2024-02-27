package com.tfswx.component.tailfweb.sse.startup;

import cn.hutool.core.net.NetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author CYVATION-LXL
 */
@Slf4j
public class TailFReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${server.port:8080}")
    private String serverPort;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("\n-本地 控制台地址:http://localhost:{}/tail-f\n" +
                        "-局域网 控制台地址:http://{}:{}/tail-f\n" +
                        "控制台日志已开启，如需关闭，请添加配置：com.tfswx.component.tail-f.enable=false",
                serverPort,
                NetUtil.getLocalhostStr(),
                serverPort);
    }
}