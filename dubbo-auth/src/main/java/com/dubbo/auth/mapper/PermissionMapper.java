package com.dubbo.auth.mapper;

import com.dubbo.api.auth.dto.ResourceDto;
import com.dubbo.api.auth.dto.UserRoleRelaDto;
import com.dubbo.common.dto.BaseDto;
import com.dubbo.common.mapper.BaseMapper;

import java.util.List;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 11:26
 * @version:
 **/
public interface PermissionMapper extends BaseMapper<BaseDto> {

    /**
     * 删除用户关联角色
     *
     * @param userId 用户Id
     * @return 删除角色数
     */
    int delUserRelaRole(Long userId);

    /**
     * 从用户角色中删除角色
     *
     * @param roleId 待删除角色Id
     * @param userId 用户Id
     */
    int delRole(Long userId, Long roleId);

    /**
     * 保存用户关联角色
     *
     * @param userId 用户Id
     * @param roleIds 角色Id列表
     */
    void saveUserRole(Long userId, List<Long> roleIds);

    /**
     * 删除角色关联的资源
     *
     * @param roleId 角色Id
     * @return 删除资源数
     */
    int delResourceByRoleId(Long roleId);

    /**
     * 保存角色关联的资源
     *
     * @param roleId 角色Id
     * @param resourceIds 资源Id集合
     */
    void saveRoleResource(Long roleId, List<Long> resourceIds);

    /**
     * 删除角色与资源的对应关系
     *
     * @param resourceIds 资源id集合
     */
    void delRoleRelaResource(List<Long> resourceIds);

    /**
     * 根据系统id删除角色与系统下资源的对应关系
     *
     * @param systemId 资源id集合
     */
    void delRoleRelaSystemResource(Long systemId);

    /**
     * 根据角色删除角色与用户的对应关系
     *
     * @param roleIds 角色集合
     */
    void delRoleRelaUser(List<Long> roleIds);

    /**
     * 获取用户关联的角色
     *
     * @param userId 用户id
     * @return 用户关联的角色集合
     */
    List<UserRoleRelaDto> getUserRole(Long userId);

    /**
     * 获取用户拥有资源集合
     *
     * @param userId 用户id
     * @return 用户拥有资源集合
     */
    List<ResourceDto> getAllPermission(Long userId);

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
