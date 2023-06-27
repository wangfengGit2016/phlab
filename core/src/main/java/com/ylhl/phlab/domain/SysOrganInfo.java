package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_organ_info")
public class SysOrganInfo {

    @TableId
    @Comment("节点id")
    private String id;
    @TableField(alias = "parent_id")
    @Comment("上级节点")
    private String parentId;
    @TableField(alias = "parent_name")
    @Comment("上级节点名称")
    private String parentName;
    @TableField(alias = "node_name")
    @Comment("节点（区域/机构/科室）名")
    private String nodeName;
    @TableField(alias = "node_code")
    @Comment("节点编码")
    private String nodeCode;
    @TableField(alias = "full_name")
    @Comment("全称")
    private String fullName;
    @Comment("类型 1地区 2机构  3科室")
    private String type;
    @Comment("组织机构分级")
    private String level;
    @Comment("备注")
    private String remark;
    @Comment("状态 1 启用 0停用")
    private String status;
    @Comment("排序")
    private int sort;
    @Comment("删除状态 0：未删除 1：已删除")
    private String deleted;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

}