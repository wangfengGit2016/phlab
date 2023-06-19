package com.ylhl.phlab.controller;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.domain.SysRoleMenuRel;
import com.ylhl.phlab.param.SysRoleParam;
import com.ylhl.phlab.service.impl.SysRoleInfoService;
import com.ylhl.phlab.service.impl.SysRoleMenuRelService;
import com.ylhl.phlab.utils.AssertUtil;
import com.ylhl.phlab.utils.JsonUtils;
import com.ylhl.phlab.utils.NodesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "SysRoleController",description = "系统-角色")
@Slf4j
@RestController
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    SysRoleInfoService sysRoleInfoService;
    @Resource
    SysRoleMenuRelService sysRoleMenuRelService;


    @ApiOperation("角色分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysRoleParam param){

        return sysRoleInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("角色树")
    @PostMapping(value="/tree")
    public JSONObject tree(@RequestBody SysRoleParam param){
        JSONObject res =  sysRoleInfoService.list((JSONObject) JSONObject.toJSON(param));
        res.put("tree", NodesUtils.getChildNodes(JsonUtils.getList(res,"list"), DictConstants.ROOT_NODE));
        res.remove("list");
        return res;
    }

    @ApiOperation("角色创建")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody SysRoleParam param){

        return sysRoleInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("角色编辑")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody SysRoleParam param){

        return sysRoleInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("角色删除")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody SysRoleParam param){

        return sysRoleInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("角色详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody SysRoleParam param){
       JSONObject res = sysRoleInfoService.detail((JSONObject) JSONObject.toJSON(param));
        return res;
    }

    @ApiOperation("角色权限分配")
    @PostMapping(value="/assign")
    public JSONObject assign(@RequestBody SysRoleParam param){
        AssertUtil.isTrue(StringUtils.isBlank(param.getId()),"角色id不能为空");
        if(param.getMenus()!=null){
            SysRoleMenuRel rel = new SysRoleMenuRel();
            rel.setRoleId(param.getId());
            sysRoleMenuRelService.batchAssign(param.getId(),param.getMenus());
        }
        return new JSONObject();
    }
}
