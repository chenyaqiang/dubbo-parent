package com.dubbo.api.auth.service;

import com.dubbo.api.auth.dto.ResourceDto;
import com.dubbo.api.auth.dto.UserRoleRelaDto;
import com.dubbo.common.dto.BaseDto;
import com.dubbo.common.dto.query.BaseQuery;
import com.dubbo.common.service.BaseService;

import java.util.List;
import java.util.Set;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 10:54
 * @version:
 **/
public interface PermissionService extends BaseService<BaseDto, BaseQuery> {


    /**
     * 保存用户关联角色
     *
     * @param userId 用户id
     * @param roleIds 关联角色
     */
    void saveUserRole(Long userId, List<Long> roleIds);

    /**
     * 保存角色关联菜单
     *
     * @param roleId 角色id
     * @param resourceIds 关联角色
     */
    void saveRoleResource(Long roleId, List<Long> resourceIds);

    /**
     * 删除角色关联的资源
     *
     * @param resourceIds 资源id集合
     */
    void delRoleRelaResource(List<Long> resourceIds);

    /**
     * 删除角色关联的资源
     *
     * @param roleId 角色id
     */
    void delRoleRelaResource(Long roleId);

    /**
     * 删除系统下的资源关联的角色
     *
     * @param systemId 资源id集合
     */
    void delRoleRelaSystemResource(Long systemId);

    /**
     * 根据角色删除用户与角色关系
     *
     * @param roleIds 角色集合
     */
    void delRoleRelaUser(List<Long> roleIds);

    /**
     * 根据用户删除用户与角色关系
     *
     * @param userId 用户id
     */
    void delUserRelaRole(Long userId);

    /**
     * 获取用户关联的角色
     *
     * @param userId 用户id
     * @return 用户关联的角色集合
     */
    List<UserRoleRelaDto> getUserRole(Long userId);

    /**
     * 获取用户具有资源url权限
     *
     * @param userId 用户id
     * 用户拥有的请求权限（url）
     */
    Set<String> getAllPermission(Long userId);

    /**
     * 获取角色资源树
     *
     * @param roleId 角色id
     * @return 角色资源树
     */
    List<ResourceDto> getRoleResource(Long roleId);

    /**
     * 获取角色关联的资源id集合
     *
     * @param roleId 角色id
     * @return 资源id集合
     */
    List<Long> getRoleResourceIds(Long roleId);
}
