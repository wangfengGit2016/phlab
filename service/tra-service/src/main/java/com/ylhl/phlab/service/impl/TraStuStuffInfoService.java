package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraStuStuffInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraStuStuffInfoService")
public class TraStuStuffInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<TraStuStuffInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,TraStuStuffInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<TraStuStuffInfo> list=CoreBuilder.select().list(TraStuStuffInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraStuStuffInfo bean = BeanUtil.toBean(data,TraStuStuffInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("stuff_id",data.getString("stuffId")).remove(TraStuStuffInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraStuStuffInfo bean = BeanUtil.toBean(data,TraStuStuffInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("stuff_id",data.getString("stuffId")).one(TraStuStuffInfo.class);
          res.put("data",bean);
          return res;
      }

}