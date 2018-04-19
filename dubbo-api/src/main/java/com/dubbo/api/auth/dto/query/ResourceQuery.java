package com.dubbo.api.auth.dto.query;

import com.dubbo.common.dto.query.BaseQuery;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:40
 * @version:
 **/
public class ResourceQuery extends BaseQuery {

    private static final long serialVersionUID = -8963207610629995760L;

    private Long              userId;                                // 用户id

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
