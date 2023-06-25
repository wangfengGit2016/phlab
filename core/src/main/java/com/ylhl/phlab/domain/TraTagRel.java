package com.ylhl.phlab.domain;

import lombok.Data;
import java.util.Date;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableId;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.Comment;

@Data
@Table("tra_tag_rel")
public class TraTagRel {

    @TableId
    @Comment("")
    private String id;
    @TableField(alias = "tag_id")
    @Comment("标签id")
    private String tagId;
    @TableField(alias = "business_id")
    @Comment("业务id")
    private String businessId;

}