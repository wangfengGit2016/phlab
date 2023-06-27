package com.ylhl.phlab.controller;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.param.SysStaffParam;
import com.ylhl.phlab.service.impl.SysStaffInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "SysStaffController",description = "系统-职工")
@Slf4j
@RestController
@RequestMapping("/sys/staff")
public class SysStaffController {
    @Resource
    SysStaffInfoService sysStaffInfoService;


    @ApiOperation("职工分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysStaffParam param){

        return sysStaffInfoService.page((JSONObject) JSONObject.toJSON(param));
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
