package com.ylhl.phlab.controller;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.param.SysMenuParam;
import com.ylhl.phlab.service.impl.SysMenuInfoService;
import com.ylhl.phlab.utils.JsonUtils;
import com.ylhl.phlab.utils.NodesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "SysMenuController",description = "系统-菜单")
@Slf4j
@RestController
@RequestMapping("/sys/menu")
public class SysMenuController {

    @Resource
    SysMenuInfoService sysMenuInfoService;

    @ApiOperation("菜单分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysMenuParam param){

        return sysMenuInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("菜单树")
    @PostMapping(value="/tree")
    public JSONObject tree(@RequestBody SysMenuParam param){
        JSONObject res =  sysMenuInfoService.list((JSONObject) JSONObject.toJSON(param));
        res.put("tree",NodesUtils.getChildNodes(JsonUtils.getList(res,"list"), DictConstants.ROOT_NODE));
        res.remove("list");
        return res;
    }

    @ApiOperation("菜单列表")
    @PostMapping(value="/list")
    public JSONObject list(@RequestBody SysMenuParam param){

        return sysMenuInfoService.list((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("菜单创建")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody SysMenuParam param){

        return sysMenuInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("菜单编辑")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody SysMenuParam param){

        return sysMenuInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("菜单删除")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody SysMenuParam param){

        return sysMenuInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("菜单详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody SysMenuParam param){

        return sysMenuInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }
}
