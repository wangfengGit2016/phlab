package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_role_info")
public class SysRoleInfo {

    @TableId
    @Comment("")
    private String id;
    @TableField(alias = "parent_id")
    @Comment("父节点ID")
    private String parentId;
    @TableField(alias = "role_name")
    @Comment("角色名")
    private String roleName;
    @TableField(alias = "role_code")
    @Comment("角色编码")
    private String roleCode;
    @TableField(alias = "role_desc")
    @Comment("角色描述")
    private String roleDesc;
    @Comment("删除状态")
    private String deleted;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

}