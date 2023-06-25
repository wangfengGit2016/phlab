package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_approval_process_info")
public class LabApprovalProcessInfo {

    @TableId
    @TableField(alias = "appr_pro_id")
    @Comment("审批流程id")
    private String apprProId;
    @TableField(alias = "appr_pro_type")
    @Comment("审批流程 1：计划审批")
    private String apprProType;
    @TableField(alias = "appr_pro_name")
    @Comment("审批流程名称")
    private String apprProName;
    @TableField(alias = "dept_id")
    @Comment("申请科室id")
    private String deptId;
    @TableField(alias = "dept_name")
    @Comment("申请科室名称")
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
    @TableField(alias = "update_id")
    @Comment("更新人id")
    private String updateId;
    @TableField(alias = "update_name")
    @Comment("更新人名称")
    private String updateName;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;
    @TableField(alias = "delete_id")
    @Comment("删除人id")
    private String deleteId;
    @TableField(alias = "delete_name")
    @Comment("删除人名称")
    private String deleteName;
    @TableField(alias = "delete_time")
    @Comment("删除时间")
    private Date deleteTime;
    @Comment("删除状态 0：未删除 1：已删除")
    private String deleted;

}