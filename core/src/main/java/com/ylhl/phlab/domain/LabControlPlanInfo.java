package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_control_plan_info")
public class LabControlPlanInfo {

    @TableId
    @TableField(alias = "control_plan_id")
    @Comment("质控计划id")
    private String controlPlanId;
    @TableField(alias = "control_name")
    @Comment("质控计划名称")
    private String controlName;
    @Comment("年度")
    private String year;
    @TableField(alias = "control_user_name")
    @Comment("质控项目负责人姓名")
    private String controlUserName;
    @TableField(alias = "control_phone")
    @Comment("质控项目负责人联系方式")
    private String controlPhone;
    @TableField(alias = "testing_basis")
    @Comment("检测依据")
    private String testingBasis;
    @TableField(alias = "comp_method")
    @Comment("比对方式")
    private String compMethod;
    @Comment("参加人员")
    private String participants;
    @TableField(alias = "org_unit")
    @Comment("组织单位")
    private String orgUnit;
    @TableField(alias = "comp_time")
    @Comment("比对时间")
    private String compTime;
    @TableField(alias = "judg_basis")
    @Comment("评判依据")
    private String judgBasis;
    @Comment("当前状态 0：待提交 1：待审批 2：已完成")
    private String status;
    @TableField(alias = "create_id")
    @Comment("创建人id")
    private String createId;
    @TableField(alias = "create_name")
    @Comment("创建人姓名")
    private String createName;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_id")
    @Comment("更新人id")
    private String updateId;
    @TableField(alias = "update_name")
    @Comment("更新人姓名")
    private String updateName;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;
    @TableField(alias = "delete_id")
    @Comment("删除人id")
    private String deleteId;
    @TableField(alias = "delete_name")
    @Comment("删除人姓名")
    private String deleteName;
    @TableField(alias = "delete_time")
    @Comment("删除时间")
    private Date deleteTime;
    @Comment("删除状态 0：未删除 1：已删除")
    private String deleted;

}