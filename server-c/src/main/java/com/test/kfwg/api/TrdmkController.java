package com.test.kfwg.api;

import com.test.kfwg.dto.trdmk.HelloOutputDTO;
import com.test.kfwg.service.TrdmkService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 模块三Controller
 *
 * @author 霍杰
 * @date 2024/01/31
 */
@RequestMapping("api/trdmk")
@RestController
public class TrdmkController {

    @Resource
    private TrdmkService trdmkService;

    @RequestMapping("/hello")
    @ResponseBody
    public HelloOutputDTO hello(@RequestParam(name = "name", defaultValue = "unknown user") String name) {
        return trdmkService.hello(name);
    }

}
