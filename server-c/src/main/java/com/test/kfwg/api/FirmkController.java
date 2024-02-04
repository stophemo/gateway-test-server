package com.test.kfwg.api;

import com.test.kfwg.dto.firmk.HelloOutputDTO;
import com.test.kfwg.service.FirmkService;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping("/hello")
    @ResponseBody
    public HelloOutputDTO hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return firmkService.hello(name);
    }

}
