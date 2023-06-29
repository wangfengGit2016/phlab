package com.ylhl.phlab.param;

import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.domain.TraPaperInfo;
import com.ylhl.phlab.mapper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


@ApiModel("试卷信息")
@Data
@EqualsAndHashCode(callSuper = true)
public class TraPaperInfoParam extends Page<TraPaperInfo> {
    @TableId
    @TableField(alias = "paper_id")
    @ApiModelProperty("")
    private String paperId;
    @TableField(alias = "paper_name")
    @ApiModelProperty("试卷名称")
    private String paperName;
    @TableField(alias = "paper_type")
    @ApiModelProperty("试卷类型 1.固定试卷 2.限时试卷")
    private String paperType;
    @TableField(alias = "question_count")
    @ApiModelProperty("题目数量")
    private Integer questionCount;
    @TableField(alias = "pass_score")
    @ApiModelProperty("及格分数")
    private Integer passScore;
    @ApiModelProperty("试卷总分")
    private Integer score;
    @TableField(alias = "exam_duration")
    @ApiModelProperty("考试时长(分钟)")
    private Integer examDuration;
    @ApiModelProperty("发布状态 0未发布 1已发布")
    private String status;
    @TableField(alias = "create_id")
    @ApiModelProperty("创建人id")
    private String createId;
    @TableField(alias = "create_name")
    @ApiModelProperty("创建人姓名")
    private String createName;
}
