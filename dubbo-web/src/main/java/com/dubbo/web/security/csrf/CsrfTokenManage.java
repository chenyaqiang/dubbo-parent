package com.dubbo.web.security.csrf;

import com.dubbo.common.util.StringUtil;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.UUID;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 11:40
 * @version:
 **/
public class CsrfTokenManage {

    protected static final String DEFAULT_CSRF_COOKIE_NAME = "XSRF-TOKEN";

    protected static final String DEFAULT_CSRF_PARAMETER_NAME = "_xsrf";

    protected static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";

    private static Method setHttpOnlyMethod;

    private static boolean cookieHttpOnly;

    {
        setHttpOnlyMethod = ReflectionUtils.findMethod(Cookie.class, "setHttpOnly", boolean.class);
        if (setHttpOnlyMethod != null) {
            cookieHttpOnly = true;
        }
    }

    public static String loadToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, DEFAULT_CSRF_COOKIE_NAME);
        if (cookie == null) {
            return null;
        }
        String token = cookie.getValue();
        if (StringUtil.isBlank(token)) {
            return null;
        }
        return token;
    }

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }

    public static void saveToken(String token, HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = new Cookie(DEFAULT_CSRF_COOKIE_NAME, token);
        cookie.setSecure(request.isSecure());
        if (StringUtil.isBlank(token)) {
            cookie.setMaxAge(0);
        } else {
            cookie.setMaxAge(-1);
        }
        if (cookieHttpOnly && setHttpOnlyMethod != null) {
            ReflectionUtils.invokeMethod(setHttpOnlyMethod, cookie, Boolean.TRUE);
        }
        response.addCookie(cookie);
    }

    public static String getPageToken(HttpServletRequest request) {
        String pageToken = request.getHeader(DEFAULT_CSRF_HEADER_NAME);
        if (StringUtil.isBlank(pageToken)) {
            pageToken = request.getParameter(DEFAULT_CSRF_PARAMETER_NAME);
        }
        return pageToken;
    }
}
