package com.zh.cloud.mall.practice.common.exception;

/**
 * ServiceC层抛出异常，RuntimeException是为了不进行抛出或者捕捉处理
 */

public class ZhMallException extends RuntimeException{
    private final Integer code;
    private final String message;

    public ZhMallException(Integer code, String message){
        this.code = code;
        this.message = message;
    }
    public ZhMallException(ZhMallExceptionEnum ex){
        this(ex.getCode(), ex.getMsg());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
