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
    private String describtion;
    @TableField(alias = "user_id")
    @Comment("用户id")
    private String userId;
    @TableField(alias = "user_real_name")
    @Comment("用户姓名")
    private String userRealName;
    @TableField(alias = "generate_time")
    @Comment("获得时间")
    private Date generateTime;

}