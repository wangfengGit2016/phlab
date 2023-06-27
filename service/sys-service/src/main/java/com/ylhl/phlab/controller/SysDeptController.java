package com.ylhl.phlab.controller;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.param.SysDeptParam;
import com.ylhl.phlab.service.impl.SysDeptInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "SysDeptController",description = "系统-科室")
@Slf4j
@RestController
@RequestMapping("/sys/dept")
public class SysDeptController {
    @Resource
    SysDeptInfoService sysDeptInfoService;

    @ApiOperation("科室分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysDeptParam param){

        return sysDeptInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("科室列表")
    @PostMapping(value="/list")
    public JSONObject list(@RequestBody SysDeptParam param){

        return sysDeptInfoService.list((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("科室创建")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody SysDeptParam param){
        return sysDeptInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("科室编辑")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody SysDeptParam param){

        return sysDeptInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("科室删除")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody SysDeptParam param){

        return sysDeptInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("科室详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody SysDeptParam param){

        return sysDeptInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }

}
