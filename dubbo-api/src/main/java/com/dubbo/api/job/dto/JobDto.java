package com.dubbo.api.job.dto;

import com.dubbo.common.dto.BaseDto;

import java.util.Date;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 10:46
 * @version:
 **/
public class JobDto extends BaseDto {

    private static final long serialVersionUID = -3087658965883908267L;

    private String            name;                                    // 名称
    private String            group;                                   // 群组名称，服务群组及定时任务书所属群组，一个服务server对应一个group
    private String            code;                                    // 用户自定义任务code group+code确定一个具体的定时任务

    private Date startTime;                               // 开始时间，为null 是表示默认当前时间，立刻执行
    private Date              endTime;                                 // 结束时间

    private String            server;                                  // 任务对应服务

    private Integer           triggerType;                             // 触发器类型：0 表达式触发器,1 简单触发器(间隔几秒后再次执行job),2 定时触发器
    private String            cron;                                    // 定时任务表达式 (triggerType=0)
    private Integer           interval;                                // 简单触发器,job执行间隔时间,单位秒(triggerType=1)
    private Integer           repeatCount;                             // 重复次数,注意：如果为0表示不执行,-1表示不限制次数（直到过期）,默认为0。楼主设置成1,表示执行一次(triggerType=1)
    private Date              timingTime;                              // 定时时间点 (triggerType=2)

    private String            ext;                                     // 扩展字段

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getTriggerType() {
        return triggerType;
    }

    public void setTriggerType(Integer triggerType) {
        this.triggerType = triggerType;
    }

    public String getCron() {
        return cron;
    }

    public void setCron(String cron) {
        this.cron = cron;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public Integer getRepeatCount() {
        return repeatCount;
    }

    public void setRepeatCount(Integer repeatCount) {
        this.repeatCount = repeatCount;
    }

    public Date getTimingTime() {
        return timingTime;
    }

    public void setTimingTime(Date timingTime) {
        this.timingTime = timingTime;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    @Override
    public String toString() {
        return "JobDto{" +
                "name='" + name + '\'' +
                ", group='" + group + '\'' +
                ", code='" + code + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", server='" + server + '\'' +
                ", triggerType=" + triggerType +
                ", cron='" + cron + '\'' +
                ", interval=" + interval +
                ", repeatCount=" + repeatCount +
                ", timingTime=" + timingTime +
                ", ext='" + ext + '\'' +
                '}';
    }
}
