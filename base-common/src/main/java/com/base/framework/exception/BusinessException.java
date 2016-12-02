package com.base.framework.exception;

/**
 * BusinessException
 * @author zhouyw
 * @date 2016.09.06
 */
public class BusinessException extends RuntimeException{

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BusinessException(String code,String message) {
        super((message));
        this.code = code;
    }

    public BusinessException(String code,String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }


    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
