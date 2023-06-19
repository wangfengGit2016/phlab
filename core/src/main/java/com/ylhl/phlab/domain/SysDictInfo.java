package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_dict_info")
public class SysDictInfo {

    @TableId
    @Comment("字典ID")
    private String id;
    @TableField(alias = "parent_id")
    @Comment("父节点ID")
    private String parentId;
    @TableField(alias = "dict_code")
    @Comment("编码")
    private String dictCode;
    @Comment("值")
    private String value;
    @Comment("标签")
    private String label;
    @Comment("类型")
    private String type;
    @Comment("描述")
    private String description;
    @Comment("备注")
    private String remark;
    @Comment("是否根节点")
    private String root;
    @Comment("顺序")
    private Integer sort;
    @Comment("状态 1 启用 0停用")
    private String status;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

}