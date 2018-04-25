package com.dubbo.job.mapper;

import com.dubbo.api.job.dto.JobDto;
import com.dubbo.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-25 11:07
 * @version:
 **/
public interface JobMapper extends BaseMapper<JobDto> {

    /**
     * 获取job
     *
     * @param code 任务code
     * @param group 任务群组
     */
    JobDto getJobformGroup(@Param("code") String code, @Param("group") String group);
}
