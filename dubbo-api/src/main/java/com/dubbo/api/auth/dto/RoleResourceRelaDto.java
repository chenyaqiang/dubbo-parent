package com.dubbo.api.auth.dto;


import com.dubbo.common.dto.BaseDto;

/**
 * @Description:角色资源关联dto
 * @Author: wb
 * @CreateDate: 2018-04-18 10:30
 * @version:
 **/
public class RoleResourceRelaDto extends BaseDto {

    private static final long serialVersionUID = -3087658965883908267L;

    private Long              roleId;                                  // 角色Id
    private Long              resourceId;                              // 资源Id

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

}
