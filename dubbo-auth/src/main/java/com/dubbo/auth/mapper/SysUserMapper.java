package com.dubbo.auth.mapper;

import com.dubbo.api.auth.dto.SysUserDto;
import com.dubbo.common.mapper.BaseMapper;

import java.util.List;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 11:20
 * @version:
 **/
public interface SysUserMapper extends BaseMapper<SysUserDto> {

    /**
     * 修改用户状态
     *
     * @param userIds 用户Id集合
     * @param state 用户状态 0:冻结,1: 激活
     */
    void updateState(List<Long> userIds, Integer state);

    /**
     * 修改用户密码
     *
     * @param userId 用户Id
     * @param password 新密码
     */
    void updatePassword(Long userId, String password);

    /**
     * 更新用户登录信息
     *
     * @param userLoginInfo
     */
    void updateLoginInfo(SysUserDto userLoginInfo);

    /**
     * 根据用户登录账号查询用户
     *
     * @param loginName 用户登录账号
     * @return 用户详情
     */
    SysUserDto getSysUserByloginName(String loginName);
}
