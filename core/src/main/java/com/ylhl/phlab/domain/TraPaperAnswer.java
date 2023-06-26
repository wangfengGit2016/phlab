package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_paper_answer")
public class TraPaperAnswer {

    @TableId
    @TableField(alias = "paper_answer_id")
    @Comment("")
    private String paperAnswerId;
    @TableField(alias = "user_id")
    @Comment("用户id")
    private String userId;
    @TableField(alias = "public_paper_id")
    @Comment("试卷id")
    private String publicPaperId;
    @TableField(alias = "public_paper_name")
    @Comment("试卷名称")
    private String publicPaperName;
    @TableField(alias = "public_paper_type")
    @Comment("试卷类型")
    private String publicPaperType;
    @Comment("得分")
    private Integer score;
    @TableField(alias = "total_score")
    @Comment("总分")
    private Integer totalScore;
    @TableField(alias = "pass_score")
    @Comment("及格分数")
    private Integer passScore;
    @TableField(alias = "question_true")
    @Comment("作对题目数量")
    private Integer questionTrue;
    @TableField(alias = "question_total")
    @Comment("题目总数")
    private Integer questionTotal;
    @TableField(alias = "start_time")
    @Comment("开始答卷时间")
    private Date startTime;
    @TableField(alias = "submit_time")
    @Comment("提交时间")
    private Date submitTime;
    @TableField(alias = "time_consuming")
    @Comment("答卷耗时")
    private Integer timeConsuming;
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