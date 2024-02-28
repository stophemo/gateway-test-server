package com.tfswx.component.tailfweb.sse.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author CYVATION-LXL
 */
@Slf4j
public final class TailFUtils {
    private TailFUtils() {

    }

    public static String convertColor(String line) {
        return line.replaceAll("\\e\\[[\\d;]*[^\\d;]", "");
    }


    private final static String ENC = StandardCharsets.UTF_8.name();

    public static String filenameEncoding(String filename) {
        String encodeFileName;
        try {
            encodeFileName = URLEncoder.encode(filename, ENC).replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            log.debug("URLEncoder 异常：{}", filename, e);
            encodeFileName = filename;
        }
        return encodeFileName;
    }

    public static void setDownloadFileHeader(HttpServletResponse response, String fileName) {
        String encodeFileName = filenameEncoding(fileName);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + encodeFileName);
        response.setHeader(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
    }
}