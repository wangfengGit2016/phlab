package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_approval_process_detail")
public class LabApprovalProcessDetail {

    @TableId
    @TableField(alias = "appr_pro_detail_id")
    @Comment("审批流程模版明细id")
    private String apprProDetailId;
    @TableField(alias = "appr_pro_id")
    @Comment("审核流程id")
    private String apprProId;
    @TableField(alias = "node_rel")
    @Comment("节点关系 0：且 1：或")
    private String nodeRel;
    @TableField(alias = "node_name")
    @Comment("节点名称")
    private String nodeName;
    @TableField(alias = "dept_id")
    @Comment("科室id")
    private String deptId;
    @TableField(alias = "dept_name")
    @Comment("科室名称")
    private String deptName;
    @TableField(alias = "staff_id")
    @Comment("员工id")
    private String staffId;
    @TableField(alias = "staff_name")
    @Comment("员工名称")
    private String staffName;
    @Comment("排序")
    private Integer sort;
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