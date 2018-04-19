package com.dubbo.web.security.csrf;

import com.dubbo.common.util.StringUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:crsf 配置
 * @Author: wb
 * @CreateDate: 2018-04-19 11:36
 * @version:
 **/
public class CsrfConfigManager {

    private List<String> excludeUrls;

    private List<String> methods;

    public boolean matches(HttpServletRequest request) {
        if (!methods.contains(request.getMethod())) {
            return false;
        }

        if (null != excludeUrls && !excludeUrls.isEmpty()) {
            // 去掉工程域名
            String path = StringUtil.isBlank(request.getContextPath()) ? request.getRequestURI() : request.getRequestURI().substring(request.getContextPath().length());
            if (excludeUrls.contains(path)) {
                return false;
            }
        }
        return true;
    }

    public List<String> getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(List<String> excludeUrls) {
        this.excludeUrls = excludeUrls;
    }

    public List<String> getMethods() {
        return methods;
    }

    public void setMethods(List<String> methods) {
        this.methods = methods;
    }
}
