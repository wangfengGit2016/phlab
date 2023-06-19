package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_log_info")
public class SysLogInfo {

    @TableId
    @Comment("")
    private String id;
    @Comment("操作模块")
    private String module;
    @Comment("操作详情")
    private String content;
    @TableField(alias = "user_id")
    @Comment("操作人账户")
    private String userId;
    @TableField(alias = "user_name")
    @Comment("操作人姓名")
    private String userName;
    @Comment("状态 0成功 1失败")
    private String status;
    @Comment("操作人ip")
    private String ip;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;

}