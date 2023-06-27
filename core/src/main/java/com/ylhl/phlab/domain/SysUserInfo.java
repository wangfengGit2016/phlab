package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

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
    @TableField(alias = "user_status")
    @Comment("用户状态 0冻结 1解冻")
    private String userStatus;
    @TableField(alias = "admin_type")
    @Comment("是否超管")
    private String adminType;
    @TableField(alias = "office_status")
    @Comment("任职状态 0：在职 1：离职")
    private String officeStatus;
    @TableField(alias = "organ_id")
    @Comment("所属组织")
    private String organId;
    @TableField(alias = "region_id")
    @Comment("所属区域")
    private String regionId;
    @Comment("职位")
    private String position;
    @TableField(alias = "entry_time")
    @Comment("入职时间")
    private String entryTime;
    @TableField(alias = "depart_time")
    @Comment("离职时间")
    private String departTime;
    @Comment("删除状态")
    private String deleted;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

}