package com.tfswx.component.tailfweb.sse.api;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.tfswx.component.tailfweb.sse.config.TailFAutoConfigure;
import com.tfswx.component.tailfweb.sse.config.TailFConfig;
import com.tfswx.component.tailfweb.sse.service.TailFService;
import com.tfswx.component.tailfweb.sse.util.SseEmitterUtil;
import com.tfswx.component.tailfweb.sse.util.TailFUtils;
import com.tfswx.futool.core.common.TfFileUtils;
import com.tfswx.futool.io.readpart.ReadPartInputStream;
import com.tfswx.futool.io.utils.TfIOUtils;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Slf4j
@Controller
@RequestMapping("/tail-f")
public class TailFController {

    @Resource
    private TailFService tailFService;

    @Resource
    private TailFConfig tailFConfig;

    @ResponseBody
    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public void index(HttpServletResponse response) throws IOException {
        String rtnText = TailFAutoConfigure.PAGE_HTML;

        response.setContentType(MediaType.TEXT_HTML_VALUE);
        try (OutputStream outputStream = response.getOutputStream()) {
            outputStream.write(rtnText.getBytes(StandardCharsets.UTF_8));
        }
    }

    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() {
        return tailFService.connect();
    }

    @ApiOperation("下载日志")
    @GetMapping(value = "download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void download(HttpServletResponse response) throws IOException {
        TailFUtils.setDownloadFileHeader(response,
                String.format("%s.log", DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss")));
        String localOutput = tailFConfig.getLocalOutput();
        if (StrUtil.isBlank(localOutput)) {
            response.sendError(404, "未配置");
            return;
        }
        if (!Files.exists(Paths.get(localOutput))) {
            response.sendError(404, "日志暂未生成：" + localOutput);
            return;
        }
        Long fileSize = null;
        try {
            File file = new File(localOutput);
            fileSize = file.length();
            response.setContentLengthLong(fileSize);
        } catch (Exception e) {
            log.error("获取日志文件大小错误：{}", localOutput, e);
        }
        try (InputStream logInput = TfFileUtils.newInputStream(localOutput);
             InputStream readPartInput = fileSize != null
                     ? new ReadPartInputStream(logInput, fileSize)
                     : logInput;
             OutputStream outputStream = response.getOutputStream()) {
            TfIOUtils.copy(readPartInput, outputStream);
        } catch (Exception e) {
            if (e.getClass().getTypeName().contains("ClientAbort")) {
                // 用户断开连接
                log.warn("用户断开连接，文件下载取消");
            } else {
                log.error("日志下载错误", e);
            }
        }
    }
}





