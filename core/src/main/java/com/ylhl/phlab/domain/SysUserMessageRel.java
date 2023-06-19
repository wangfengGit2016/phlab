package com.ylhl.phlab.domain;

import lombok.Data;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("sys_user_message_rel")
public class SysUserMessageRel {

    @TableId
    @TableField(alias = "user_id")
    @Comment("")
    private String userId;
    @TableId
    @TableField(alias = "message_id")
    @Comment("")
    private String messageId;
    @TableField(alias = "is_read")
    @Comment("是否已读")
    private String isRead;
    @TableField(alias = "code")
    @Comment("编码")
    private String code;


}