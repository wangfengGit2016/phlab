package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_staff_info")
public class SysStaffInfo {

    @TableId
    @TableField(alias = "staff_id")
    @Comment("员工id")
    private String staffId;
    @TableField(alias = "user_id")
    @Comment("用户id")
    private String userId;
    @Comment("员工名")
    private String name;
    @TableField(alias = "staff_phone")
    @Comment("联系方式")
    private String staffPhone;
    @TableField(alias = "id_card")
    @Comment("证件号码")
    private String idCard;
    @TableField(alias = "office_status")
    @Comment("任职状态 0：在职 1：离职")
    private String officeStatus;
    @TableField(alias = "organ_id")
    @Comment("s所属组织")
    private String organId;
    @Comment("职位")
    private String position;
    @TableField(alias = "entry_time")
    @Comment("入职时间")
    private String entryTime;
    @TableField(alias = "depart_time")
    @Comment("离职时间")
    private String departTime;
    @Comment("状态 0：有效 1：锁定")
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