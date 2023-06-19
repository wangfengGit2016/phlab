package com.ylhl.phlab.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.jlefebure.spring.boot.minio.MinioService;
import com.ylhl.phlab.enums.MessageEnum;
import com.ylhl.phlab.excel.MessageDTO;
import com.ylhl.phlab.param.SysMessageParam;
import com.ylhl.phlab.service.impl.SysMessageInfoService;
import com.ylhl.phlab.utils.ExcelUtils;
import com.ylhl.phlab.utils.JsonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "SysMessageController",description = "系统-消息")
@Slf4j
@RestController
@RequestMapping("/sys/message")
public class SysMessageController {
    @Resource
    SysMessageInfoService sysMessageInfoService;
    @Resource
    MinioService minioService;

    @ApiOperation("消息数据")
    @PostMapping(value="/data")
    public JSONObject data(@RequestBody SysMessageParam param){
        JSONObject data = new JSONObject();
        data.put("userId",StpUtil.getLoginId());
        return sysMessageInfoService.data(data);
    }

    @ApiOperation("读所有数据")
    @PostMapping(value="/readAll")
    public JSONObject readAll(@RequestBody SysMessageParam param){
        JSONObject data = new JSONObject();
        data.put("userId",StpUtil.getLoginId());
        data.put("type",param.getType());
        return sysMessageInfoService.readAll(data);
    }

    @ApiOperation("读一条")
    @PostMapping(value="/readOne")
    public JSONObject readOne(@RequestBody SysMessageParam param){
        JSONObject data = new JSONObject();
        data.put("userId",StpUtil.getLoginId());
        data.put("id",param.getId());
        return sysMessageInfoService.readOne(data);
    }

    @ApiOperation("读最新一条数据")
    @PostMapping(value="/readFirst")
    public JSONObject readFirst(@RequestBody SysMessageParam param){
        JSONObject data = new JSONObject();
        data.put("userId",StpUtil.getLoginId());
        data.put("regionId",StpUtil.getSession().getString("regionId"));
        return sysMessageInfoService.readFirst(data);
    }

    @ApiOperation("区域创建")
    @PostMapping(value="/create")
    public JSONObject create(@RequestBody SysMessageParam param){
        sysMessageInfoService.submit(MessageEnum.getEnum(param.getType()),param.getBusinessId(),param.getTitle(),param.getContent(),param.getRegionId());
        return new JSONObject();
    }

    @ApiOperation(value = "文档导出",hidden = true)
    @GetMapping(value="/excel")
    public void excel(HttpServletRequest request, HttpServletResponse response) {
        Workbook wb = new SXSSFWorkbook(100);
        JSONObject res =sysMessageInfoService.list(new JSONObject());
        List<JSONObject> list = JsonUtils.getList(res,"list");
        List<MessageDTO> infos =list.stream().map(item-> BeanUtil.toBean(item, MessageDTO.class)).collect(Collectors.toList());
        ExcelUtils.dataToExcel(wb,new MessageDTO(),infos);
        ExcelUtils.writeWorkbook(response,wb,"测试.xlsx");
//        String fileName = IdUtil.fastSimpleUUID();
//        Path path = Paths.get(fileName);


    }


}
