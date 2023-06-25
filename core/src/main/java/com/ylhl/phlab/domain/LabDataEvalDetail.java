package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_data_eval_detail")
public class LabDataEvalDetail {

    @TableId
    @TableField(alias = "data_eval_id")
    @Comment("评价记录id")
    private String dataEvalId;
    @TableField(alias = "data_id")
    @Comment("数据id")
    private String dataId;
    @TableField(alias = "plan_id")
    @Comment("下发计划id")
    private String planId;
    @TableField(alias = "eval_result")
    @Comment("评价结果 1：通过 2：不通过")
    private String evalResult;
    @TableField(alias = "eval_content")
    @Comment("评价内容")
    private String evalContent;
    @TableField(alias = "eval_id")
    @Comment("评价人id")
    private String evalId;
    @TableField(alias = "eval_name")
    @Comment("评价人姓名")
    private String evalName;
    @TableField(alias = "eval_time")
    @Comment("评价时间")
    private Date evalTime;
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