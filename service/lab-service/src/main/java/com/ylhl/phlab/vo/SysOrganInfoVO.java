package com.ylhl.phlab.vo;

import com.ylhl.phlab.domain.SysOrganInfo;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SysOrganInfoVO {

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
    private List<SysOrganInfo> deptList;

}