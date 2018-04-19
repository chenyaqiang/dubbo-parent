package com.dubbo.common.exception;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 20:04
 * @version:
 **/
public class SysRunException extends BaseException {

    private static final long serialVersionUID = 5497235678885386759L;

    public SysRunException(String message) {
        super(5000, message);
    }
}
