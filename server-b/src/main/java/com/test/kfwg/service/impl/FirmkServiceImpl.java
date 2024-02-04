package com.test.kfwg.service.impl;

import com.test.kfwg.dto.firmk.HelloOutputDTO;
import com.test.kfwg.service.FirmkService;
import org.springframework.stereotype.Service;


/**
 * 模块一Service实现类
 *
 * @author 霍杰
 * @date 2024/01/31
 */
@Service
public class FirmkServiceImpl implements FirmkService {



    /**
     * 
     *
     * @return 出参
     */
    @Override
    public HelloOutputDTO hello() {
        HelloOutputDTO helloOutputDTO = new HelloOutputDTO();
        helloOutputDTO.setText("serverB -- mk1 -- hello");
        return helloOutputDTO;
    }

}


