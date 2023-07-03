package com.ylhl.phlab.param;

import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.domain.TraCourseInfo;
import com.ylhl.phlab.mapper.Page;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@ApiModel("试卷信息")
@Data
@EqualsAndHashCode(callSuper = true)
public class TraCourseInfoParam extends Page<TraCourseInfo> {
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
}
