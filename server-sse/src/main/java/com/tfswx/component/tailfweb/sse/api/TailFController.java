package com.tfswx.component.tailfweb.sse.api;

import com.tfswx.component.tailfweb.sse.service.TailFService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/tail-f")
public class TailFController {

    @Resource
    private TailFService tailFService;

    @Autowired
    private HttpServletRequest request;

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String index(Model model) {
        return "tail-f-sse";
    }


    @GetMapping(path = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter connect() {
        String userIp = request.getRemoteAddr();
        return tailFService.connect(userIp);
    }

}





