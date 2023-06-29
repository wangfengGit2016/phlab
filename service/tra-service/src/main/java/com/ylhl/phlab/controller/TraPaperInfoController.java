package com.ylhl.phlab.controller;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.param.TraPaperInfoParam;
import com.ylhl.phlab.service.impl.TraPaperInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "TraPaperInfoController", description = "人员管培-试卷管理")
@Slf4j
@RestController
@RequestMapping("/tra/paper")
public class TraPaperInfoController {

    @Resource
    TraPaperInfoService traPaperInfoService;

    @ApiOperation("试卷分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody TraPaperInfoParam param){
        return traPaperInfoService.page((JSONObject) JSONObject.toJSON(param));
    }
    @ApiOperation("试卷列表")
    @PostMapping(value="/list")
    public JSONObject list(@RequestBody TraPaperInfoParam param){
        return traPaperInfoService.list((JSONObject) JSONObject.toJSON(param));
    }


    @ApiOperation("新增试卷")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody TraPaperInfoParam param){

        return traPaperInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("编辑试卷")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody TraPaperInfoParam param){

        return traPaperInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("删除试卷")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody TraPaperInfoParam param){

        return traPaperInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("试卷详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody TraPaperInfoParam param){

        return traPaperInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }
    @ApiOperation("试卷发布")
    @PostMapping(value="/publish")
    public JSONObject publish(@RequestBody TraPaperInfoParam param){

        return traPaperInfoService.publish((JSONObject) JSONObject.toJSON(param));
    }
}
