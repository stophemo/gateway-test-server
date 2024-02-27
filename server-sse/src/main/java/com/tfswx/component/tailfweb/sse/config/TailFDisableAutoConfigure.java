package com.tfswx.component.tailfweb.sse.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;

/**
 * @author CYVATION-LXL
 */
@Slf4j
@ConditionalOnMissingBean(TailFAutoConfigure.class)
public class TailFDisableAutoConfigure {
    public TailFDisableAutoConfigure() {
        log.info("控制台日志已由配置控制关闭");
    }
}