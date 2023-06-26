package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_certificate_record")
public class TraCertificateRecord {

    @TableId
    @TableField(alias = "certificate_record_id")
    @Comment("")
    private String certificateRecordId;
    @TableField(alias = "certificate_id")
    @Comment("证书id")
    private String certificateId;
    @TableField(alias = "certificate_type")
    @Comment("证书类型 1 本系统证书  2 第三方证书")
    private String certificateType;
    @TableField(alias = "file_path")
    @Comment("证书附件")
    private String filePath;
    @Comment("描述")
    private String description;
    @TableField(alias = "user_id")
    @Comment("用户id")
    private String userId;
    @Comment("持有人姓名")
    private String holder;
    @TableField(alias = "generate_time")
    @Comment("获得时间")
    private Date generateTime;
    @TableField(alias = "create_id")
    @Comment("创建人id")
    private String createId;
    @TableField(alias = "create_name")
    @Comment("创建人姓名")
    private String createName;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_id")
    @Comment("更新人id")
    private String updateId;
    @TableField(alias = "update_name")
    @Comment("更新人姓名")
    private String updateName;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;
    @TableField(alias = "delete_id")
    @Comment("删除人id")
    private String deleteId;
    @TableField(alias = "delete_name")
    @Comment("删除人姓名")
    private String deleteName;
    @TableField(alias = "delete_time")
    @Comment("删除时间")
    private Date deleteTime;
    @Comment("删除状态 0：未删除 1：已删除")
    private String deleted;
}