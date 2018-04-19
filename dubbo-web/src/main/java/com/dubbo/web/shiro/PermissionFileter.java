package com.dubbo.web.shiro;

import com.dubbo.api.constant.ReturnCode;
import com.dubbo.common.dto.BaseReturnDto;
import com.dubbo.common.util.JsonUtil;
import com.dubbo.common.util.StringUtil;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 9:23
 * @version:
 **/
public class PermissionFileter extends PermissionsAuthorizationFilter {

    private static final Logger logger = LoggerFactory.getLogger(PermissionFileter.class);

    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response,
                                   Object mappedValue) throws IOException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String path = httpRequest.getRequestURI();

        // 去掉工程域名
        if (StringUtil.isNotBlank(httpRequest.getContextPath())) {
            path = path.substring(httpRequest.getContextPath().length());
        }

        // 判断是否有权限
        boolean isAccess = super.isAccessAllowed(request, response, new String[] { path });
        logger.info("request path:{} isAccess:{}", path, isAccess);

        return isAccess;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response,
                                     Object mappedValue) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        BaseReturnDto<Void> brd = new BaseReturnDto<>(ReturnCode.PERMISSION.getCode(),
                "operation is forbidden, please confirm your permission!");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(JsonUtil.toJson(brd));
        return Boolean.FALSE;
    }
}
