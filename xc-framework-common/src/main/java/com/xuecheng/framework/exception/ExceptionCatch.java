package com.xuecheng.framework.exception;

import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 */
@ControllerAdvice//控制器增强
public class ExceptionCatch {
    private static final Logger log = LoggerFactory.getLogger(ExceptionCatch.class);

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e) {
        ResultCode resultCode = e.getResultCode();
        log.error("cast exception:" + e.getMessage());
        return new ResponseResult(resultCode);
    }
}
