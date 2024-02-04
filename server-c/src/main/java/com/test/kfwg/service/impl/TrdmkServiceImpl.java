package com.test.kfwg.service.impl;

import com.test.kfwg.dto.trdmk.HelloOutputDTO;
import com.test.kfwg.service.TrdmkService;
import org.springframework.stereotype.Service;


/**
 * 模块三Service实现类
 *
 * @author 霍杰
 * @date 2024/01/31
 */
@Service
public class TrdmkServiceImpl implements TrdmkService {



    /**
     * 
     *
     * @return 出参
     */
    @Override
    public HelloOutputDTO hello(String name) {
        HelloOutputDTO helloOutputDTO = new HelloOutputDTO();
        helloOutputDTO.setText("serverC -- mk3 : Hello " + name);
        return helloOutputDTO;
    }

}


