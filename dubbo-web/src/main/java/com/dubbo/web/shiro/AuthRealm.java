package com.dubbo.web.shiro;

import com.dubbo.api.auth.dto.SysUserDto;
import com.dubbo.api.auth.service.PermissionService;
import com.dubbo.api.auth.service.SysUserService;
import com.dubbo.web.util.WebUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Set;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 9:08
 * @version:
 **/
public class AuthRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private SysUserService sysUserService;

    @Autowired
    @Lazy
    private PermissionService permissionService;

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户信息
        SysUserDto user = WebUtil.getLoginUser();
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            Set<String> allPermissions = permissionService.getAllPermission(user.getId());
            // 全部权限
            info.addStringPermissions(allPermissions);
            return info;
        }
        return null;
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        SysUserDto user = sysUserService.getSysUserByLoginName(upToken.getUsername());
        if (null != user) {
            return new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(), getName());
        }

        return null;
    }

    /**
     * 清理授权信息，当用户id不为null，清除改用户的授权信息，当id为null，清理系统全部的授权信息
     *
     * @param userId 用户id
     */
    public void clearAuthorizationInfo(Long userId) {
        Cache<Object, AuthorizationInfo> cache = getAuthorizationCache();
        clearCache(userId, cache);
    }

    private void clearCache(Long userId, Cache<Object, ?> cache) {
        if (null != cache) {
            if (null != userId) {
                SysUserDto user = sysUserService.findById(userId);
                cache.remove(user.getLoginName());
            }
            else {
                // 清除全部授权
                cache.clear();
            }
        }
    }

    /**
     * 清理认证信息，当用户id不为null，清除该用户的认证信息，当id为null，清理系统全部的认证信息
     *
     * @param userId 用户id
     */
    public void clearAuthenticationInfo(Long userId) {
        Cache<Object, AuthenticationInfo> cache = getAuthenticationCache();
        clearCache(userId, cache);
    }
}
