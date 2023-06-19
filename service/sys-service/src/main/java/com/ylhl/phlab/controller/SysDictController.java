package com.ylhl.phlab.controller;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.param.SysDictParam;
import com.ylhl.phlab.service.impl.SysDictInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@Api(tags = "SysDictController",description = "系统-字典")
@Slf4j
@RestController
@RequestMapping("/sys/dict")
public class SysDictController {
    @Resource
    SysDictInfoService sysDictInfoService;
    @ApiOperation("字典分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysDictParam param){

        return sysDictInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("字典创建")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody SysDictParam param){
        param.setParentId(DictConstants.ROOT_NODE);
        return sysDictInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("字典编辑")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody SysDictParam param){

        return sysDictInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("字典删除")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody SysDictParam param){

        return sysDictInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("字典项列表")
    @PostMapping(value="/itemList")
    public JSONObject itemList(@RequestBody SysDictParam param){
        JSONObject data = new JSONObject();
        data.put("parentId",param.getId());
        data.put("dictCode",param.getDictCode());
        return sysDictInfoService.itemList(data);
    }

    @ApiOperation("字典项创建")
    @PostMapping(value="/itemCreate")
    public JSONObject itemCreate(@RequestBody SysDictParam param){

        return sysDictInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("字典项编辑")
    @PostMapping(value="/itemUpdate")
    public JSONObject itemUpdate(@RequestBody SysDictParam param){

        return sysDictInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("字典项删除")
    @PostMapping(value="/itemDelete")
    public JSONObject itemDelete(@RequestBody SysDictParam param){

        return sysDictInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }
}
