package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_plan_message_info")
public class LabPlanMessageInfo {

    @TableId
    @TableField(alias = "plan_message_id")
    @Comment("消息id")
    private String planMessageId;
    @TableField(alias = "plan_id")
    @Comment("计划id")
    private String planId;
    @TableField(alias = "doc_number")
    @Comment("文号")
    private String docNumber;
    @Comment("标题")
    private String title;
    @Comment("年度")
    private String year;
    @TableField(alias = "type_id")
    @Comment("盲样种类id")
    private String typeId;
    @TableField(alias = "type_name")
    @Comment("盲样种类")
    private String typeName;
    @TableField(alias = "dept_id")
    @Comment("发送科室id")
    private String deptId;
    @TableField(alias = "dept_name")
    @Comment("发送科室名称")
    private String deptName;
    @TableField(alias = "plan_user_name")
    @Comment("发送人")
    private String planUserName;
    @TableField(alias = "plan_phone")
    @Comment("发送人手机号")
    private String planPhone;
    @TableField(alias = "sample_date")
    @Comment("领取样本截止时间")
    private String sampleDate;
    @TableField(alias = "data_date")
    @Comment("检测数据上传截止时间")
    private String dataDate;
    @Comment("具体内容")
    private String content;
    @TableField(alias = "release_time")
    @Comment("发布时间")
    private String releaseTime;
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