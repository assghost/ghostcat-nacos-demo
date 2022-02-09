package com.ghostcat.data.controller;

import com.ghostcat.data.model.vo.HelloDataVO;
import com.ghostcat.data.service.HelloDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("api/hello-data")
public class HelloDataController {

    @Autowired
    private HelloDataService helloDataService;

    @GetMapping
    public Mono<HelloDataVO> getHelloWorld() {
        HelloDataVO helloDataVO = helloDataService.getHelloData();
        return Mono.just(helloDataVO);
    }

    @GetMapping(value = "stream", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<HelloDataVO> getHelloWorldStream() {
        HelloDataVO helloDataVO = helloDataService.getHelloData();
        return Flux.just(helloDataVO, helloDataVO, helloDataVO).delayElements(Duration.ofSeconds(1));
    }

    @GetMapping(value = "list")
    public Flux<HelloDataVO> getHelloWorldList() {
        HelloDataVO helloDataVO = helloDataService.getHelloData();
        return Flux.just(helloDataVO, helloDataVO, helloDataVO).delayElements(Duration.ofSeconds(1));
    }
}
