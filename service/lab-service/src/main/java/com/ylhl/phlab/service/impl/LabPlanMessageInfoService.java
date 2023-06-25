package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabPlanMessageInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabPlanMessageInfoService")
public class LabPlanMessageInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabPlanMessageInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,LabPlanMessageInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabPlanMessageInfo> list=CoreBuilder.select().list(LabPlanMessageInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabPlanMessageInfo bean = BeanUtil.toBean(data,LabPlanMessageInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("plan_message_id",data.getString("planMessageId")).remove(LabPlanMessageInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabPlanMessageInfo bean = BeanUtil.toBean(data,LabPlanMessageInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("plan_message_id",data.getString("planMessageId")).one(LabPlanMessageInfo.class);
          res.put("data",bean);
          return res;
      }

}