package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("lab_data_file_rel")
public class LabDataFileRel {

    @TableField(alias = "business_id")
    @Comment("业务id")
    private String businessId;
    @Comment("文件 1：数据文件 2：数据附件 3：评价附件")
    private String type;
    @TableField(alias = "file_id")
    @Comment("文件id")
    private String fileId;
    @TableField(alias = "file_path")
    @Comment("存储路径")
    private String filePath;
    @TableField(alias = "create_id")
    @Comment("创建人id")
    private String createId;
    @TableField(alias = "create_name")
    @Comment("创建人名称")
    private String createName;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;

}