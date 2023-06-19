package com.ylhl.phlab.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("消息")
@Data
public class SysMessageParam {
    @ApiModelProperty(value = "页长",reference = "page")
    protected long size = 10;
    @ApiModelProperty(value = "当前页",reference = "page")
    protected long current = 1;

    @ApiModelProperty(value = "区域ID",reference = "readAll")
    private String id;
    @ApiModelProperty(value = "区域ID",reference = "readAll")
    private String type;


    @ApiModelProperty(value = "区域ID",reference = "readAll")
    private String regionId;
    @ApiModelProperty(value = "区域ID",reference = "readAll")
    private String title;
    @ApiModelProperty(value = "区域ID",reference = "readAll")
    private String content;
    @ApiModelProperty(value = "区域ID",reference = "readAll")
    private String businessId;





}
