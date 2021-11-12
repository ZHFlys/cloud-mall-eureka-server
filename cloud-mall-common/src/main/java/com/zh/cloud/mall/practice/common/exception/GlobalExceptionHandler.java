package com.zh.cloud.mall.practice.common.exception;

import com.zh.cloud.mall.practice.common.common.ApiRestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger
    (GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e){
        logger.error("Default Exception：" + e);
        return ApiRestResponse.error(ZhMallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(ZhMallException.class)
    @ResponseBody
    public Object handleZhMallException(ZhMallException e){
        logger.error("ZhMallException Exception：" + e);
        return ApiRestResponse.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        logger.error("MethodArgumentNotValidException Exception：" + e);
        return handleBindingResult(e.getBindingResult());
    }

    private ApiRestResponse handleBindingResult(BindingResult result){
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if(result.hasErrors()){
            List<ObjectError> allErrors = result.getAllErrors();
            allErrors.forEach(obj -> {
                String message = obj.getDefaultMessage();
                list.add(message);
            });
        }
        if(list.size() == 0){
            return ApiRestResponse.error(ZhMallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestResponse.error(ZhMallExceptionEnum.REQUEST_PARAM_ERROR.getCode(),
        list.toString());
    }
}
