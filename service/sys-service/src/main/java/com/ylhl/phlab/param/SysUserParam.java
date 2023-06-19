package com.ylhl.phlab.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@ApiModel("用户")
@Data
public class SysUserParam{
    @ApiModelProperty(value = "页长",reference = "page")
    protected long size = 10;
    @ApiModelProperty(value = "当前页",reference = "page")
    protected long current = 1;
    @ApiModelProperty(value = "开始时间",reference = "page")
    private Date startTime;
    @ApiModelProperty(value = "结束时间",reference = "page")
    private Date endTime;

    @ApiModelProperty(value = "",reference = "update,delete,detail,disable")
    private String id;
    @ApiModelProperty(value = "用户名",reference = "create,login,page")
    private String userName;
    @ApiModelProperty(value = "登录密码",reference = "create,login")
    private String password;
    @Size(message = "姓名不能超过20个字符",max = 20)
    @NotBlank(message = "")
    @ApiModelProperty(value = "真实姓名",reference = "create,page,update")
    private String realName;
    @ApiModelProperty(value = "电话",reference = "create,update")
    private String phone;
    @ApiModelProperty(value = "身份证号",reference = "create,update")
    private String idcard;
    @ApiModelProperty(value = "工作单位",reference = "create,page,update")
    private String unit;
    @ApiModelProperty(value = "所属区域",reference = "create,update")
    private String regionId;
    @ApiModelProperty(value = "用户状态 0冻结 1解冻",reference = "page,disable")
    private String userStatus;
    @ApiModelProperty(value = "是否超管")
    private String adminType;
    @ApiModelProperty(value = "机构id")
    private String organizationId;

    @ApiModelProperty(value = "角色集合,字符串list",reference = "create,update")
    private List<String> roles;

    @ApiModelProperty(value = "旧密码",reference = "repass")
    private String oldPass;
    @ApiModelProperty(value = "新密码",reference = "repass")
    private String newPass;

    @ApiModelProperty(value = "验证码ID",reference = "login")
    private String codeKey;
    @ApiModelProperty(value = "验证码值",reference = "login")
    private String code;

}
