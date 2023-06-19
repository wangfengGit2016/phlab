package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_file_info")
public class SysFileInfo {

    @TableId
    @Comment("主键ID")
    private String id;
    @Comment("存储:(1-华为云)")
    private Integer location;
    @Comment("obs桶名称")
    private String bucket;
    @TableField(alias = "file_path")
    @Comment("存储路径")
    private String filePath;
    @TableField(alias = "origin_name")
    @Comment("文件原始名称（用户上传时名称）")
    private String originName;
    @TableField(alias = "file_suffix")
    @Comment("文件后缀名")
    private String fileSuffix;
    @TableField(alias = "content_type")
    @Comment("文件content-type类型")
    private String contentType;
    @TableField(alias = "file_size")
    @Comment("文件大小，以kb为单位")
    private String fileSize;
    @TableField(alias = "user_id")
    @Comment("创建用户")
    private String userId;
    @TableField(alias = "organization_id")
    @Comment("所属机构")
    private String organizationId;
    @Comment("'逻辑删除 1-正常 0-删除'")
    private String deleted;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("删除时间")
    private Date updateTime;

}