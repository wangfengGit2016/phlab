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
    @Comment("机构id")
    private String id;
    @TableField(alias = "parent_id")
    @Comment("上级机构")
    private String parentId;
    @TableField(alias = "parent_name")
    @Comment("上级机构名称")
    private String parentName;
    @TableField(alias = "organ_name")
    @Comment("机构名")
    private String organName;
    @TableField(alias = "organ_code")
    @Comment("机构编码")
    private String organCode;
    @TableField(alias = "full_name")
    @Comment("全称")
    private String fullName;
    @TableField(alias = "region_id")
    @Comment("区划id")
    private String regionId;
    @Comment("备注")
    private String remark;
    @Comment("状态 1 启用 0停用")
    private String status;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private String createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private String updateTime;

}