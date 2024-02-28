package com.tfswx.component.tailfweb.sse.config;

import com.tfswx.futool.io.publish.PublishOutputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;

import java.io.PrintStream;


/**
 * 将 System.out 注册成可发布的流
 *
 * @author CYVATION-LXL
 */
public class TailFSystemOutEnvironmentPostProcessor
        implements EnvironmentPostProcessor, Ordered {

    public final static PublishOutputStream PUBLISH_OUTPUT_STREAM = new PublishOutputStream(System.out);

    static {
        PrintStream newP = new PrintStream(PUBLISH_OUTPUT_STREAM);
        System.setOut(newP);
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        // 不做功
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}