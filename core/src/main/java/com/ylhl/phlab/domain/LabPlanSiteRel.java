package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_plan_site_rel")
public class LabPlanSiteRel {

    @TableId
    @TableField(alias = "plan_site_id")
    @Comment("计划地区关联id")
    private String planSiteId;
    @TableField(alias = "plan_id")
    @Comment("计划id")
    private String planId;
    @TableField(alias = "site_id")
    @Comment("地区id")
    private String siteId;
    @TableField(alias = "site_name")
    @Comment("地区名称")
    private String siteName;
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