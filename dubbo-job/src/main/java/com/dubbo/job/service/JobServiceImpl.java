package com.dubbo.job.service;

import com.dubbo.api.job.constant.TriggerType;
import com.dubbo.api.job.dto.JobDto;
import com.dubbo.api.job.dto.JobQuery;
import com.dubbo.api.job.exception.JobAlreadyExistsException;
import com.dubbo.api.job.exception.JobException;
import com.dubbo.api.job.service.JobService;
import com.dubbo.common.service.impl.BaseServiceImpl;
import com.dubbo.job.mapper.JobMapper;
import com.dubbo.job.proxy.JobProxy;
import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 11:16
 * @version:
 **/
public class JobServiceImpl extends BaseServiceImpl<JobDto, JobQuery> implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    /**
     * 定时任务管理器
     */
    @Autowired
    private Scheduler quartzScheduler;

    @Autowired
    private JobMapper jobMapper;

    @Override
    public JobDto save(JobDto jobInfo) {
        // 生成定时任务主键
        JobKey jobKey = JobKey.jobKey(jobInfo.getCode(), jobInfo.getGroup());

        try {
            if (!quartzScheduler.checkExists(jobKey)) {
                jobInfo.setCreateTime(new Date());
                // 保存消息
                jobMapper.insert(jobInfo);
                // 服务链接
                JobDataMap map = new JobDataMap();
                map.put("jobInfo", jobInfo);
                // 构造job明细
                JobDetail jobDetail = JobBuilder.newJob(JobProxy.class).setJobData(map).withIdentity(jobKey).build();
                // 生成触发器主键
                TriggerKey triggerkey = TriggerKey.triggerKey(jobInfo.getCode(), jobInfo.getGroup());
                // 构造触发器，并将job明细和触发器添加到quartz
                quartzScheduler.scheduleJob(jobDetail, buildTrigger(jobInfo, triggerkey));
            } else {// job已存在
                logger.error("add job fail, job existing,code:{}, group:{}", jobInfo.getCode(), jobInfo.getGroup());
                throw new JobAlreadyExistsException("添加定时任务失败，任务已存在！");
            }

        } catch (ObjectAlreadyExistsException e) {
            logger.error("add job fail, job existing,code:{}, group:{}", jobInfo.getCode(), jobInfo.getGroup());
            throw new JobAlreadyExistsException("添加定时任务失败，任务已存在！");
        } catch (SchedulerException e) {
            logger.error("add job fail, code:{}, group:{}", jobInfo.getCode(), jobInfo.getGroup());
            throw new JobException("添加定时任务失败！");
        }
        return jobInfo;
    }

    @Override
    public int update(JobDto jobInfo) {

        // 生成定时任务主键
        JobKey jobKey = JobKey.jobKey(jobInfo.getCode(), jobInfo.getGroup());

        int updates = 0;
        try {
            if (quartzScheduler.checkExists(jobKey)) {
                jobInfo.setUpdateTime(new Date());
                updates = super.update(jobInfo);

                if (updates > 0) {
                    // 生成触发器主键
                    TriggerKey triggerkey = TriggerKey.triggerKey(jobInfo.getCode(), jobInfo.getGroup());
                    // 获取触发器
                    Trigger trigger = quartzScheduler.getTrigger(triggerkey);
                    if (trigger != null) {
                        // 更新job
                        quartzScheduler.rescheduleJob(triggerkey, buildTrigger(jobInfo, triggerkey));
                    } else {// 获取触发器失败
                        logger.error("update job fail, when get the trigger, code:{}, group:{}", jobInfo.getCode(),
                                jobInfo.getGroup());
                        throw new JobException("修改定时任务,获取触发器失败！");
                    }
                } else {// job不存在
                    quartzScheduler.deleteJob(jobKey);
                    logger.error("update job fail, code:{}, group:{}", jobInfo.getCode(), jobInfo.getGroup());
                    throw new JobException("修改定时任务失败,任务不存在！");
                }

            } else {// job不存在
                logger.error("update job fail, code:{}, group:{}", jobInfo.getCode(), jobInfo.getGroup());
                throw new JobException("修改定时任务失败,任务不存在！");
            }
        } catch (SchedulerException e) {
            logger.error("update job fail, code:{}, group:{}", jobInfo.getCode(), jobInfo.getGroup());
            throw new JobException("修改定时任务失败！");
        }

        return updates;
    }

    @Override
    public JobDto upsert(JobDto jobInfo) {
        // 生成定时任务主键
        JobKey jobKey = JobKey.jobKey(jobInfo.getCode(), jobInfo.getGroup());

        try {
            if (quartzScheduler.checkExists(jobKey)) {
                this.update(jobInfo);
            } else {
                this.save(jobInfo);
            }
        } catch (SchedulerException e) {
            logger.error("upsert job fail, code:{}, group:{}", jobInfo.getCode(), jobInfo.getGroup());
            throw new JobException("操作定时任务失败！");
        } catch (JobAlreadyExistsException e) {
            // 重新更新
            this.update(jobInfo);
        } catch (JobException e) {
            // 重新插入
            try {
                this.save(jobInfo);
            } catch (JobAlreadyExistsException jae) {
                // 不处理
            }
        }

        return jobInfo;
    }

    @Override
    public int delete(Long id) {
        JobDto jobInfo = this.findById(id);
        return delete(jobInfo);
    }

    @Override
    public int delete(String code, String group) {
        JobDto jobInfo = this.get(code, group);
        return delete(jobInfo);
    }

    @Override
    public void pause(Long id) {
        JobDto jobInfo = this.findById(id);
        if (null == jobInfo) {
            throw new JobException("暂停任务失败，任务不存在！");
        }

        this.pause(jobInfo.getCode(), jobInfo.getGroup());
    }

    @Override
    public void pause(String group) {
        try {
            // 根据group暂停job
            quartzScheduler.pauseJobs(GroupMatcher.jobGroupEquals(group));
        } catch (Exception e) {
            logger.error("pause job fail,  group:{}", group, e);
            throw new JobException("暂停定时任务失败！");
        }
    }

    @Override
    public void pause(String code, String group) {
        // 生成定时任务主键
        JobKey jobkey = JobKey.jobKey(code, group);
        try {
            // 校验job是否存在
            if (quartzScheduler.checkExists(jobkey)) {
                // 暂停定时任务
                quartzScheduler.resumeJob(jobkey);
            }
            else {// job不存在
                logger.info("job not exist, code:{}, group:{}", code, group);
            }
        } catch (Exception e) {
            logger.error("resume job fail, code:{}, group:{}", code, group, e);
            throw new JobException("恢复定时任务失败！");
        }
    }

    @Override
    public void pauseAll() {
        try {
            // 暂停所有定时任务
            quartzScheduler.pauseAll();
        } catch (Exception e) {
            logger.error("pause all job fail", e);
            throw new JobException("暂停所有定时任务失败！");
        }
    }

    @Override
    public void resume(Long id) {
        JobDto jobInfo = this.findById(id);
        if (null == jobInfo) {
            throw new JobException("暂停任务失败，任务不存在！");
        }

        this.resume(jobInfo.getCode(), jobInfo.getGroup());
    }

    @Override
    public void resume(String group) {
        try {
            // 根据group暂停job
            quartzScheduler.resumeJobs(GroupMatcher.jobGroupEquals(group));
        } catch (Exception e) {
            logger.error("resume job fail,  group:{}", group, e);
            throw new JobException("恢复定时任务失败！");
        }
    }

    @Override
    public void resume(String code, String group) {
        // 生成定时任务主键
        JobKey jobkey = JobKey.jobKey(code, group);
        try {
            // 校验job是否存在
            if (quartzScheduler.checkExists(jobkey)) {
                // 暂停定时任务
                quartzScheduler.resumeJob(jobkey);
            }
            else {// job不存在
                logger.info("job not exist, code:{}, group:{}", code, group);
            }
        } catch (Exception e) {
            logger.error("resume job fail, code:{}, group:{}", code, group, e);
            throw new JobException("恢复定时任务失败！");
        }
    }

    @Override
    public void resumeAll() {
        try {
            // 暂停所有定时任务
            quartzScheduler.resumeAll();
        } catch (Exception e) {
            logger.error("resume all job fail", e);
            throw new JobException("恢复所有定时任务失败！");
        }
    }

    @Override
    public boolean runOnce(String jobId, String groupName, String server) {
        return false;
    }

    @Override
    public boolean startAll() {
        return false;
    }

    @Override
    public boolean shutdownAll() {
        return false;
    }

    @Override
    public JobDto get(String code, String group) {
        return jobMapper.getJobformGroup(code, group);
    }

    /**
     * 构造触发器
     *
     * @param job
     * @param triggerkey
     * @return
     */
    private Trigger buildTrigger(JobDto job, TriggerKey triggerkey) {
        // 生成触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
        triggerBuilder.withIdentity(triggerkey);

        // 开始时间
        if (job.getStartTime() != null) {
            triggerBuilder.startAt(job.getStartTime());
        } else {
            triggerBuilder.startNow();
        }

        if (job.getEndTime() != null) {// 结束时间
            triggerBuilder.endAt(job.getEndTime());
        }
        if (TriggerType.CRON.getType() == job.getTriggerType()) {// 表达式触发器

            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()));
        } else if (TriggerType.TIME.getType() == job.getTriggerType()) {
            String cronFormat = "ss mm HH dd MM ? yyyy";
            String cron = new SimpleDateFormat(cronFormat).format(job.getTimingTime());
            triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(cron));
        } else if (TriggerType.SIMPLE.getType() == job.getTriggerType()) {// 简单触发器

            SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule();
            builder.withIntervalInSeconds(job.getInterval());

            if (null == job.getRepeatCount()) {
                builder.repeatForever();
            } else {
                builder.withRepeatCount(job.getRepeatCount());
            }

            triggerBuilder.withSchedule(builder);
        }

        return triggerBuilder.build();
    }

    private int delete(JobDto jobInfo) {
        // 删除记录数
        int deletes = 0;

        if (null != jobInfo) {
            deletes = super.delete(jobInfo.getId());
            // 生成定时任务主键
            JobKey jobKey = JobKey.jobKey(jobInfo.getCode(), jobInfo.getGroup());
            try {
                if (quartzScheduler.checkExists(jobKey)) {
                    quartzScheduler.deleteJob(jobKey);
                }
            } catch (SchedulerException e) {
                logger.error("delete job fail, id:{}", jobInfo.getId());
                throw new JobException("修改定时任务失败！");
            }
        }
        return deletes;
    }
}
