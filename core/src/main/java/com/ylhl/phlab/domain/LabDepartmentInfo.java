package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_department_info")
public class LabDepartmentInfo {

    @TableId
    @TableField(alias = "dept_id")
    @Comment("科室id")
    private String deptId;
    @TableField(alias = "site_id")
    @Comment("科室所在地区id")
    private String siteId;
    @TableField(alias = "site_name")
    @Comment("科室所在地区名称")
    private String siteName;
    @TableField(alias = "dept_name")
    @Comment("科室名称")
    private String deptName;
    @TableField(alias = "dept_code")
    @Comment("科室编码")
    private String deptCode;
    @Comment("科室介绍")
    private String introduction;
    @TableField(alias = "dept_type")
    @Comment("科室类型 0：省级 1：市区级")
    private String deptType;
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