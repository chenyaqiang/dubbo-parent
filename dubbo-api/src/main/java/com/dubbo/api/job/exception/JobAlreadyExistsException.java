package com.dubbo.api.job.exception;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 10:50
 * @version:
 **/
public class JobAlreadyExistsException extends JobException{

    static final long serialVersionUID = -3387516993124229948L;

    public JobAlreadyExistsException(String message) {
        super(message);
    }
}
