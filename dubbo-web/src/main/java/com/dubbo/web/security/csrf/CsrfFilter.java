package com.dubbo.web.security.csrf;

import com.dubbo.api.constant.ReturnCode;
import com.dubbo.common.dto.BaseReturnDto;
import com.dubbo.common.util.JsonUtil;
import com.dubbo.common.util.StringUtil;
import com.dubbo.common.util.spring.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 13:06
 * @version:
 **/
public class CsrfFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(CsrfFilter.class);

    protected static final String RESPONSE_TOKEN_NAME = "X-CSRF-TOKEN";


    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CsrfConfigManager csrfConfigManager = SpringContextHolder.getBean("csrfConfigManager");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String token = CsrfTokenManage.loadToken(httpRequest);
        logger.info("doFilter1 -- before---token:{}", token);
        if (csrfConfigManager.matches(httpRequest)) {
            String pageToken = CsrfTokenManage.getPageToken(httpRequest);
            logger.info("doFilter2 -- before---pageToken:{}", pageToken);
            if (StringUtil.isBlank(pageToken) || !pageToken.equals(token)) {
                BaseReturnDto<Void> brd = new BaseReturnDto<>(ReturnCode.PERMISSION.getCode(),
                        "operation is forbidden!");
                httpResponse.setCharacterEncoding("UTF-8");
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write(JsonUtil.toJson(brd));

                return;
            }
        }

        if (StringUtil.isBlank(token)) {
            token = CsrfTokenManage.generateToken();
            CsrfTokenManage.saveToken(token, httpRequest, httpResponse);
        }

        logger.info("doFilter -- end---token:{}", token);
        httpResponse.setHeader(CsrfTokenManage.DEFAULT_CSRF_HEADER_NAME, token);
        chain.doFilter(request, response);
    }

    public void destroy() {
        logger.info("CsrfFilter destroy");
    }
}
