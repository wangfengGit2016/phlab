package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_approval_process_node_info")
public class LabApprovalProcessNodeInfo {

    @TableId
    @TableField(alias = "node_id")
    @Comment("节点id")
    private String nodeId;
    @TableField(alias = "control_plan_id")
    @Comment("质检计划id")
    private String controlPlanId;
    @TableField(alias = "approval_process_id")
    @Comment("审批流程id")
    private String approvalProcessId;
    @TableField(alias = "node_rel")
    @Comment("节点关系 0：且 1：或")
    private String nodeRel;
    @TableField(alias = "node_name")
    @Comment("节点名称")
    private String nodeName;
    @TableField(alias = "node_dept_id")
    @Comment("节点科室id")
    private String nodeDeptId;
    @TableField(alias = "node_dept_name")
    @Comment("节点科室名称")
    private String nodeDeptName;
    @TableField(alias = "node_staff_position")
    @Comment("节点审批人职位")
    private String nodeStaffPosition;
    @TableField(alias = "node_staff_id")
    @Comment("节点审批人员id")
    private String nodeStaffId;
    @TableField(alias = "node_staff_name")
    @Comment("节点审批人名称")
    private String nodeStaffName;
    @TableField(alias = "node_sort")
    @Comment("节点排序")
    private Integer nodeSort;
    @TableField(alias = "appr_staff_id")
    @Comment("审批人员id")
    private String apprStaffId;
    @TableField(alias = "appr_staff_name")
    @Comment("审批人员名称")
    private String apprStaffName;
    @TableField(alias = "appr_status")
    @Comment("审批状态 0：同意 1：不同意")
    private String apprStatus;
    @TableField(alias = "appr_opinion")
    @Comment("审批意见")
    private String apprOpinion;
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