package com.dubbo.api.job.constant;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 10:47
 * @version:
 **/
public enum TriggerType {

    CRON(0, "表达式触发器"), SIMPLE(1, "简单触发器"), TIME(2, "定时触发器");

    private int type;

    private String desc;

    private TriggerType(int type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return type;
    }

    public String getDesc() {
        return desc;
    }

}
