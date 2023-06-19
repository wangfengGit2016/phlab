package com.ylhl.phlab.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Primary
@Component
public class ExcelHandlerImpl implements ExcelHandler {

    @Override
    public JSONObject doService(MultipartFile file, String type, String id) {
       Map<String,ExcelHandler> map=SpringUtil.getBeansOfType(ExcelHandler.class);
       ExcelHandler handler = map.get(type);
       if(handler!=null){
          return handler.doService(file,type,id);
       }
       return  new JSONObject();
    }
}
