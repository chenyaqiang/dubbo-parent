package com.dubbo.api.job.dto;

import com.dubbo.common.dto.query.BaseQuery;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 10:48
 * @version:
 **/
public class JobQuery extends BaseQuery {

    private static final long serialVersionUID = 997338194280600000L;

    private String name;//名称

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
