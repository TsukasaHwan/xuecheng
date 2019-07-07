package com.xuecheng.framework.exception;

import com.google.common.collect.ImmutableMap;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.model.response.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 */
@ControllerAdvice//控制器增强
public class ExceptionCatch {
    private static final Logger log = LoggerFactory.getLogger(ExceptionCatch.class);
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;//GoogleMap线程安全并且不能改变
    //定义map的builder对象，去构建ImmutableMap
    protected static ImmutableMap.Builder<Class<? extends Throwable>, ResultCode> builder = ImmutableMap.builder();

    static {
        builder.put(HttpMessageNotReadableException.class, CommonCode.INVALID_PARAM);
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException e) {
        ResultCode resultCode = e.getResultCode();
        log.error("cast exception:" + e.getMessage());
        return new ResponseResult(resultCode);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception e) {
        log.error("cast exception:" + e.getMessage());
        if (EXCEPTIONS == null) {
            EXCEPTIONS = builder.build();//构建EXCEPTIONS
        }
        ResultCode resultCode = EXCEPTIONS.get(e.getClass());
        if (resultCode != null) {
            return new ResponseResult(resultCode);
        } else {
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }
}
