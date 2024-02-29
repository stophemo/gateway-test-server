package com.tfswx.component.tailfweb.sse.service.Impl;

import com.tfswx.component.tailfweb.sse.util.SseEmitterUtil;
import com.tfswx.futool.io.common.AdditionalOutputStream;
import com.tfswx.futool.io.piped.TfAlwaysPipedOutputStream;
import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;

/**
 * @author CYVATION-LXL
 */
@Slf4j
public class ListenerOutputStream extends AdditionalOutputStream {

    private final TfAlwaysPipedOutputStream pipedOutputStream;

    public ListenerOutputStream(OutputStream out, TfAlwaysPipedOutputStream pipedOutputStream) {
        super(out);
        this.pipedOutputStream = pipedOutputStream;
    }

    @Override
    protected void innerWrite(byte[] buffer, int offset, int length) {
        if (SseEmitterUtil.isNeedSend() && length > 0) {
            try {
                pipedOutputStream.write(buffer, offset, length);
            } catch (Exception ignore) {
                // 忽略
            }
        }
    }

    @Override
    protected void innerClose() {
        log.error("错误的关闭调用", new RuntimeException("close error call"));
    }
}