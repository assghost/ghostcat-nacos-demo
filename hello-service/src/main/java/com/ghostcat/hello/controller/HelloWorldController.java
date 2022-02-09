package com.ghostcat.hello.controller;

import com.ghostcat.hello.model.vo.HelloResponseVO;
import com.ghostcat.hello.model.vo.HelloWorldVO;
import com.ghostcat.hello.service.HelloWorldService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api
@RestController
@RequestMapping("api/hello-world")
public class HelloWorldController {

    @Autowired
    private HelloWorldService helloWorldService;

    @ApiOperation("get hello world")
    @GetMapping
    public HelloResponseVO<HelloWorldVO> getHelloWorld() {
        HelloWorldVO helloWorld = helloWorldService.getHelloWorld();
        return HelloResponseVO.ok(helloWorld);
    }
}
