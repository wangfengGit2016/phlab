package com.ylhl.phlab.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("系统日志")
@Data
public class SysLogParam {
    @ApiModelProperty(value = "页长",reference = "page")
    protected long size = 10;
    @ApiModelProperty(value = "当前页",reference = "page")
    protected long current = 1;
    @ApiModelProperty(value = "")
    private String id;
    @ApiModelProperty(value = "操作模块",reference = "page")
    private String module;
    @ApiModelProperty("操作详情")
    private String content;
    @ApiModelProperty(value = "操作人ID",reference = "page")
    private String userId;
    @ApiModelProperty(value = "操作人账号",reference = "page")
    private String userName;
    @ApiModelProperty("状态 0成功 1失败")
    private String status;
    @ApiModelProperty("操作人ip")
    private String ip;

    @ApiModelProperty(value = "开始时间",reference = "page")
    private Date startTime;
    @ApiModelProperty(value = "结束时间",reference = "page")
    private Date endTime;

}
