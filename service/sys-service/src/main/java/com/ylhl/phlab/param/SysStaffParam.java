package com.ylhl.phlab.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("职工")
@Data
public class SysStaffParam {
    @ApiModelProperty(value = "页长",reference = "page")
    protected long size = 10;
    @ApiModelProperty(value = "当前页",reference = "page")
    protected long current = 1;
    @ApiModelProperty(value = "职工id",reference = "tree,update,delete,detail")
    private String id;
    @ApiModelProperty(value = "用户id",reference = "tree,update,delete,detail")
    private String userId;
    @ApiModelProperty(value = "用户名",reference = "tree,update,delete,detail")
    private String name;
    @ApiModelProperty(value = "联系方式",reference = "create")
    private String staffPhone;
    @ApiModelProperty(value = "证件号码",reference = "create")
    private String idCard;
    @ApiModelProperty(value = "任职状态",reference = "page,create")
    private String officeStatus;
    @ApiModelProperty(value = "科室id",reference = "create")
    private String deptId;
    @ApiModelProperty(value = "职位",reference = "create")
    private String position;
    @ApiModelProperty(value = "入职时间",reference = "create")
    private String entryTime;
    @ApiModelProperty(value = "离职时间",reference = "create")
    private String departTime;
    @ApiModelProperty(value = "状态",reference = "update")
    private String status;
}
