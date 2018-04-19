/**
 * 
 */
package com.dubbo.common.dto;

import java.io.Serializable;

/**
 * @Company: 浙江核新同花顺网络信息股份有限公司
 * @ClassName: MongoBaseDto.java
 * @Description: mongodb基础类
 * @Author: jiangzheng@myhexin.com
 * @CreateDate 2017-8-3下午3:31:45
 * @version: 2.0
 */
public class MongoBaseDto implements Serializable {

    private static final long serialVersionUID = -3087658965883908267L;

    private String            id;                                      // id
    private Long              inputTime;                               // 创建时间
    private Long              updateTime;                              // 更新时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getInputTime() {
        return inputTime;
    }

    public void setInputTime(Long inputTime) {
        this.inputTime = inputTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

}
