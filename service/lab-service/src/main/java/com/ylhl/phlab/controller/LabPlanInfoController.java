package com.ylhl.phlab.controller;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.domain.LabPlanInfo;
import com.ylhl.phlab.dto.LabPlanInfoDTO;
import com.ylhl.phlab.dto.LabPlanTypeRelDTO;
import com.ylhl.phlab.dto.SysOrganInfoDTO;
import com.ylhl.phlab.service.impl.LabPlanInfoService;
import com.ylhl.phlab.utils.FieldUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(tags = "LabPlanInfoController", description = "实验室质控-下发计划")
@Slf4j
@RestController
@RequestMapping("/lab/plan")
public class LabPlanInfoController {
    @Resource
    LabPlanInfoService labPlanInfoService;
    @ApiOperation("下发计划分页")
    @PostMapping(value = "/page")
    public JSONObject page(@RequestBody LabPlanInfoDTO param) {
        return labPlanInfoService.page((JSONObject) JSONObject.toJSON(param));
    }
    @ApiOperation("下发计划获取上次填写记录")
    @PostMapping(value = "/detailByBefore")
    public JSONObject detailByBefore(@RequestBody LabPlanInfoDTO param) {
        return labPlanInfoService.detailByBefore((JSONObject) JSONObject.toJSON(param));
    }
    @ApiOperation("下发计划添加")
    @PostMapping(value = "/add")
    public JSONObject add(@RequestBody LabPlanInfoDTO param) {
        //FieldUtil.setInsert(param);
        return labPlanInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("下发计划修改")
    @PostMapping(value = "/update")
    public JSONObject update(@RequestBody LabPlanInfoDTO param) {
        //FieldUtil.setUpdate(param);
        return labPlanInfoService.update((JSONObject) JSONObject.toJSON(param));
    }
    @ApiOperation("下发计划详情")
    @PostMapping(value = "/detail")
    public JSONObject detail(@RequestBody LabPlanInfoDTO param) {
        return labPlanInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("下发计划删除")
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody LabPlanInfoDTO param) {
        //FieldUtil.setDelete(param);
        return labPlanInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("下发计划撤回")
    @PostMapping(value = "/revoke")
    public JSONObject revoke(@RequestBody LabPlanInfoDTO param) {
        //FieldUtil.setUpdate(param);
        return labPlanInfoService.revoke((JSONObject) JSONObject.toJSON(param));
    }
    @ApiOperation("下发计划获取场地科室树")
    @PostMapping(value = "/siteAndDeptTree")
    public JSONObject siteAndDeptTree(@RequestBody SysOrganInfoDTO param) {
        return labPlanInfoService.siteAndDeptTree((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("下发计划盲样拉取结果")
    @PostMapping(value = "/typeResult")
    public JSONObject typeResult(@RequestBody LabPlanTypeRelDTO param) {
        return labPlanInfoService.typeResult((JSONObject) JSONObject.toJSON(param));
    }
    @ApiOperation("下发计划文件预览")
    @PostMapping(value = "/fileToPdf")
    public JSONObject fileToPdf(@RequestBody LabPlanInfoDTO param) {
        return labPlanInfoService.fileToPdf((JSONObject) JSONObject.toJSON(param));
    }
}
