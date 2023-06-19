package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_sms_info")
public class SysSmsInfo {

    @TableId
    @Comment("")
    private String id;
    @Comment("联系方式")
    private String phone;
    @Comment("通知人员")
    private String name;
    @Comment("通知内容")
    private String content;
    @Comment("通知类型 1短信 2电话")
    private String type;
    @TableField(alias = "business_type")
    @Comment("业务类型 1注册 2预警 ")
    private String businessType;
    @TableField(alias = "business_id")
    @Comment("业务id")
    private String businessId;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("修改时间")
    private Date updateTime;

}