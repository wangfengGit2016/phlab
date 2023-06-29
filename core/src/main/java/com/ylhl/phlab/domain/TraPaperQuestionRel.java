package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_paper_question_rel")
public class TraPaperQuestionRel {

    @TableId
    @Comment("")
    private String id;
    @TableField(alias = "question_id")
    @Comment("题目id")
    private String questionId;
    @TableField(alias = "paper_id")
    @Comment("试卷id")
    private String paperId;
    @TableField(alias = "question_type")
    @Comment("题型")
    private String questionType;
    @Comment("排序")
    private Integer sort;

}