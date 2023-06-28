package com.ylhl.phlab.controller;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.param.SysOrganParam;
import com.ylhl.phlab.param.SysStaffParam;
import com.ylhl.phlab.service.impl.SysOrganInfoService;
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

@Api(tags = "SysOrganController",description = "系统-组织架构")
@Slf4j
@RestController
@RequestMapping("/sys/organ")
public class SysOrganController {
    @Resource
    SysOrganInfoService sysOrganInfoService;

    @ApiOperation("组织架构分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysOrganParam param){

        return sysOrganInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("组织架构树")
    @PostMapping(value="/tree")
    public JSONObject tree(@RequestBody SysStaffParam param){
        JSONObject organ = sysOrganInfoService.list(new JSONObject());
        if(StringUtils.isBlank(param.getId())){
            param.setId(DictConstants.ROOT_NODE);
        }
        JSONObject res = new JSONObject();
        res.put("tree", NodesUtils.getChildNodes(JsonUtils.getList(organ,"list"),param.getId()));
        res.remove("list");
        return res;
    }

    @ApiOperation("区域树")
    @PostMapping(value="/regionTree")
    public JSONObject regionTree(@RequestBody SysStaffParam param){
        JSONObject data = new JSONObject();
        data.put("type","1");
        JSONObject organ = sysOrganInfoService.list(data);
        if(StringUtils.isBlank(param.getId())){
            param.setId(DictConstants.ROOT_NODE);
        }
        JSONObject res = new JSONObject();
        res.put("tree", NodesUtils.getChildNodes(JsonUtils.getList(organ,"list"),param.getId()));
        res.remove("list");
        return res;
    }


    @ApiOperation("组织架构列表")
    @PostMapping(value="/list")
    public JSONObject list(@RequestBody SysOrganParam param){

        return sysOrganInfoService.list((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("组织架构创建")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody SysOrganParam param){
        return sysOrganInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("组织架构编辑")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody SysOrganParam param){

        return sysOrganInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("组织架构删除")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody SysOrganParam param){

        return sysOrganInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("组织架构详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody SysOrganParam param){

        return sysOrganInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }

}
