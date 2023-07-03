package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_question_bak")
public class TraQuestionBak {

    @TableId
    @TableField(alias = "question_bak_id")
    @Comment("")
    private String questionBakId;
    @TableField(alias = "question_title")
    @Comment("题干")
    private String questionTitle;
    @TableField(alias = "question_type")
    @Comment("题目类型  0单选题 1多选题 2判断题 3主观题")
    private String questionType;
    @TableField(alias = "question_content")
    @Comment("选项内容")
    private String questionContent;
    @TableField(alias = "correct_answer")
    @Comment("正确答案")
    private String correctAnswer;
    @TableField(alias = "public_paper_id")
    @Comment("所属试卷id")
    private String publicPaperId;
    @TableField(alias = "question_sort")
    @Comment("题目排序")
    private Integer questionSort;
    @Comment("分数")
    private Integer score;
    @TableField(alias = "module_sort")
    @Comment("模块排序")
    private Integer moduleSort;
    @TableField(alias = "module_name")
    @Comment("模块名称")
    private String moduleName;
    @Comment("答案解析")
    private String analysis;
    @TableField(alias = "pic_path")
    @Comment("图片路径")
    private String picPath;
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