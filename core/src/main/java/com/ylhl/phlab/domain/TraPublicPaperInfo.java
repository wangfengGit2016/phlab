package com.ylhl.phlab.domain;

import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
@Table("tra_public_paper_info")
public class TraPublicPaperInfo {

    @TableId
    @TableField(alias = "paper_id")
    @Comment("")
    private String paperId;
    @TableField(alias = "paper_name")
    @Comment("试卷名称")
    private String paperName;
    @TableField(alias = "question_count")
    @Comment("题目数量")
    private Integer questionCount;
    @TableField(alias = "unique_id")
    @Comment("展示唯一id")
    private Integer uniqueId;
    @TableField(alias = "start_exam_time")
    @Comment("开始考试时间")
    private Date startExamTime;
    @TableField(alias = "end_exam_time")
    @Comment("考试截至时间")
    private Date endExamTime;
    @TableField(alias = "pass_score")
    @Comment("及格分数")
    private String passScore;
    @Comment("试卷总分")
    private String score;
    @TableField(alias = "exam_duration")
    @Comment("考试时长(分钟)")
    private Integer examDuration;
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