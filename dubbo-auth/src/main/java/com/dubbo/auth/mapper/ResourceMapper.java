package com.dubbo.auth.mapper;

import com.dubbo.api.auth.dto.ResourceDto;
import com.dubbo.common.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 11:24
 * @version:
 **/
public interface ResourceMapper extends BaseMapper<ResourceDto> {

    /**
     * 单删除功能
     *
     * @param id
     * @return 删除记录数
     */
    int delResource(Long id);

    /**
     * 删除系统及系统下的功能
     *
     * @param id
     * @return 删除记录数
     */
    int delSystem(Long id);

    /**
     * 查询资源数
     *
     * @param  params 查询条件
     * @return 资源树
     */
    List<ResourceDto> findResourceTree(Map<String, Long> params);
}
