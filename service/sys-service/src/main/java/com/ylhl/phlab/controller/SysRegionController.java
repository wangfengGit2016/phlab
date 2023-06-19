package com.ylhl.phlab.controller;


import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.param.SysRegionParam;
import com.ylhl.phlab.service.impl.SysRegionInfoService;
import com.ylhl.phlab.utils.JsonUtils;
import com.ylhl.phlab.utils.NodesUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Api(tags = "SysRegionController",description = "系统-区域")
@Slf4j
@RestController
@RequestMapping("/sys/region")
public class SysRegionController {
    @Resource
    SysRegionInfoService sysRegionInfoService;

    @ApiOperation("区域分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysRegionParam param){

        return sysRegionInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("区域树")
    @PostMapping(value="/tree")
    public JSONObject tree(@RequestBody SysRegionParam param){
        JSONObject res = sysRegionInfoService.list(new JSONObject());
        if(StringUtils.isBlank(param.getId())){
            param.setId(DictConstants.ROOT_NODE);
        }
        res.put("tree", NodesUtils.getChildNodes(JsonUtils.getList(res,"list"),param.getId()));
        res.remove("list");
        return res;
    }

    @ApiOperation("用户区域树")
    @PostMapping(value="/userTree")
    public JSONObject userTree(@RequestBody JSONObject param){
        JSONObject res = sysRegionInfoService.list(param);
        List<JSONObject> regions=JsonUtils.getList(res,"list");
        String regionId = StpUtil.getSession().getString("regionId");
        JSONObject tree = new JSONObject();
        regions.forEach(item->{
            if(item.getString("id").equals(regionId)){
                tree.putAll(item);
            }
        });
        tree.put("children",NodesUtils.getChildNodes(regions, regionId));
        res.put("tree", Collections.singletonList(tree));
        res.remove("list");
        return res;
    }

    @ApiOperation("区域列表")
    @PostMapping(value="/list")
    public JSONObject list(@RequestBody SysRegionParam param){

        return sysRegionInfoService.list((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("区域创建")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody SysRegionParam param){
        return sysRegionInfoService.insert((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("区域编辑")
    @PostMapping(value="/update")
    public JSONObject update(@RequestBody SysRegionParam param){

        return sysRegionInfoService.update((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("区域删除")
    @PostMapping(value="/delete")
    public JSONObject delete(@RequestBody SysRegionParam param){

        return sysRegionInfoService.delete((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("区域详情")
    @PostMapping(value="/detail")
    public JSONObject detail(@RequestBody SysRegionParam param){

        return sysRegionInfoService.detail((JSONObject) JSONObject.toJSON(param));
    }

}
