package com.dubbo.api.auth.dto;

import com.dubbo.common.dto.BaseDto;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:30
 * @version:
 **/
public class RoleDto extends BaseDto {

    private static final long serialVersionUID = 4427659063217026626L;

    private String name;                                    // 角色名称
    private String desc;                                    // 角色描述
    private String resourceIds;                             // 角色关联的资源,逗号分隔

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(String resourceIds) {
        this.resourceIds = resourceIds;
    }

    @Override
    public String toString() {
        return "RoleDto [name=" + name + ", desc=" + desc + ", resourceIds=" + resourceIds + "]";
    }
}
