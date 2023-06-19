package com.ylhl.phlab.controller;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.param.SysLogParam;
import com.ylhl.phlab.service.impl.SysLogInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "SysLogController",description = "系统-日志")
@Slf4j
@RestController
@RequestMapping("/sys/log")
public class SysLogController {
    @Resource
    SysLogInfoService sysLogInfoService;
    @ApiOperation("日志分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysLogParam param){

        return sysLogInfoService.page((JSONObject) JSONObject.toJSON(param));
    }
}
