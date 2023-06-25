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