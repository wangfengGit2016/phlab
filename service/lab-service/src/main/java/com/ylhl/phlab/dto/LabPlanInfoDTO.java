package com.ylhl.phlab.dto;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.domain.LabPlanFileRel;
import com.ylhl.phlab.domain.SysFileInfo;
import com.ylhl.phlab.mapper.Page;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
@ApiModel("下发计划")
@Data
public class LabPlanInfoDTO extends Page<LabPlanInfoDTO> {
    @ApiModelProperty("计划id")
    private String planId;
    @ApiModelProperty("计划ids")
    private String planIds;
    @NotBlank(message = "文号不能为空")
    @ApiModelProperty("文号")
    private String docNumber;
    @ApiModelProperty("标题")
    @NotBlank(message = "标题不能为空")
    private String title;
    @ApiModelProperty("年度")
    private String year;
    @ApiModelProperty("地区数量")
    private Integer siteTotal;
    @ApiModelProperty("盲样种类id")
    private String typeId;
    @ApiModelProperty("盲样种类")
    @NotBlank(message = "盲样种类不能为空")
    private String typeName;
    @ApiModelProperty("科室id")
    private String deptId;
    @ApiModelProperty("科室")
    private String deptName;
    @NotBlank(message = "联系人不能为空")
    @ApiModelProperty("联系人")
    private String planUserName;
    @NotBlank(message = "联系方式不能为空")
    @ApiModelProperty("联系方式")
    private String planPhone;
    @ApiModelProperty("领取样本截止日期")
    @NotBlank(message = "领取样本截止日期不能为空")
    private String sampleDate;
    @ApiModelProperty("检测数据上传截止日期")
    @NotBlank(message = "检测数据上传截止日期不能为空")
    private String dataDate;
    @ApiModelProperty("具体内容")
    private String content;
    @ApiModelProperty("发布时间")
    private String releaseTime;
    @ApiModelProperty("评价跳转状态 0：需要评价 1：不需要评价")
    private String needEval;
    @ApiModelProperty("发布状态")
    private String status;
    @ApiModelProperty("表头信息")
    private String excelHead;
    @ApiModelProperty("表体信息")
    private String excelBody;
    @ApiModelProperty("创建人id")
    private String createId;
    @ApiModelProperty("创建人姓名")
    private String createName;
    @ApiModelProperty("创建时间")
    private Date createTime;
    @ApiModelProperty("更新人id")
    private String updateId;
    @ApiModelProperty("更新人姓名")
    private String updateName;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("删除人id")
    private String deleteId;
    @ApiModelProperty("删除人姓名")
    private String deleteName;
    @ApiModelProperty("删除时间")
    private Date deleteTime;
    @ApiModelProperty("删除状态 0：未删除 1：已删除")
    private String deleted;
    @ApiModelProperty("文件集合")
    private List<JSONObject> fileList;
    @ApiModelProperty("表头集合")
    private List<JSONObject> headList;
    @ApiModelProperty("表体集合")
    private List<JSONObject> bodyList;
    @NotNull(message = "选择下级单位不能为空")
    @ApiModelProperty("科室集合")
    private List<List<String>> deptList;
    @ApiModelProperty("文件反显字段")
    private String fileMessage;
    @ApiModelProperty("科室反显字段")
    private String deptMessage;
}