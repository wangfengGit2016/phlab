package com.ylhl.phlab.domain;

import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
@Table("lab_plan_site_dept_rel")
public class LabPlanSiteDeptRel {

    @TableField(alias = "plan_id")
    @Comment("计划id")
    private String planId;
    @TableField(alias = "site_dept_name")
    @Comment("地区科室")
    private String siteDeptName;
    @TableField(alias = "create_id")
    @Comment("创建人id")
    private String createId;
    @TableField(alias = "create_name")
    @Comment("创建人名称")
    private String createName;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;

}