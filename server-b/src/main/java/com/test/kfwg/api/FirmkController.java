package com.test.kfwg.api;

import com.test.kfwg.dto.firmk.HelloOutputDTO;
import com.test.kfwg.service.FirmkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 模块一Controller
 *
 * @author 霍杰
 * @date 2024/01/31
 */
@RequestMapping("api/firmk")
@RestController
public class FirmkController {

    @Resource
    private FirmkService firmkService;

    @GetMapping("hello")
    public HelloOutputDTO hello() {
        return firmkService.hello();
    }

}
