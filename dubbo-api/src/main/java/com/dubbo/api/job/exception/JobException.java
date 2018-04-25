package com.dubbo.api.job.exception;

import com.dubbo.api.constant.ReturnCode;
import com.dubbo.common.exception.BaseException;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 10:49
 * @version:
 **/
public class JobException extends BaseException {

    static final long serialVersionUID = -3387516993124229948L;

    public JobException(String message) {
        super(ReturnCode.JOB.getCode(), message);
    }
}
