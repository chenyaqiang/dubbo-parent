package com.dubbo.web.constant;

import com.dubbo.common.util.spring.CustomizedPropertyConfigurer;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 10:57
 * @version:
 **/
public class Constant {

    private Constant() {
        super();
    }

    // session user key
    public static final String AUTH_SESSION_USER = "auth_session_user";

    // session超时时间，默认30min
    public static int authSessionTime = Integer.parseInt(CustomizedPropertyConfigurer.getProperty("auth.session.time", "1800000"));
}
