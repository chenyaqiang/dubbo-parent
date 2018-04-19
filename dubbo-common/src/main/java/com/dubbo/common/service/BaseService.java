package com.dubbo.common.service;

import com.dubbo.common.dto.BaseDto;
import com.dubbo.common.dto.query.BaseQuery;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 19:35
 * @version:
 **/
public interface BaseService<T extends BaseDto, Q extends BaseQuery> {

    /**
     * 插入保存对象
     *
     * @param dto  对象信息
     */
    T save(T dto);

    /**
     * 获取对象详情
     *
     * @param id 对象Id
     * @return 对象详情
     */
    T findById(Long id);

    /**
     * 更新对象
     *
     * @param dto   对象信息
     */
    int update(T dto);

    /**
     * 分页查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    Page<T> findByPage(Q query);

    /**
     * 查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    List<T> findList(Q query);

    /**
     * 批量删除（控制ids不超过1000）
     *
     * @param ids 待删除Id列表
     * @return 删除记录数
     */
    int batchDelete(List<Long> ids);

    /**
     * 单个删除
     *
     * @param id 待删除Id
     * @return 删除记录数
     */
    int delete(Long id);
}
