package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_region_info")
public class SysRegionInfo {

    @TableId
    @Comment("字典ID")
    private String id;
    @TableField(alias = "parent_id")
    @Comment("父节点ID")
    private String parentId;
    @TableField(alias = "parent_name")
    @Comment("父名称")
    private String parentName;
    @TableField(alias = "region_name")
    @Comment("区划名")
    private String regionName;
    @TableField(alias = "region_code")
    @Comment("区划编码")
    private String regionCode;
    @TableField(alias = "full_name")
    @Comment("全称")
    private String fullName;
    @Comment("备注")
    private String remark;
    @Comment("状态 1 启用 0停用")
    private String status;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

}