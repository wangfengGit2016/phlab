package com.ylhl.phlab.domain;

import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import lombok.Data;

import java.util.Date;

@Data
@Table("lab_plan_type_rel")
public class LabPlanTypeRel {
    @TableField(alias = "type_id")
    @Comment("盲样种类id")
    private String typeId;
    @Comment("盲样结果")
    private String result;
    @Comment("类型 0： 判断结果 1：检测项目（荧光结果）")
    private String form;
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
