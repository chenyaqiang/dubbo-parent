package com.dubbo.api.auth.dto;

import com.dubbo.common.dto.BaseDto;

import java.util.Date;

/**
 * @Description:系统用户
 * @Author: wb
 * @CreateDate: 2018-04-18 10:26
 * @version:
 **/
public class SysUserDto extends BaseDto {

    private static final long serialVersionUID = 527050133836386339L;

    private String name;                                    // 名称
    private String loginName;                               // 登录账号
    private String password;                                // 密码
    private String phone;                                   // 联系号码
    private Integer state;                                  // 用户状态 0:未完善,1: 冻结, 2：激活
    private Date lastLoginTime;                             // 最近登录时间
    private String lastLoginIp;                             // 最近登录Ip
    private String roleIds;                                 // 角色Id,逗号分隔
    private String roleNames;                               // 角色名称


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIp() {
        return lastLoginIp;
    }

    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp;
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    @Override
    public String toString() {
        return "SysUserDto{" +
                "name='" + name + '\'' +
                ", loginName='" + loginName + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", state=" + state +
                ", lastLoginTime=" + lastLoginTime +
                ", lastLoginIp='" + lastLoginIp + '\'' +
                ", roleIds='" + roleIds + '\'' +
                ", roleNames='" + roleNames + '\'' +
                '}';
    }
}
