package com.ylhl.phlab.excel;

import com.ylhl.phlab.annotation.Excel;
import com.ylhl.phlab.annotation.ExcelField;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Data
@Excel(type = "MessageInfo")
public class MessageDTO {
    @NotBlank(message = "type不能为空")
    @Size(min = 10,max = 20,message = "类型长度不匹配")
    @ExcelField(alias = "类型",value = "类型")
    private String type;
    @ExcelField(alias = "标题",value = "标题")
    private String title;
    @ExcelField(alias = "内容",value = "内容")
    private String content;
    @ExcelField(alias = "错误信息",value = "错误信息")
    private String errorMassage;

}
