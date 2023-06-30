package com.ylhl.phlab.domain;

import com.ylhl.phlab.annotation.Comment;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
@Table("lab_data_type_rel")
public class LabDataTypeRel {

    @TableId
    @TableField(alias = "data_type_rel_id")
    @Comment("数据盲样结果id")
    private String dataTypeRelId;
    @TableField(alias = "data_id")
    @Comment("数据id")
    private String dataId;
    @TableField(alias = "sample_num")
    @Comment("样本编号")
    private String sampleNum;
    @TableField(alias = "judg_result")
    @Comment("判定结果")
    private String judgResult;
    @TableField(alias = "fluo_result")
    @Comment("荧光结果")
    private String fluoResult;
    @TableField(alias = "ct_num")
    @Comment("CT值")
    private String ctNum;
    @TableField(alias = "create_id")
    @Comment("创建人id")
    private String createId;
    @TableField(alias = "create_name")
    @Comment("创建人名称")
    private String createName;
    @TableField(alias = "create_time")
    @Comment("创建时间")
    private Date createTime;
    @TableField(alias = "update_id")
    @Comment("更新人id")
    private String updateId;
    @TableField(alias = "update_name")
    @Comment("更新人名称")
    private String updateName;
    @TableField(alias = "update_time")
    @Comment("更新时间")
    private Date updateTime;
    @Comment("删除人id")
    private String deleteId;
    @TableField(alias = "delete_name")
    @Comment("删除人名称")
    private String deleteName;
    @TableField(alias = "delete_time")
    @Comment("删除时间")
    private Date deleteTime;
    @Comment("删除状态 0：未删除 1：已删除")
    private String deleted;
}