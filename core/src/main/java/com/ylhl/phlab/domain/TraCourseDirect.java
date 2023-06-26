package com.ylhl.phlab.domain;

import lombok.Data;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_course_direct")
public class TraCourseDirect {

    @TableId
    @Comment("")
    private String id;
    @TableField(alias = "course_id")
    @Comment("课程id")
    private String courseId;
    @Comment("目录")
    private String direct;
    @TableField(alias = "stuff_cover_path")
    @Comment("学习资料封面图片")
    private String stuffCoverPath;
    @TableField(alias = "stuff_id")
    @Comment("学习资料id")
    private String stuffId;
    @TableField(alias = "stuff_type")
    @Comment("学习资料类型 1文章 2视频 3文档")
    private String stuffType;
    @Comment("排序")
    private String sort;

}