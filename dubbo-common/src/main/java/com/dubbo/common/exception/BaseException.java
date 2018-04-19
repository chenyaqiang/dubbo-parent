package com.dubbo.common.exception;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 19:31
 * @version:
 **/
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = -7166564848176658883L;

    private final Integer code;                                    // 异常码

    private final String  message;                                 // 错误码

    public BaseException(Integer code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
