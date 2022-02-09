package com.ghostcat.hello.feign;

import com.ghostcat.hello.feign.vo.HelloDataVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("data-service")
public interface DataServiceFeignClient {

    @GetMapping("api/hello-data")
    HelloDataVO getHelloData();

    @GetMapping("api/hello-data/list")
    List<HelloDataVO> getHelloDataList();
}
