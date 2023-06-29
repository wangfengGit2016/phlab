package com.ylhl.phlab.dto;

import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import lombok.Data;

import java.util.Date;
@Data
public class SysFileInfoDTO {
    private String id;
    private Integer location;
    private String bucket;
    private String filePath;
    private String originName;
    private String fileSuffix;
    private String contentType;
    private String fileSize;
    private String userId;
    private String organizationId;
    private String deleted;
    private Date createTime;
    private Date updateTime;
}
