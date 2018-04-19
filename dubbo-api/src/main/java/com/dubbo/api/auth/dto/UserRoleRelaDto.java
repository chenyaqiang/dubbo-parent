package com.dubbo.api.auth.dto;

import com.dubbo.common.dto.BaseDto;

/**
 * @Description:用戶角色关联dto
 * @Author: wb
 * @CreateDate: 2018-04-18 10:33
 * @version:
 **/
public class UserRoleRelaDto extends BaseDto {

    private static final long serialVersionUID = -3135188917944942921L;

    private Long userId;                                  // 用户Id
    private Long roleId;                                  // 角色Id
    private String roleName;                                // 角色名

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
