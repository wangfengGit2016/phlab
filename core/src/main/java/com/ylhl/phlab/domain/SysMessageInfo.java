package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_message_info")
public class SysMessageInfo {

    @TableId
    @Comment("")
    private String id;
    @Comment("消息类型 1预警消息 2日照中心建设申请 3节假日慰问金")
    private String type;
    @Comment("消息标题")
    private String title;
    @Comment("消息内容")
    private String content;
    @TableField(alias = "business_id")
    @Comment("业务id")
    private String businessId;
    @TableField(alias = "region_id")
    @Comment("区域ID")
    private String regionId;
    @Comment("消息状态 0未读 1已读")
    private String status;
    @TableField(alias = "create_time")
    @Comment("")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("")
    private Date updateTime;
    @TableField(alias = "is_read",ignore = true)
    @Comment("")
    private String isRead;


}