package com.tfswx.component.tailfweb.sse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author CYVATION-LXL
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "com.tfswx.component.tail-f")
public class TailFConfig {
    /**
     * 总开关
     */
    private boolean enable = true;
    /**
     * 页面日志最大条数
     */
    private int lines = 200;
    /**
     * 日志本地输出
     */
    private String localOutput = null;
    /**
     * 是否异步发送（默认false同步发送）
     */
    private boolean sendAsync = false;
    /**
     * 单行日志文本字符最大长度
     */
    private int charSize = 1000;
    /**
     * 输出缓冲区大小（日志行数）
     */
    private int outputBufferSize = 5000;
    /**
     * 批量合并输出大小（日志行数）
     */
    private int outputMergeSize = 1;
}