package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_menu_info")
public class SysMenuInfo {

    @TableId
    @Comment("菜单ID")
    private String id;
    @Comment("菜单名称")
    private String title;
    @Comment("接口地址")
    private String api;
    @Comment("权限标识")
    private String permission;
    @Comment("菜单路由")
    private String path;
    @TableField(alias = "parent_id")
    @Comment("父菜单ID")
    private String parentId;
    @TableField(alias = "parent_name")
    @Comment("父菜单名")
    private String parentName;
//    @Comment("图标")
//    private String icon;
    @TableField(alias = "show_flag")
    @Comment("是否显示：0是 1否")
    private String showFlag;
    @Comment("排序值")
    private Integer sort;
    @TableField(alias = "keep_alive")
    @Comment("是否缓冲")
    private String keepAlive;
    @Comment("菜单类型 0：目录 1：菜单 2: 按钮")
    private String type;
    @Comment("删除标记")
    private String deleted;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

}