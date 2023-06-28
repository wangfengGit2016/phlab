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

@Api(tags = "LabDataInfoController", description = "实验室质控-检测数据")
@Slf4j
@RestController
@RequestMapping("/lab/data")
public class LabDataInfoController {
    @Resource
    LabDataInfoService labDataInfoService;
    @ApiOperation("检测数据分页")
    @PostMapping(value = "/page")
    public JSONObject page(@RequestBody LabDataInfoDTO param) {
        return labDataInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("检测数据上报")
    @PostMapping(value = "/report")
    public JSONObject report(@RequestBody LabPlanInfoDTO param) {
        return labDataInfoService.report((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("检测数据补考")
    @PostMapping(value = "/repair")
    public JSONObject repair(@RequestBody LabPlanInfoDTO param) {
        return labDataInfoService.repair((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("检测数据领取样本")
    @PostMapping(value = "/receive")
    public JSONObject receive(@RequestBody LabPlanInfoDTO param) {
        return labDataInfoService.receive((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("检测数据获取上次填写记录")
    @PostMapping(value = "/detailByBefore")
    public JSONObject detailByBefore(@RequestBody LabPlanInfoDTO param) {
        return labDataInfoService.detailByBefore((JSONObject) JSONObject.toJSON(param));
    }

}
