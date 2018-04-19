/**
 * 
 */
package com.dubbo.api.auth.service;

import com.dubbo.api.auth.dto.ResourceDto;
import com.dubbo.api.auth.dto.query.ResourceQuery;
import com.dubbo.common.service.BaseService;

import java.util.List;

/**
 * @Description:
 * @Author: wb
 * @CreateDate: 2018-04-18 10:46
 * @version:
 **/
public interface ResourceService extends BaseService<ResourceDto, ResourceQuery> {

    /**
     * 查询资源树,当userId !=null时,查询用户所拥有的资源树,当userId==null，查询系统最大的资源树；<br />
     * 当systemId != null时，查询ResourceDto.type = 0 && ResourceDto.id=systemId 的系统资源树
     * 
     * @param userId 用户id
     * @param systemId 系统id
     * @return 资源树
     */
    List<ResourceDto> findResourceTree(Long userId, Long systemId);

}
