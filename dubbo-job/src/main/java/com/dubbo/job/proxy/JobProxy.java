package com.dubbo.job.proxy;

import com.alibaba.dubbo.config.spring.ReferenceBean;
import com.dubbo.api.job.dto.JobDto;

import com.dubbo.api.job.service.Job;
import com.dubbo.common.util.spring.SpringContextHolder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 11:09
 * @version:
 **/
public class JobProxy extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(JobProxy.class);

    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // 获取job明细
        JobDetail jobDetail = context.getJobDetail();
        if (jobDetail == null) {
            logger.error("获取任务明细失败,JobExecutionContext:{}", context);
            return;
        }

        JobDataMap dataMap = jobDetail.getJobDataMap();
        JobDto jobInfo = (JobDto) dataMap.get("jobInfo");

        try {
            // 获取远程服务
            Job job = getServerBean(jobInfo.getGroup());
            if (job == null) {
                logger.error("任务执行失败,服务不存在,job:{}", jobInfo);
            } else {
                // 执行远程业务
                job.execute(jobInfo.getExt());
            }
        } catch (Exception e) {
            logger.error("任务执行失败,job:{}", jobInfo, e);
        }
    }

    private Job getServerBean(String group) throws Exception {
        ReferenceBean<Job> referenceBean = new ReferenceBean<>();
        referenceBean.setApplicationContext(SpringContextHolder.getApplicationContext());
        referenceBean.setInterface(Job.class);
        referenceBean.setGroup(group);
        referenceBean.afterPropertiesSet();
        return referenceBean.get();

    }
}
