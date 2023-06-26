package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_course_info")
public class TraCourseInfo {

    @TableId
    @TableField(alias = "course_id")
    @Comment("")
    private String courseId;
    @TableField(alias = "course_name")
    @Comment("课程名称")
    private String courseName;
    @TableField(alias = "course_cover_path")
    @Comment("封面图片路径")
    private String courseCoverPath;
    @TableField(alias = "exam_name")
    @Comment("考试名称")
    private String examName;
    @TableField(alias = "public_paper_id")
    @Comment("正式试卷id")
    private String publicPaperId;
    @Comment("描述")
    private String description;
    @Comment("发布状态 0未发布 1已发布")
    private String status;
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