package com.test.kfwg.api;

import com.test.kfwg.dto.trdmk.HelloOutputDTO;
import com.test.kfwg.service.TrdmkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("hello")
    public HelloOutputDTO hello() {
        return trdmkService.hello();
    }

}
