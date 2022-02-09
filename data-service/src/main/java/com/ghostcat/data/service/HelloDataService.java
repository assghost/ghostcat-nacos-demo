package com.ghostcat.data.service;

import com.ghostcat.data.model.vo.HelloDataVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HelloDataService {

    public HelloDataVO getHelloData() {
        HelloDataVO helloDataVO = new HelloDataVO();
        String msg = "hello alibaba spring cloud from data service";

        helloDataVO.setStr(msg);
        return helloDataVO;
    }
}
