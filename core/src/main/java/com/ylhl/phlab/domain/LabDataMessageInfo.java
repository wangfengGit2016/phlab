package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_data_message_info")
public class LabDataMessageInfo {

    @TableId
    @TableField(alias = "data_message_id")
    @Comment("")
    private String dataMessageId;
    @TableField(alias = "dept_id")
    @Comment("科室id")
    private String deptId;
    @TableField(alias = "dept_name")
    @Comment("科室名称")
    private String deptName;
    @TableField(alias = "staff_id")
    @Comment("员工id")
    private String staffId;
    @TableField(alias = "staff_name")
    @Comment("员工名称")
    private String staffName;
    @Comment("联系方式")
    private String phone;
    @Comment("通知内容")
    private String content;
    @TableField(alias = "data_id")
    @Comment("数据id")
    private String dataId;
    @Comment("通知类型 1短信 2电话")
    private String type;
    @TableField(alias = "business_type")
    @Comment("业务类型 1：催领样 2：催上传")
    private String businessType;
    @Comment("通知状态")
    private String status;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("修改时间")
    private Date updateTime;

}