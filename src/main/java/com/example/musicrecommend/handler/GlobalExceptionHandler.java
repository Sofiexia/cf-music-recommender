package com.example.musicrecommend.handler;

import com.example.musicrecommend.dto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error(500, "服务器内部错误，请稍后重试");
    }

    @ExceptionHandler(RestClientException.class)
    public Result<String> handleRestClientException(RestClientException e) {
        log.error("推荐服务调用失败", e);
        return Result.error(503, "推荐服务暂时不可用");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Result<String> handleIllegalArgumentException(IllegalArgumentException e) {
        log.warn("参数异常: {}", e.getMessage());
        return Result.error(400, e.getMessage());
    }

    // 可根据需要添加更多异常处理
}