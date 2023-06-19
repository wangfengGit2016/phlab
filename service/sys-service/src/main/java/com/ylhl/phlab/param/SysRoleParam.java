package com.ylhl.phlab.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
@ApiModel("角色")
@Data
public class SysRoleParam{
    @ApiModelProperty(value = "页长",reference = "page")
    protected long size = 10;
    @ApiModelProperty(value = "当前页",reference = "page")
    protected long current = 1;
    @ApiModelProperty(value = "角色id",reference = "update,detail,delete,tree,assign")
    private String id;
    @ApiModelProperty(value = "角色名",reference = "update,page")
    private String roleName;
    @ApiModelProperty(value = "角色编码",reference = "update")
    private String roleCode;
    @ApiModelProperty(value = "角色描述",reference = "update")
    private String roleDesc;
    @ApiModelProperty(value = "父节点ID",reference = "update")
    private String parentId;

    @ApiModelProperty(value = "菜单权限集合,字符串list",reference = "assign")
    private List<String> menus;


}
