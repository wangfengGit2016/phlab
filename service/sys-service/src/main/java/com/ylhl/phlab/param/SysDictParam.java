package com.ylhl.phlab.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("字典参数")
@Data
public class SysDictParam {
    @ApiModelProperty(value = "页长",reference = "page")
    protected long size = 10;
    @ApiModelProperty(value = "当前页",reference = "page")
    protected long current = 1;

    @ApiModelProperty(value = "字典ID",reference = "update,delete,itemList,itemUpdate,itemDelete")
    private String id;
    @ApiModelProperty(value = "父节点ID",reference = "create,itemCreate,update,itemUpdate")
    private String parentId;
    @ApiModelProperty(value = "编码",reference = "create,itemCreate,update,itemUpdate")
    private String dictCode;
    @ApiModelProperty(value = "值",reference = "create,itemCreate,update,itemUpdate")
    private String value;
    @ApiModelProperty(value = "标签",reference = "create,itemCreate,update,itemUpdate")
    private String label;
    @ApiModelProperty(value = "类型",reference = "create,page,itemCreate,update,itemUpdate")
    private String type;
    @ApiModelProperty(value = "描述",reference = "create,itemCreate,update,itemUpdate")
    private String description;
    @ApiModelProperty(value = "备注",reference = "create,itemCreate,update,itemUpdate")
    private String remark;
    @ApiModelProperty(value = "是否根节点",reference = "create,itemCreate,update,itemUpdate")
    private String root;
    @ApiModelProperty(value = "顺序",reference = "create,itemCreate,update,itemUpdate")
    private Integer sort;
    @ApiModelProperty(value = "状态 1 启用 0停用",reference = "create,itemCreate,update,itemUpdate")
    private String status;

}
