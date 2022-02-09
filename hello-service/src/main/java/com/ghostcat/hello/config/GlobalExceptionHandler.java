package com.ghostcat.hello.config;

import com.ghostcat.hello.model.vo.HelloResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public HelloResponseVO handleException(Exception ex) {
        log.error("", ex);
        return HelloResponseVO.fail();
    }
}
