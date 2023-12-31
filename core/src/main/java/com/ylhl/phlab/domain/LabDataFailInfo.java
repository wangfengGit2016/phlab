package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_data_fail_info")
public class LabDataFailInfo {

    @TableId
    @TableField(alias = "history_data_id")
    @Comment("历史数据id")
    private String historyDataId;
    @TableField(alias = "data_id")
    @Comment("数据id")
    private String dataId;
    @TableField(alias = "plan_id")
    @Comment("下发计划id")
    private String planId;
    @TableField(alias = "doc_number")
    @Comment("文号")
    private String docNumber;
    @TableField(alias = "type_id")
    @Comment("盲样种类")
    private String typeId;
    @TableField(alias = "type_name")
    @Comment("盲样种类")
    private String typeName;
    @TableField(alias = "site_id")
    @Comment("所在地区id")
    private String siteId;
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
    @Comment("员工姓名")
    private String staffName;
    @TableField(alias = "site_name")
    @Comment("所在地区")
    private String siteName;
    @TableField(alias = "upload_user_name")
    @Comment("上传人员姓名")
    private String uploadUserName;
    @TableField(alias = "upload_phone")
    @Comment("上传人员手机号")
    private String uploadPhone;
    @TableField(alias = "upload_time")
    @Comment("上传时间")
    private String uploadTime;
    @TableField(alias = "sample_status")
    @Comment("样本状态 0：未领取 1：已领取")
    private String sampleStatus;
    @TableField(alias = "upload_data_status")
    @Comment("数据上传状态 0：否 1：是")
    private String uploadDataStatus;
    @Comment("当前状态 0：待提交 1：待评价 2：已评价")
    private String status;
    @TableField(alias = "eval_result")
    @Comment("评价结果 0：未评价 1：通过 2：不通过")
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
    @TableField(alias = "lab_name")
    @Comment("实验室名称")
    private String labName;
    @TableField(alias = "lab_address")
    @Comment("实验室地址")
    private String labAddress;
    @TableField(alias = "lab_user_name")
    @Comment("实验室联系人姓名")
    private String labUserName;
    @TableField(alias = "lab_phone")
    @Comment("实验室联系人手机号")
    private String labPhone;
    @Comment("标题")
    private String title;
    @TableField(alias = "plan_user_name")
    @Comment("计划联系人")
    private String planUserName;
    @TableField(alias = "plan_phone")
    @Comment("计划联系人手机号")
    private String planPhone;
    @Comment("具体内容")
    private String context;
    @TableField(alias = "file_id")
    @Comment("检测报告表文件id")
    private String fileId;
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
    @Comment("删除状态 0:未删除 1：已删除")
    private String deleted;

}