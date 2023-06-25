package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_cortrol_plan_summary")
public class LabCortrolPlanSummary {

    @TableId
    @TableField(alias = "cortrol_plan_summary_id")
    @Comment("质控汇总id")
    private String cortrolPlanSummaryId;
    @Comment("年度")
    private String year;
    @TableField(alias = "place_name")
    @Comment("所名")
    private String placeName;
    @TableField(alias = "plan_summary_name")
    @Comment("计划名称")
    private String planSummaryName;
    @TableField(alias = "submit_time")
    @Comment("提交时间")
    private String submitTime;
    @TableField(alias = "file_id")
    @Comment("文件id")
    private String fileId;
    @Comment("流转状态 0：茅所 1：质检科")
    private String status;
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
    @Comment("删除人姓名")
    private String deleteName;
    @TableField(alias = "delete_time")
    @Comment("删除时间")
    private Date deleteTime;
    @Comment("删除状态 0：未删除 1：已删除")
    private String deleted;

}