package com.ylhl.phlab.param;

import com.ylhl.phlab.annotation.Comment;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("机构")
@Data
public class SysOrganParam {
    @ApiModelProperty(value = "页长",reference = "page")
    protected long size = 10;
    @ApiModelProperty(value = "当前页",reference = "page")
    protected long current = 1;

    @ApiModelProperty(value = "区域ID",reference = "tree,update,delete,detail")
    private String id;
    @ApiModelProperty(value = "父节点ID",reference = "create")
    private String parentId;
    @ApiModelProperty(value = "父名称",reference = "create")
    private String parentName;
    @ApiModelProperty(value = "机构名称",reference = "page,create")
    private String nodeName;
    @ApiModelProperty(value = "机构编码",reference = "create")
    private String nodeCode;
    @ApiModelProperty(value = "全称",reference = "create")
    private String fullName;
    @ApiModelProperty(value = "备注",reference = "create")
    private String remark;
    @ApiModelProperty(value = "状态 1 启用 0停用",reference = "create")
    private String status;
    @ApiModelProperty("类型 1地区 2机构  3科室")
    private String type;
}
