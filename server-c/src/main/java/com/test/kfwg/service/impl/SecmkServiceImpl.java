package com.test.kfwg.service.impl;

import com.test.kfwg.dto.secmk.HelloOutputDTO;
import com.test.kfwg.service.SecmkService;
import org.springframework.stereotype.Service;


/**
 * 模块二Service实现类
 *
 * @author 霍杰
 * @date 2024/01/31
 */
@Service
public class SecmkServiceImpl implements SecmkService {






    @Override
    public HelloOutputDTO hello(String name) {
        HelloOutputDTO helloOutputDTO = new HelloOutputDTO();
        helloOutputDTO.setText("serverC -- mk2 : Hello " + name);
        return helloOutputDTO;
    }
    
}


