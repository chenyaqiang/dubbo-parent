package com.dubbo.common.mapper;

import com.dubbo.common.dto.BaseDto;
import com.dubbo.common.dto.query.BaseQuery;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 19:25
 * @version:
 **/
public interface BaseMapper<T extends BaseDto> {

    /**
     * 插入
     *
     * @param dto 待插入记录
     * @return 插入的记录（可以带出数据库中数据）
     */
    int insert(T dto);

    /**
     * 根据id查询
     *
     * @param id 记录Id
     * @return 记录
     */
    T select(Long id);

    /**
     * 更新
     *
     * @param dto 待更新记录
     * @return 更新的记录数
     */
    int update(T dto);

    /**
     * 查询
     *
     * @param query 查询条件
     * @return 查询结果
     */
    List<T> list(BaseQuery query);

    /**
     * 统计
     *
     * @param query 统计条件
     * @return 统计数
     */
    Long count(BaseQuery query);

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
    int delete(@Param("id") Long id);
}
