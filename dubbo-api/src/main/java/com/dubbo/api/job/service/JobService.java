package com.dubbo.api.job.service;

import com.dubbo.api.job.dto.JobDto;
import com.dubbo.api.job.dto.JobQuery;
import com.dubbo.common.service.BaseService;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 10:51
 * @version:
 **/
public interface JobService extends BaseService<JobDto,JobQuery> {

    /**
     * 存在修改，不存在新增
     *
     * @param jobInfo 定时任务
     * @return 定时任务
     * @throws JobException
     */
    JobDto upsert(JobDto jobInfo);

    /**
     * 删除job
     *
     * @param code 任务code
     * @param group 任务群组
     * @return 删除记录数
     */
    int delete(String code, String group);

    /**
     * 暂停job
     *
     * @param id 任务id
     */
    void pause(Long id);

    /**
     * 根据group暂停job
     *
     * @param group 任务群组
     */
    void pause(String group);

    /**
     * 暂停job
     *
     * @param code 任务code
     * @param group 任务群组
     */
    void pause(String code, String group);

    /**
     * 暂停所有job
     *
     */
    void pauseAll();

    /**
     * 恢复job
     *
     * @param id 任务id
     */
    void resume(Long id);

    /**
     * 根据group恢复job
     *
     * @param group 任务群组
     */
    void resume(String group);

    /**
     * 恢复job
     *
     * @param code 任务code
     * @param group 任务群组
     */
    void resume(String code, String group);



    /**
     * 恢复所有job
     *
     */
    void resumeAll();

    /**
     * 运行一次job
     *
     * @param jobId
     * @param groupName
     * @param server
     * @return
     */
    boolean runOnce(String jobId, String groupName, String server);

    /**
     * 启动所有job
     *
     * @return
     */
    boolean startAll();

    /**
     * 关闭所有job
     *
     * @return
     */
    boolean shutdownAll();

    /**
     * 获取job
     *
     * @param code 任务code
     * @param group 任务群组
     */
    JobDto get(String code, String group);
}
