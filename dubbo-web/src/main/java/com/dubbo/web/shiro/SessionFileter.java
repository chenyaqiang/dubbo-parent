package com.dubbo.web.shiro;

import com.dubbo.api.constant.ReturnCode;
import com.dubbo.common.dto.BaseReturnDto;
import com.dubbo.common.util.JsonUtil;
import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 9:24
 * @version:
 **/
public class SessionFileter extends UserFilter {

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        BaseReturnDto<Void> brd = new BaseReturnDto<>(ReturnCode.TIME_OUT.getCode(),
                "session time out, please login again!");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().write(JsonUtil.toJson(brd));
        return Boolean.FALSE;
    }
}
