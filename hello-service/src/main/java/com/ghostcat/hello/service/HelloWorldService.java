package com.ghostcat.hello.service;

import com.ghostcat.hello.feign.vo.HelloDataVO;
import com.ghostcat.hello.model.vo.HelloWorldVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ghostcat.hello.feign.DataServiceFeignClient;

import java.util.List;

@Slf4j
@Service
public class HelloWorldService {

    @Autowired
    private DataServiceFeignClient dataServiceFeignClient;

    public HelloWorldVO getHelloWorld() {
        HelloWorldVO helloWorldVO = new HelloWorldVO();
        String msg = "hello alibaba spring cloud";
        String dataMsg = getData();
        if (!StringUtils.isEmpty(dataMsg)) {
            msg = dataMsg;
        }

        helloWorldVO.setStr(msg);
        return helloWorldVO;
    }

    private String getData() {
        HelloDataVO helloDataVO = dataServiceFeignClient.getHelloData();

        if (helloDataVO != null) {
            return helloDataVO.getStr();
        }
        return null;
    }
}
