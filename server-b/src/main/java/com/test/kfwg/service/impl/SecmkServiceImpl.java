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



    /**
     * 
     *
     */
    @Override
    public HelloOutputDTO hello() {
        HelloOutputDTO helloOutputDTO = new HelloOutputDTO();
        helloOutputDTO.setText("serverB -- mk2 -- hello");
        return helloOutputDTO;
    }

}


