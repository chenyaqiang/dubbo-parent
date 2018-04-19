/**
 * 
 */
package com.dubbo.common.dto.query;

import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Date;

/**
 * @Company: 浙江核新同花顺网络信息股份有限公司
 * @ClassName: BaseQuery.java
 * @Description: 基础查询类
 * @Author: jiangzheng@myhexin.com
 * @CreateDate 2017-7-7上午10:11:32
 * @version: 2.0
 */
public class BaseQuery implements Serializable {

    private static final long serialVersionUID = 997338194280600000L;

    private Date              createdTimeStart;                      // 插入时间--起
    private Date              createdTimeEnd;                        // 插入时间--止

    private Pageable          page;                                  // 分页信息，（没有分页不传，查询全部）

    private int               startIndex;                            // 这个是给分页limit开始记录

    public Date getCreatedTimeStart() {
        return createdTimeStart;
    }

    public void setCreatedTimeStart(Date createdTimeStart) {
        this.createdTimeStart = createdTimeStart;
    }

    public Date getCreatedTimeEnd() {
        return createdTimeEnd;
    }

    public void setCreatedTimeEnd(Date createdTimeEnd) {
        this.createdTimeEnd = createdTimeEnd;
    }

    public Pageable getPage() {
        return page;
    }

    public void setPage(Pageable page) {
        this.page = page;
    }

    public int getStartIndex() {

        if (null != page) {
            this.startIndex = page.getPageNumber() * page.getPageSize();
        }
        return startIndex;
    }

    @Override
    public String toString() {
        return "BaseQuery [createdTimeStart=" + createdTimeStart + ", createdTimeEnd=" + createdTimeEnd + ", page="
               + page + ", startIndex=" + startIndex + "]";
    }

}
