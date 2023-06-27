package com.ylhl.phlab.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.CacheConstants;
import com.ylhl.phlab.domain.SysUserInfo;
import com.ylhl.phlab.enums.AdminTypeEnum;
import com.ylhl.phlab.enums.UserStatusEnum;
import com.ylhl.phlab.param.SysUserParam;
import com.ylhl.phlab.service.impl.SysRoleInfoService;
import com.ylhl.phlab.service.impl.SysUserInfoService;
import com.ylhl.phlab.utils.AssertUtil;
import com.ylhl.phlab.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

@Api(tags = "SysUserController",description = "系统-用户")
@Slf4j
@RestController
@RequestMapping("/sys/user")
public class SysUserController {
    @Resource
    SysUserInfoService sysUserInfoService;
    @Resource
    SysRoleInfoService sysRoleInfoService;
    @Resource
    StringRedisTemplate stringRedisTemplate;

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public JSONObject login(@RequestBody SysUserParam param){
        log.info("{}",param);
        String code = stringRedisTemplate.opsForValue().get(CacheConstants.CAPTCHA_PREFIX + param.getCodeKey());
//        AssertUtil.isNull(code,"验证码已失效");
//        AssertUtil.isFalse(code.equalsIgnoreCase(param.getCode()),"验证码有误");
        JSONObject user = new JSONObject();
        user.put("userName",param.getUserName());
        SysUserInfo userInfo=sysUserInfoService.info(user);
        AssertUtil.isNull(userInfo,"用户不存在");
        AssertUtil.isFalse(BCrypt.checkpw(param.getPassword(),userInfo.getPassword()),"密码不正确");
        StpUtil.login(userInfo.getId());
        StpUtil.getSession().set("username",userInfo.getUserName());
        StpUtil.getSession().set("password",userInfo.getPassword());
        StpUtil.getSession().set("regionId",userInfo.getRegionId());
        StpUtil.getSession().set("organId",userInfo.getOrganId());
        JSONObject res = new JSONObject();
        user.put("id",userInfo.getId());
        JSONObject info = sysUserInfoService.detail(user);
        StpUtil.getSession().set("roleCode",info.getString("roleCode"));
        if(AdminTypeEnum.ADMIN.match(info.getString("roleCode"))){
            info.put("permission", Collections.singletonList("admin"));
        }
        List<String> permission = JsonUtils.getList(info,"permission",String.class);
        res.put("isNeedWarn",permission.contains("admin")||permission.contains("6002"));
        res.put("username",userInfo.getUserName());
        res.put("tokenName",StpUtil.getTokenName());
        res.put("token",StpUtil.getTokenValue());
        res.put("userInfo",info);
        return res;
    }

    @ApiOperation("账户编辑")
    @PostMapping(value="/account")
    public JSONObject account(@RequestBody SysUserParam param){
        JSONObject user = new JSONObject();
        user.put("userName",param.getUserName());
        SysUserInfo userInfo=sysUserInfoService.info(user);
        AssertUtil.isNull(userInfo,"用户不存在");
        AssertUtil.isFalse(BCrypt.checkpw(param.getPassword(),userInfo.getPassword()),"密码不正确");
        user.put("id",StpUtil.getLoginId());
        user.put("realName",param.getRealName());
        user.put("phone",param.getPhone());
        sysUserInfoService.update(user);
        return user;
    }

    @ApiOperation("用户退出")
    @PostMapping("/logout")
    public JSONObject logout() {
        StpUtil.logout();
        return new JSONObject();
    }

    @ApiOperation("用户分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysUserParam param){
        Function<SysUserParam, String> getId = SysUserParam::getId;
        param.setRegionId((String) StpUtil.getSession().get("regionId"));
        return sysUserInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("用户创建")
    @Transactional
    @PostMapping(value="/create")
    public JSONObject create(@Validated @RequestBody SysUserParam param){
        param.setPassword(BCrypt.hashpw(param.getPassword()));

        JSONObject res = sysUserInfoService.insert((JSONObject) JSONObject.toJSON(param));
        AssertUtil.isNull(param.getRoles(),"请设置用户角色");
        sysRoleInfoService.batchAssign(res.getString("id"),param.getRoles());
        return new JSONObject();
    }

    @ApiOperation("用户编辑")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody SysUserParam param){
        if(StringUtils.isNotBlank(param.getPassword())){
            param.setPassword(BCrypt.hashpw(param.getPassword()));
        }else {
            param.setPassword(null);
        }
        sysUserInfoService.update((JSONObject) JSONObject.toJSON(param));
        AssertUtil.isNull(param.getRoles(),"请设置用户角色");
        sysRoleInfoService.batchAssign(param.getId(),param.getRoles());
        return new JSONObject();
    }

    @ApiOperation("用户删除")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody SysUserParam param){
        return sysUserInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("用户详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody SysUserParam param){
        return sysUserInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("重设密码")
    @PostMapping(value="/repass")
    public JSONObject repass(@RequestBody SysUserParam param){
        String password= (String) StpUtil.getSession().get("password");
        AssertUtil.isFalse(BCrypt.checkpw(param.getOldPass(),password),"密码不正确");
        SysUserParam sysUserParam = new SysUserParam();
        sysUserParam.setId((String) StpUtil.getLoginId());
        sysUserParam.setPassword(BCrypt.hashpw(param.getNewPass()));
        return sysUserInfoService.update((JSONObject) JSONObject.toJSON(sysUserParam));
    }

    @ApiOperation("用户冻结-解冻")
    @PostMapping(value="/disable")
    public JSONObject disable(@RequestBody SysUserParam param){
        StpUtil.logoutByLoginId(param.getId());
        SysUserParam sysUserParam = new SysUserParam();
        sysUserParam.setId(param.getId());
        sysUserParam.setUserStatus(UserStatusEnum.DISABLE.match(param.getUserStatus())?UserStatusEnum.ABLE.getCode():UserStatusEnum.DISABLE.getCode());
        return sysUserInfoService.update((JSONObject) JSONObject.toJSON(sysUserParam));
    }
}
