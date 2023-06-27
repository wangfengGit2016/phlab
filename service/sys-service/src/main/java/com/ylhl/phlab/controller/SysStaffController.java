package com.ylhl.phlab.controller;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.param.SysStaffParam;
import com.ylhl.phlab.service.impl.SysDeptInfoService;
import com.ylhl.phlab.service.impl.SysOrganInfoService;
import com.ylhl.phlab.service.impl.SysRegionInfoService;
import com.ylhl.phlab.service.impl.SysStaffInfoService;
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
import java.util.ArrayList;
import java.util.List;

@Api(tags = "SysStaffController",description = "系统-职工")
@Slf4j
@RestController
@RequestMapping("/sys/staff")
public class SysStaffController {
    @Resource
    SysRegionInfoService sysRegionInfoService;
    @Resource
    SysOrganInfoService sysOrganInfoService;
    @Resource
    SysDeptInfoService sysDeptInfoService;
    @Resource
    SysStaffInfoService sysStaffInfoService;


    @ApiOperation("职工分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysStaffParam param){

        return sysStaffInfoService.page((JSONObject) JSONObject.toJSON(param));
    }


    @ApiOperation("职工树")
    @PostMapping(value="/tree")
    public JSONObject tree(@RequestBody SysStaffParam param){
        JSONObject region = sysRegionInfoService.list(new JSONObject());
        JSONObject organ = sysOrganInfoService.list(new JSONObject());
        JSONObject dept = sysDeptInfoService.list(new JSONObject());
        List<JSONObject> menus = new ArrayList<>();
        menus.addAll(JsonUtils.getList(region,"list"));
        menus.addAll(JsonUtils.getList(organ,"list"));
        menus.addAll(JsonUtils.getList(dept,"list"));
        if(StringUtils.isBlank(param.getId())){
            param.setId(DictConstants.ROOT_NODE);
        }
        JSONObject res = new JSONObject();
        res.put("tree", NodesUtils.getChildNodes(menus,param.getId()));
        res.remove("list");
        return res;
    }


    @ApiOperation("职工列表")
    @PostMapping(value="/list")
    public JSONObject list(@RequestBody SysStaffParam param){

        return sysStaffInfoService.list((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("职工创建")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody SysStaffParam param){
        return sysStaffInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("职工编辑")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody SysStaffParam param){

        return sysStaffInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("职工删除")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody SysStaffParam param){

        return sysStaffInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("职工详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody SysStaffParam param){

        return sysStaffInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }

}
