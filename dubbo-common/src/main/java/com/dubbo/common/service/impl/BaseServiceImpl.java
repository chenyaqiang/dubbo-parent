package com.dubbo.common.service.impl;

import com.dubbo.common.dto.BaseDto;
import com.dubbo.common.dto.query.BaseQuery;
import com.dubbo.common.mapper.BaseMapper;
import com.dubbo.common.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-17 19:39
 * @version:
 **/
public class BaseServiceImpl<T extends BaseDto,Q extends BaseQuery> implements BaseService<T,Q> {

    @Autowired
    private BaseMapper<T> baseMapper;

    @Override
    public T save(T dto) {
        baseMapper.insert(dto);
        return dto;
    }

    @Override
    public T findById(Long id) {
        return baseMapper.select(id);
    }

    @Override
    public int update(T dto) {
        return baseMapper.update(dto);
    }

    @Override
    public Page<T> findByPage(Q query) {
        return null;
    }

    @Override
    public List<T> findList(Q query) {
        // 不分页
        query.setPage(null);
        return baseMapper.list(query);
    }

    @Override
    public int batchDelete(List<Long> ids) {
        return baseMapper.batchDelete(ids);
    }

    @Override
    public int delete(Long id) {
        return baseMapper.delete(id);
    }
}
