package com.dubbo.api.auth.constant;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:25
 * @version:
 **/
public enum ResourceType {

    SYSTEM(0),           // 系统
    FIRST_MENU(1),       // 菜单
    SECOND_MENU(2),      // 二级菜单
    BUTTON(3);           // 按钮

    private Integer type;

    private ResourceType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

}
