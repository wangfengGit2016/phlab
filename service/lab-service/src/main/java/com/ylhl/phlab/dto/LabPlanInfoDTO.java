package com.ylhl.phlab.dto;

import cn.hutool.json.JSONObject;
import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.domain.LabPlanFileRel;
import com.ylhl.phlab.domain.SysFileInfo;
import com.ylhl.phlab.mapper.Page;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LabPlanInfoDTO extends Page<LabPlanInfoDTO> {
    private String planId;
    private String planIds;
    private String docNumber;
    private String title;
    private String year;
    private Integer siteTotal;
    private String typeId;
    private String typeName;
    private String deptId;
    private String deptName;
    private String planUserName;
    private String planPhone;
    private String sampleDate;
    private String dataDate;
    private String content;
    private String releaseTime;
    private String message;
    private String needEval;
    private String status;
    private String createId;
    private String createName;
    private Date createTime;
    private String updateId;
    private String updateName;
    private Date updateTime;
    private String deleteId;
    private String deleteName;
    private Date deleteTime;
    private String deleted;
    private List<JSONObject> fileList;
    private List<JSONObject> siteList;
    private List<JSONObject> deptList;
}