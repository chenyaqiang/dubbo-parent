package com.dubbo.common.util.spring;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-19 11:04
 * @version:
 **/
public class CustomizedPropertyConfigurer extends PropertyPlaceholderConfigurer {

    private static Properties properties;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties properties)
            throws BeansException {
        super.processProperties(beanFactory, properties);
        this.properties = properties;
    }

    public static Properties getProps() {
        return properties;
    }

    public static Object get(String name) {
        return properties.get(name);
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    public static String getProperty(String name, String defaultValue) {
        return properties.getProperty(name, defaultValue);
    }
}
