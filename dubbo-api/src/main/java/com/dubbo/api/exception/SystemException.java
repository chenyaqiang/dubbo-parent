/**
 * 
 */
package com.dubbo.api.exception;


import com.dubbo.api.constant.ReturnCode;
import com.dubbo.common.exception.BaseException;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 9:24
 * @version:
 **/
public class SystemException extends BaseException {

    static final long serialVersionUID = -3387516993124229948L;

    /**
     * @param message
     */
    public SystemException(String message){
        super(ReturnCode.SYSTEM.getCode(), "system error:  " + message);
    }

    /**
     * @param code
     * @param message
     */
    public SystemException(Integer code, String message){
        super(code, message);
    }

}
