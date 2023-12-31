package com.ylhl.phlab.param;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.mapper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@ApiModel("题目信息")
@Data
@EqualsAndHashCode(callSuper = true)
public class TraQuestionInfoParam extends Page<TraQuestionInfoParam> {
    @TableId
    @ApiModelProperty("题目ID")
    @TableField(alias = "question_id")
    private String questionId;
    @ApiModelProperty("批量题目id")
    private List<String> questionIds;
    @ApiModelProperty("题干")
    @TableField(alias = "question_title")
    private String questionTitle;
    @ApiModelProperty("题目类型")
    @TableField(alias = "question_type")
    private String questionType;
    @ApiModelProperty("业务类型")
    @TableField(alias = "business_type")
    private Integer businessType;
    @ApiModelProperty("分值")
    private Integer score;
    @ApiModelProperty("标签")
    private String tag;
    @ApiModelProperty("选项内容")
    @TableField(alias = "question_content")
    private List<JSONObject> questionContent;
    @ApiModelProperty("展示唯一id")
    @TableField(alias = "unique_id")
    private String uniqueId;
    @ApiModelProperty("正确答案")
    @TableField(alias = "correct_answer")
    private String correctAnswer;
    @ApiModelProperty("答案解析")
    private String analysis;
    @ApiModelProperty("图片路径")
    @TableField(alias = "pic_path")
    private String picPath;
}