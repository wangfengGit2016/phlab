package com.ylhl.phlab.controller;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.dto.LabDataInfoDTO;
import com.ylhl.phlab.dto.LabPlanInfoDTO;
import com.ylhl.phlab.service.impl.LabDataInfoService;
import com.ylhl.phlab.service.impl.LabPlanInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "LabEvalInfoController", description = "实验室质控-在线评价")
@Slf4j
@RestController
@RequestMapping("/lab/eval")
public class LabEvalInfoController {
    @Resource
    LabDataInfoService labDataInfoService;

    @Resource
    LabPlanInfoService labPlanInfoService;
    @ApiOperation("在线评价汇总分页")
    @PostMapping(value = "/summaryPage")
    public JSONObject page(@RequestBody LabPlanInfoDTO param) {
        return labPlanInfoService.summaryPage((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("在线评价分页")
    @PostMapping(value = "/page")
    public JSONObject page(@RequestBody LabDataInfoDTO param) {
        return labDataInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("在线评价评价")
    @PostMapping(value = "/dataEval")
    public JSONObject dataEval(@RequestBody LabDataInfoDTO param) {
        return labDataInfoService.dataEval((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("在线评价详情")
    @PostMapping(value = "/detail")
    public JSONObject detail(@RequestBody LabDataInfoDTO param) {
        return labDataInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }

}
