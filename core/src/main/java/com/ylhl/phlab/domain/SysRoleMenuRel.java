package com.ylhl.phlab.domain;

import lombok.Data;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_role_menu_rel")
public class SysRoleMenuRel {

    @TableId
    @TableField(alias = "role_id")
    @Comment("")
    private String roleId;
    @TableId
    @TableField(alias = "menu_id")
    @Comment("")
    private String menuId;

}