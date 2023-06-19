package com.ylhl.phlab.controller;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.jlefebure.spring.boot.minio.MinioConfigurationProperties;
import com.jlefebure.spring.boot.minio.MinioException;
import com.jlefebure.spring.boot.minio.MinioService;
import com.ylhl.phlab.handler.ExcelHandler;
import com.ylhl.phlab.param.SysFileParam;
import com.ylhl.phlab.service.impl.SysFileInfoService;
import com.ylhl.phlab.utils.AssertUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;

@Api(tags = "SysFileController",description = "系统-文件")
@Slf4j
@RestController
@RequestMapping("/sys/file")
public class SysFileController {
    @Resource
    SysFileInfoService sysFileInfoService;
    @Resource
    MinioService minioService;
    @Resource
    MinioConfigurationProperties minioConfigurationProperties;
    @Resource
    ExcelHandler excelHandler;

    @Value("${server.url}")
    String serverUrl;


    @ApiOperation("文件分页")
    @PostMapping(value="/page")
    public JSONObject page(@RequestBody SysFileParam param){

        return sysFileInfoService.page((JSONObject) JSONObject.toJSON(param));
    }

    @ApiOperation("minio文件上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type",value = "业务类型,【MessageHandel】消息上传（demo示例）;无需关联不传"),
            @ApiImplicitParam(name = "id",value = "业务ID,无需关联不传")
    })
    @PostMapping(value="/upload")
    public JSONObject upload(@RequestParam("file") MultipartFile file, String type,String id) throws IOException, MinioException {
        //1.文件上传
        AssertUtil.isTrue(file.getOriginalFilename()!=null&&!file.getOriginalFilename().contains("."),"未知文件类型");
        String fileName = IdUtil.fastSimpleUUID();
        if(file.getOriginalFilename().contains(".")){
            fileName=fileName+file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        }
        Path path = Paths.get(fileName);
        minioService.upload(path,file.getInputStream(),file.getContentType());
        //2.文件信息入库
        SysFileParam param = new SysFileParam();
        param.setBucket(minioConfigurationProperties.getUrl());
        param.setBucket(minioConfigurationProperties.getBucket());
        param.setOriginName(file.getOriginalFilename());
        param.setFilePath(serverUrl+"/"+minioConfigurationProperties.getBucket()+"/"+fileName);
        param.setContentType(file.getContentType());
        param.setFileSize(String.valueOf(file.getSize()/1024));
        param.setUserId((String)StpUtil.getLoginId());
        JSONObject res = sysFileInfoService.insert((JSONObject) JSONObject.toJSON(param));
        res.put("path",param.getFilePath());
        //3.文件解析
        excelHandler.doService(file, type,id);
        return res;
    }

    @ApiOperation(value = "获取文件",hidden = true)
    @GetMapping(value="/{id}")
    public void detail(HttpServletResponse response, @PathVariable String id) throws IOException {
        JSONObject data = new JSONObject();
        data.put("id",id);
        JSONObject item = sysFileInfoService.detail(data);
        File file = new File(item.getString("filePath"));
        response.setContentType("application/force-download");
        response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(item.getString("originName"),"UTF-8"));
        FileInputStream in = new FileInputStream(file);
        int len;
        byte[] buffer = new byte[1024];
        OutputStream out = response.getOutputStream();
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
    }
}
