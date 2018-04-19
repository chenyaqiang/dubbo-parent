package com.dubbo.auth.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:57
 * @version:
 **/
public class ServiceContainer {

    private static final Logger logger = LoggerFactory.getLogger(ServiceContainer.class);

    public static void main(String[] args) {
        try {
            // 初始化Spring
            @SuppressWarnings("resource")
            ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/applicationContext.xml");
            ctx.start();
            logger.info("auth dubbo provider is running...");
        } catch (Exception ex) {
            logger.error("auth dubbo provider start fail...", ex);
        }
    }
}
