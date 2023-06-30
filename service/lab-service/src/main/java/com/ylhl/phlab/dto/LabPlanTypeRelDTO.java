package com.ylhl.phlab.dto;

import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;

@Data
public class LabPlanTypeRelDTO {

    private String typeId;
    private String result;
    private String form;
    private String createId;
    private String createName;
    private Date createTime;
}
