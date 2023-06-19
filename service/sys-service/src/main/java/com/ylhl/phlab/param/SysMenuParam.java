package com.ylhl.phlab.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@ApiModel("菜单")
@Data
public class SysMenuParam {

    @ApiModelProperty(value = "页长",reference = "page")
    protected long size = 10;
    @ApiModelProperty(value = "当前页",reference = "page")
    protected long current = 1;

    @ApiModelProperty(value = "菜单ID",reference = "update,delete,detail")
    private String id;
    @ApiModelProperty(value = "菜单名称",reference = "create,update")
    private String title;
    @ApiModelProperty(value = "接口地址",reference = "create,update")
    private String api;
    @ApiModelProperty(value = "权限标识",reference = "create,update")
    private String permission;
    @ApiModelProperty(value = "菜单路由",reference = "create,update")
    private String path;
    @ApiModelProperty(value = "父菜单ID",reference = "create,update")
    private String parentId;
    @ApiModelProperty(value = "父菜单名",reference = "create,update")
    private String parentName;
    @ApiModelProperty(value = "图标",reference = "create,update")
    private String icon;
    @ApiModelProperty(value = "是否显示：0是 1否",reference = "create,update")
    private String showFlag;
    @ApiModelProperty(value = "排序值",reference = "create,update")
    private Integer sort;
    @ApiModelProperty(value = "是否缓冲",reference = "create,update")
    private String keepAlive;
    @ApiModelProperty(value = "菜单类型 0：目录 1：菜单 2: 按钮",reference = "create,update")
    private String type;

}
