package com.test.kfwg.api;

import com.test.kfwg.dto.secmk.HelloOutputDTO;
import com.test.kfwg.service.SecmkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 模块二Controller
 *
 * @author 霍杰
 * @date 2024/01/31
 */
@RequestMapping("api/secmk")
@RestController
public class SecmkController {

    @Resource
    private SecmkService secmkService;

    @RequestMapping("/hello")
    @ResponseBody
    public HelloOutputDTO hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return secmkService.hello(name);
    }

}
