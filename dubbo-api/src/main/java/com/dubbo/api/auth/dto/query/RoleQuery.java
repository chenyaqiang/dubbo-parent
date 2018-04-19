package com.dubbo.api.auth.dto.query;

import com.dubbo.common.dto.query.BaseQuery;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:40
 * @version:
 **/
public class RoleQuery extends BaseQuery {

    private static final long serialVersionUID = -883486064580539715L;

    private String            name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
