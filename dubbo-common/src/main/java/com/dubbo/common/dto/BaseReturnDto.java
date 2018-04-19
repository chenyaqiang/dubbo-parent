package com.dubbo.common.dto;

import java.io.Serializable;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 19:28
 * @version:
 **/
public class BaseReturnDto<T> implements Serializable {

    private static final long serialVersionUID = -8474277743638012265L;

    private int flag;       // 标识 大于0成功，<0 失败
    private String msg;     // 提示消息
    private T data;         // 返回的数据

    public BaseReturnDto() {
    }

    public BaseReturnDto(int flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public BaseReturnDto(int flag, String msg, T data) {
        this.flag = flag;
        this.msg = msg;
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
