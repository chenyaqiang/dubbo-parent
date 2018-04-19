package com.dubbo.web.util;

import com.dubbo.api.auth.dto.SysUserDto;
import com.dubbo.web.constant.Constant;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 10:55
 * @version:
 **/
public class WebUtil {

    private static final Logger logger = LoggerFactory.getLogger(WebUtil.class);

    private WebUtil(){
        super();
    }

    /**
     * 设置session用户
     *
     * @param user 登陆用户
     */
    public static void setSessionUser(SysUserDto user) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setTimeout(Constant.authSessionTime);
        session.setAttribute(Constant.AUTH_SESSION_USER, user);
    }

    public static void setSessionAuthCode(String authCode) {
        Subject currentUser = SecurityUtils.getSubject();
        Session session = currentUser.getSession();
        session.setAttribute("authCode", authCode);
    }

    /**
     * 获取登陆用户
     *
     * @return
     */
    public static SysUserDto getLoginUser() {
        return (SysUserDto) SecurityUtils.getSubject().getSession().getAttribute(Constant.AUTH_SESSION_USER);
    }

    /**
     * 获取登陆用户
     *
     * @return
     */
    public static Long getLoginUserId() {

        return getLoginUser().getId();
    }


    /**
     * 获取请求ip
     */
    public static String getRequestIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
