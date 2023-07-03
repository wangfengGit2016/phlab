package com.ylhl.phlab.controller;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.param.TraQuestionInfoParam;
import com.ylhl.phlab.service.impl.TraQuestionInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "TraQuestionInfoController", description = "人员管培-题目管理")
@Slf4j
@RestController
@RequestMapping("/tra/question")
public class TraQuestionInfoController {

    @Resource
    TraQuestionInfoService traQuestionInfoService;

    @ApiOperation("题目分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody TraQuestionInfoParam param){
        return traQuestionInfoService.page((JSONObject) JSONObject.toJSON(param));
    }
    @ApiOperation("题目列表")
    @PostMapping(value="/list")
    public JSONObject list(@RequestBody TraQuestionInfoParam param){
        return traQuestionInfoService.list((JSONObject) JSONObject.toJSON(param));
    }


    @ApiOperation("新增题目")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody TraQuestionInfoParam param){

        return traQuestionInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("编辑题目")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody TraQuestionInfoParam param){

        return traQuestionInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("删除题目")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody TraQuestionInfoParam param){

        return traQuestionInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("题目详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody TraQuestionInfoParam param){

        return traQuestionInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }

}
