package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_stu_stuff_info")
public class TraStuStuffInfo {

    @TableId
    @TableField(alias = "stuff_id")
    @Comment("")
    private String stuffId;
    @TableField(alias = "stuff_title")
    @Comment("标题")
    private String stuffTitle;
    @TableField(alias = "stuff_type")
    @Comment("资料类型 1文章 2视频 3文档")
    private String stuffType;
    @TableField(alias = "file_path")
    @Comment("文件路径")
    private String filePath;
    @Comment("文章内容")
    private String content;
    @Comment("描述")
    private String describtion;
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