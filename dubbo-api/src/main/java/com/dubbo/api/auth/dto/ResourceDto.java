package com.dubbo.api.auth.dto;

import com.dubbo.common.dto.BaseDto;

import java.util.List;

/**
 * @Description:资源dto,管理系统，菜单，按钮等
 * @Author: wb
 * @CreateDate: 2018-04-18 10:31
 * @version:
 **/
public class ResourceDto extends BaseDto {

    private static final long serialVersionUID = -652332083239342819L;

    private String name;                                    // 菜单名称
    private String title;                                   // 菜单展示名称
    private String icon;                                    // 菜单图标样式
    private String path;                                    // 菜单请求地址
    private Long parentId;                                  // 父菜单id
    private Integer sort;                                   // 菜单排序
    private Integer type;                                   // 资源类型，0:系统 ; 1：一级菜单; 2:二级菜单; 3: 按钮
    private String bindPath;                                // 绑定或者叫依赖的path,多个path使用逗号(,)隔开
    private boolean expand;                                 // 是否展开
    private boolean check;                                  // 是否被选中
    private List<ResourceDto> children;                     // 子菜单/菜单下按钮


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBindPath() {
        return bindPath;
    }

    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public List<ResourceDto> getChildren() {
        return children;
    }

    public void setChildren(List<ResourceDto> children) {
        this.children = children;
    }

    @Override
    public String toString() {
        return "ResourceDto{" +
                "name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", icon='" + icon + '\'' +
                ", path='" + path + '\'' +
                ", parentId=" + parentId +
                ", sort=" + sort +
                ", type=" + type +
                ", bindPath='" + bindPath + '\'' +
                ", expand=" + expand +
                ", check=" + check +
                ", children=" + children +
                '}';
    }
}
