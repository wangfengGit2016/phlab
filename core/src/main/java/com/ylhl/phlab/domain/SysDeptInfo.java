package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_dept_info")
public class SysDeptInfo {

    @TableId
    @Comment("id")
    private String id;
    @TableField(alias = "parent_id")
    @Comment("机构id")
    private String parentId;
    @TableField(alias = "parent_name")
    @Comment("机构名称")
    private String parentName;
    @TableField(alias = "dept_name")
    @Comment("科室名称")
    private String deptName;
    @TableField(alias = "dept_code")
    @Comment("科室编码")
    private String deptCode;
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
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

}