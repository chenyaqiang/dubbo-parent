package com.dubbo.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 19:25
 * @version:
 **/
public class BaseDto implements Serializable {

    private static final long serialVersionUID = -4256245852120537289L;

    private Long id;                                      // id
    private Date createTime;                              // 创建时间
    private Date updateTime;                              // 更新时间
    private Integer delFlag;                              // 删除标志位 0：删除， 1：在使用


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
