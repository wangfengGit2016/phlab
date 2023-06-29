package com.ylhl.phlab.dto;

import cn.hutool.json.JSONObject;
import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.mapper.Page;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class LabDataInfoDTO extends Page<LabDataInfoDTO> {

    private String dataId;
    private String planId;
    private String year;
    private String docNumber;
    private String typeId;
    private String typeName;
    private String deptId;
    private String deptName;
    private String staffId;
    private String staffName;
    private String siteId;
    private String siteName;
    private String uploadUserName;
    private String uploadPhone;
    private String uploadTime;
    private String sampleStatus;
    private String uploadDataStatus;
    private String evalStatus;
    private String evalResult;
    private String evalContent;
    private String evalId;
    private String evalName;
    private Date evalTime;
    private String labName;
    private String labAddress;
    private String labUserName;
    private String labPhone;
    private String title;
    private String planUserName;
    private String planPhone;
    private String context;
    private String dataDeptId;
    private String dataDeptName;
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
}