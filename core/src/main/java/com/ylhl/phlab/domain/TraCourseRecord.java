package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_course_record")
public class TraCourseRecord {

    @TableId
    @TableField(alias = "course_record_id")
    @Comment("")
    private String courseRecordId;
    @TableField(alias = "course_id")
    @Comment("课程id")
    private String courseId;
    @TableField(alias = "course_name")
    @Comment("课程名称")
    private String courseName;
    @TableField(alias = "finish_stuff")
    @Comment("课程内资料已学习数量")
    private String finishStuff;
    @TableField(alias = "user_id")
    @Comment("用户id")
    private String userId;
    @TableField(alias = "user_real_name")
    @Comment("用户姓名")
    private String userRealName;
    @TableField(alias = "start_time")
    @Comment("开始学习时间")
    private Date startTime;
    @TableField(alias = "paper_id")
    @Comment("试卷id")
    private String paperId;
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