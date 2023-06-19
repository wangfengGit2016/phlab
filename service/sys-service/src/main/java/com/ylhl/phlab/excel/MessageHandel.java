package com.ylhl.phlab.excel;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.handler.ExcelHandler;
import com.ylhl.phlab.service.impl.SysMessageInfoService;
import com.ylhl.phlab.utils.ExcelUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Component("MessageHandel")
public class MessageHandel implements ExcelHandler {
    @Resource
    SysMessageInfoService sysMessageInfoService;

    @Override
    public JSONObject doService(MultipartFile file, String type, String id) {
        List<JSONObject> list = ExcelUtils.execToData(file, sysMessageInfoService, MessageDTO.class, id);
        System.out.println(list);
        return new JSONObject();
    }
}
