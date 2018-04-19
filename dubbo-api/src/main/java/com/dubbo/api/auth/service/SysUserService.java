package com.dubbo.api.auth.service;

import com.dubbo.api.auth.dto.SysUserDto;
import com.dubbo.api.auth.dto.query.SysUserQuery;
import com.dubbo.common.service.BaseService;

import java.util.List;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:42
 * @version:
 **/
public interface SysUserService extends BaseService<SysUserDto, SysUserQuery> {

    /**
     * 修改用户状态
     *
     * @param userIds 用户Id集合
     * @param state   用户状态 0:冻结,1: 激活
     */
    void updateState(List<Long> userIds, Integer state);

    /**
     * 修改用户密码
     *
     * @param userId   用户Id
     * @param password 新密码
     */
    void updatePassword(Long userId, String password);

    /**
     * 更新用户登录信息
     *
     * @param userLoginInfo 包含登录的用户信息
     */
    void updateLoginInfo(SysUserDto userLoginInfo);

    /**
     * 根据用户登录账号查询用户
     *
     * @param loginName 用户登录账号
     * @return 用户详情
     */
    SysUserDto getSysUserByLoginName(String loginName);
}
