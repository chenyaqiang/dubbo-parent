package com.dubbo.api.auth.service;

import com.dubbo.api.auth.dto.ResourceDto;
import com.dubbo.api.auth.dto.RoleDto;
import com.dubbo.common.dto.query.BaseQuery;
import com.dubbo.common.service.BaseService;

import java.util.List;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:46
 * @version:
 **/
public interface RoleService extends BaseService<RoleDto, BaseQuery> {

    /**
     * 查询当前用户拥有的资源树，且当前角色在该资源树上的选中状态<br />
     *
     * @param userId 用户id
     * @param roleId 角色id
     * @return 资源树
     */
    List<ResourceDto> findRoleResourceTree(Long userId, Long roleId);
}
