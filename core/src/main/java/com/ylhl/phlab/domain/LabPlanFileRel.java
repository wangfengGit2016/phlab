package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_plan_file_rel")
public class LabPlanFileRel {

    @TableField(alias = "plan_id")
    @Comment("下发计划id")
    private String planId;
    @TableField(alias = "file_id")
    @Comment("文件id")
    private String fileId;
    @TableField(alias = "file_path")
    @Comment("存储路径")
    private String filePath;
    @Comment("存储:(1-华为云)")
    private Integer location;
    @Comment("obs桶名称")
    private String bucket;
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
    @TableField(alias = "create_id")
    @Comment("创建人id")
    private String createId;
    @TableField(alias = "create_name")
    @Comment("创建人姓名")
    private String createName;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;

}