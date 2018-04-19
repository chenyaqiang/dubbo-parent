package com.dubbo.auth.mapper;

import com.dubbo.api.auth.dto.RoleDto;
import com.dubbo.common.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 11:23
 * @version:
 **/
public interface RoleMapper extends BaseMapper<RoleDto> {

    RoleDto getRoleByName(@Param("name") String name);
}
