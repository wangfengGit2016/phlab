package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_plan_site_department_rel")
public class LabPlanSiteDepartmentRel {

    @TableId
    @TableField(alias = "plan_site_dept_id")
    @Comment("计划场地科室关联id")
    private String planSiteDeptId;
    @TableField(alias = "plan_site_id")
    @Comment("计划场地关联id")
    private String planSiteId;
    @TableField(alias = "plan_id")
    @Comment("计划id")
    private String planId;
    @TableField(alias = "site_id")
    @Comment("场地id")
    private String siteId;
    @TableField(alias = "dept_id")
    @Comment("科室id")
    private String deptId;
    @TableField(alias = "dept_name")
    @Comment("科室名称")
    private String deptName;
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