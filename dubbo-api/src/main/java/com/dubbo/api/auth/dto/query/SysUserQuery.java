package com.dubbo.api.auth.dto.query;

import com.dubbo.common.dto.query.BaseQuery;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:37
 * @version:
 **/
public class SysUserQuery extends BaseQuery {

    private static final long serialVersionUID = -6230391530631070377L;

    private String name;                                  // 用户名，模糊匹配
    private String loginName;                             // 登录账号，模糊匹配
    private String phone;                               // 联系号码，模糊匹配

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
