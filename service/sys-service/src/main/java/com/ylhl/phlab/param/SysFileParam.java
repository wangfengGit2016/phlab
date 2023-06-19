package com.ylhl.phlab.param;

import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("文件管理")
@Data
public class SysFileParam {
    @TableId
    @ApiModelProperty("主键ID")
    private String id;
    @ApiModelProperty("存储:(1-华为云)")
    private Integer location;
    @ApiModelProperty("obs桶名称")
    private String bucket;
    @TableField(alias = "file_path")
    @ApiModelProperty("存储路径")
    private String filePath;
    @TableField(alias = "origin_name")
    @ApiModelProperty("文件原始名称（用户上传时名称）")
    private String originName;
    @TableField(alias = "file_suffix")
    @ApiModelProperty("文件后缀名")
    private String fileSuffix;
    @TableField(alias = "content_type")
    @ApiModelProperty("文件content-type类型")
    private String contentType;
    @TableField(alias = "file_size")
    @ApiModelProperty("文件大小，以kb为单位")
    private String fileSize;
    @TableField(alias = "user_id")
    @ApiModelProperty("创建用户")
    private String userId;
    @TableField(alias = "organization_id")
    @ApiModelProperty("所属机构")
    private String organizationId;
}
