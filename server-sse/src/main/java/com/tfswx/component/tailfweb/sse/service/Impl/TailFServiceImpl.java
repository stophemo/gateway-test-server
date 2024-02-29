package com.tfswx.component.tailfweb.sse.service.Impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tfswx.component.tailfweb.sse.config.TailFConfig;
import com.tfswx.component.tailfweb.sse.config.TailFSystemOutEnvironmentPostProcessor;
import com.tfswx.component.tailfweb.sse.service.TailFService;
import com.tfswx.component.tailfweb.sse.util.SseEmitterUtil;
import com.tfswx.component.tailfweb.sse.util.TailFUtils;
import com.tfswx.futool.core.common.TfFileUtils;
import com.tfswx.futool.core.common.TfPathUtils;
import com.tfswx.futool.core.lazy.LazyValue;
import com.tfswx.futool.core.lazy.TfLazyUtils;
import com.tfswx.futool.core.thread.concurrent.ConcurrentMergeQueue;
import com.tfswx.futool.io.blackhole.BlackHoleOutputStream;
import com.tfswx.futool.io.piped.TfAlwaysPipedInputStream;
import com.tfswx.futool.io.piped.TfAlwaysPipedOutputStream;
import com.tfswx.futool.io.readpart.ReadPartInputStream;
import com.tfswx.futool.io.utils.TfIOUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

/**
 * @author CYVATION-LXL
 */
@Slf4j
public class TailFServiceImpl implements TailFService, InitializingBean {

    @Autowired
    private HttpServletRequest request;

    @Override
    public SseEmitter connect() {
        String userIp = request.getRemoteAddr();
        return SseEmitterUtil.connect(userIp);
    }

    @Resource
    private TailFConfig tailFConfig;

    private final ConcurrentMergeQueue<String> sendQueue;
    private int charSize = 1000;
    private volatile LazyValue<OutputStream> localOutput;
    private final static byte[] NEW_LINE_DATA = System.lineSeparator().getBytes(StandardCharsets.UTF_8);
    private final TfAlwaysPipedInputStream pipedInputStream = new TfAlwaysPipedInputStream();
    private final TfAlwaysPipedOutputStream pipedOutputStream = new TfAlwaysPipedOutputStream(pipedInputStream);

    public TailFServiceImpl(TailFConfig tailFConfig) throws IOException {
        this.sendQueue = new ConcurrentMergeQueue<>(this::sendQueueHandler,
                Math.max(tailFConfig.getOutputMergeSize(), 1),
                1,
                Math.max(tailFConfig.getOutputBufferSize(), 100));
        this.sendQueue.setHighMode(true);
    }

    private void sendQueueHandler(List<String> lines) {
        for (String line : lines) {
            try {
                localOutput.get().write(line.getBytes(StandardCharsets.UTF_8));
                localOutput.get().write(NEW_LINE_DATA);
            } catch (Exception e) {
                log.warn("本地日志输出异常，日志输出将中止", e);
                localOutput = TfLazyUtils.create(() -> BlackHoleOutputStream.INSTANCE);
            }
            String lineFormat = line.length() > charSize
                    ? line.substring(0, charSize) + "...(too long)"
                    : line;
            // 获取连接人数
            int userCount = SseEmitterUtil.getUserCount();
            // 如果无在线人数，返回
            if (userCount < 1) {
                log.info("【WEB控制台】无连接！");
            } else {
                SseEmitterUtil.batchSendMessage(lineFormat);
            }
        }
    }


    @Override
    public void afterPropertiesSet() {

        this.charSize = tailFConfig.getCharSize();

        // 打开本地文件输出
        if (StrUtil.isNotBlank(tailFConfig.getLocalOutput())) {
            localOutput = TfLazyUtils.create(() -> {
                String directory = TfPathUtils.getDirectory(tailFConfig.getLocalOutput());
                try {
                    Files.createDirectories(Paths.get(directory));
                } catch (IOException e) {
                    log.warn("文件夹创建失败，本地日志输出功能将被禁用：{}", directory, e);
                    return BlackHoleOutputStream.INSTANCE;
                }

                try {
                    OutputStream outputStream = TfFileUtils.appendOutputStream(tailFConfig.getLocalOutput());
                    try {
                        outputStream.write(NEW_LINE_DATA);
                        outputStream.write(NEW_LINE_DATA);
                        outputStream.write(NEW_LINE_DATA);
                        outputStream.write(
                                String.format("-------------------------------- append-start:%s --------------------------------",
                                                DateUtil.format(
                                                        new Date(),
                                                        "yyyy-MM-dd HH:mm:ss"))
                                        .getBytes(StandardCharsets.UTF_8));
                        outputStream.write(NEW_LINE_DATA);
                        outputStream.write(NEW_LINE_DATA);
                        outputStream.write(NEW_LINE_DATA);
                        // 追加正常，将文件流交给上层使用
                        return outputStream;
                    } catch (Exception e) {
                        // 如果出现异常，则首先把文件关了
                        TfIOUtils.closeQuietly(outputStream);
                        throw e;
                    }
                } catch (IOException e) {
                    log.warn("日志文件创建失败，本地日志输出功能将被禁用：{}", tailFConfig.getLocalOutput(), e);
                    return BlackHoleOutputStream.INSTANCE;
                }
            });
        } else {
            localOutput = TfLazyUtils.create(() -> BlackHoleOutputStream.INSTANCE);
        }

        Thread backgroundThread = new Thread(() -> {
            try (InputStreamReader streamReader = new InputStreamReader(pipedInputStream, StandardCharsets.UTF_8);
                 BufferedReader reader = new BufferedReader(streamReader)) {
                String line;
                while (null != (line = reader.readLine())) {
                    if (SseEmitterUtil.getUserCount() == 0) {
                        continue;
                    }
                    String noColorCharLine = TailFUtils.convertColor(line);
                    sendQueue.offer(noColorCharLine);
                }
            } catch (IOException e) {
                log.error("日志异常", e);
            }
        });
        backgroundThread.start();

        ListenerOutputStream outputStream = new ListenerOutputStream(BlackHoleOutputStream.INSTANCE, pipedOutputStream);
        TailFSystemOutEnvironmentPostProcessor.PUBLISH_OUTPUT_STREAM.addSubscription(outputStream);
    }
}