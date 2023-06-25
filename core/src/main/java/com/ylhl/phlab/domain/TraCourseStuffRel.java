package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_course_stuff_rel")
public class TraCourseStuffRel {

    @TableId
    @Comment("")
    private String id;
    @TableField(alias = "course_id")
    @Comment("课程id")
    private String courseId;
    @TableField(alias = "stuff_id")
    @Comment("学习资料id")
    private String stuffId;
    @TableField(alias = "stuff_type")
    @Comment("学习资料类型 1文章 2视频 3文档")
    private String stuffType;
    @Comment("排序")
    private String sort;

}