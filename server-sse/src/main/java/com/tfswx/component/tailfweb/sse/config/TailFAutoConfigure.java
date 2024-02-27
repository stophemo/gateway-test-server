package com.tfswx.component.tailfweb.sse.config;

import com.tfswx.component.tailfweb.sse.api.TailFController;
//import com.tfswx.component.tailfweb.sse.service.impl.TailFServiceImpl;
//import com.tfswx.component.tailfweb.sse.service.impl.WebSocketCentreServiceImpl;
import com.tfswx.component.tailfweb.sse.startup.TailFReadyEventListener;
//import com.tfswx.futool.io.utils.TfIOUtils;
import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
//import org.springframework.web.socket.server.standard.ServerEndpointExporter;

//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;

/**
 * @author CYVATION-LXL
 */
@Slf4j
@Import({
//        TailFServiceImpl.class,
        TailFReadyEventListener.class,
//        WebSocketCentreServiceImpl.class,
        TailFController.class})
@ConditionalOnProperty(prefix = "com.tfswx.component.tail-f", value = "enable", havingValue = "true", matchIfMissing = true)
@ComponentScan("com.tfswx.component.tailfweb")
@Configuration
public class TailFAutoConfigure {
//    public static String PAGE_HTML;
//
//    public TailFAutoConfigure(TailFConfig tailFConfig) {
//        try (InputStream inputStream = this.getClass().getResourceAsStream("/static/tail-f.html")) {
//            byte[] bytes = TfIOUtils.toByteArray(inputStream);
//            String html = new String(bytes, StandardCharsets.UTF_8);
//            PAGE_HTML = html.replace("88888888", String.valueOf(tailFConfig.getLines()));
//        } catch (IOException ex) {
//            //
//            PAGE_HTML = null;
//        }
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public ServerEndpointExporter systemOutLogServerEndpointExporter() {
//        return new ServerEndpointExporter();
//    }
}