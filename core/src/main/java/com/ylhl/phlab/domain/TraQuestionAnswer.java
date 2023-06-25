package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_question_answer")
public class TraQuestionAnswer {

    @TableId
    @TableField(alias = "question_answer_id")
    @Comment("")
    private String questionAnswerId;
    @TableField(alias = "question_bak_id")
    @Comment("题目备份id")
    private String questionBakId;
    @TableField(alias = "paper_answer_id")
    @Comment("答卷id")
    private String paperAnswerId;
    @TableField(alias = "question_type")
    @Comment("题型")
    private String questionType;
    @Comment("排序")
    private Integer sort;
    @Comment("答题内容")
    private String content;
    @Comment("标准分值")
    private Integer score;
    @TableField(alias = "win_score")
    @Comment("得分")
    private Integer winScore;
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