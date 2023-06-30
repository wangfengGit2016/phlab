package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_plan_info")
public class LabPlanInfo {

    @TableId
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
    @TableField(alias = "site_total")
    @Comment("地区数量")
    private Integer siteTotal;
    @TableField(alias = "type_id")
    @Comment("盲样种类id")
    private String typeId;
    @TableField(alias = "type_name")
    @Comment("盲样种类")
    private String typeName;
    @TableField(alias = "dept_id")
    @Comment("科室id")
    private String deptId;
    @TableField(alias = "dept_name")
    @Comment("科室名称")
    private String deptName;
    @TableField(alias = "plan_user_name")
    @Comment("计划联系人")
    private String planUserName;
    @TableField(alias = "plan_phone")
    @Comment("计划联系人手机号")
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
    @TableField(alias = "file_message")
    @Comment("文件反显字段")
    private String fileMessage;
    @TableField(alias = "dept_message")
    @Comment("科室反显字段")
    private String deptMessage;
    @TableField(alias = "need_eval")
    @Comment("评价跳转状态 0：需要评价 1：不需要评价")
    private String needEval;
    @Comment("发布状态 0：待发布 1：已发布")
    private String status;
    @TableField(alias = "excel_head")
    @Comment("表头信息")
    private String excelHead;
    @TableField(alias = "excel_body")
    @Comment("表体信息")
    private String excelBody;
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