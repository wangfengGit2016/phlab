package com.ylhl.phlab.domain;

import com.ylhl.phlab.annotation.*;
import lombok.Data;
import java.util.Date;

@Data
@Table("sys_user_info")
public class SysUserInfo {

    @TableId
    @Comment("")
    private String id;
    @TableField(alias = "user_name")
    @Comment("用户名")
    private String userName;
    @Comment("登录密码")
    private String password;
    @TableField(alias = "real_name")
    @Comment("真实姓名")
    private String realName;
    @Comment("电话")
    private String phone;
    @Comment("身份证号")
    private String idcard;
    @Comment("工作单位")
    private String unit;
    @TableField(alias = "region_id")
    @Comment("所属区域")
    private String regionId;
    @TableField(alias = "user_status")
    @Comment("用户状态")
    private String userStatus;
    @TableField(alias = "admin_type")
    @Comment("是否超管")
    private String adminType;
    @TableField(alias = "organization_id")
    @Comment("机构id")
    private String organizationId;
    @Comment("删除状态")
    private String deleted;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

    @TableField(alias = "full_name",ignore = true)
    private String fullName;
    @TableField(alias = "role_Id",ignore = true)
    private String roleId;
    @TableField(alias = "role_name",ignore = true)
    private String roleName;
}