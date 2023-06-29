package com.ylhl.phlab.dto;

import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class SysOrganInfoDTO {

    private String deptId;
    private String parentId;
    private String parentName;
    private String nodeName;
    private String nodeCode;
    private String fullName;
    private String type;
    private String level;
    private String remark;
    private String status;
    private int sort;
    private String deleted;
    private Date createTime;
    private Date updateTime;

}