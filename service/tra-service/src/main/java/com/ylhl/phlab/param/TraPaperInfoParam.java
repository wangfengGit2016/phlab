package com.ylhl.phlab.param;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.domain.TraPaperInfo;
import com.ylhl.phlab.mapper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;


@ApiModel("试卷信息")
@Data
@EqualsAndHashCode(callSuper = true)
public class TraPaperInfoParam extends Page<TraPaperInfo> {
    @TableId
    @TableField(alias = "paper_id")
    @ApiModelProperty("")
    private String paperId;
    @ApiModelProperty("批量删除试卷id")
    private List<String> paperIds;
    @TableField(alias = "paper_name")
    @ApiModelProperty("试卷名称")
    private String paperName;
    @TableField(alias = "question_count")
    @ApiModelProperty("题目数量")
    private Integer questionCount;
    @TableField(alias = "start_exam_time")
    @ApiModelProperty("开始考试时间")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date startExamTime;
    @TableField(alias = "end_exam_time")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @ApiModelProperty("考试截至时间")
    private Date endExamTime;
    @ApiModelProperty("题目集合")
    private List<JSONObject> items;
    @ApiModelProperty("展示唯一id")
    @TableField(alias = "unique_id")
    private String uniqueId;
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
