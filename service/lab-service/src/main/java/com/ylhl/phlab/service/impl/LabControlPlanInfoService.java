package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabControlPlanInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabControlPlanInfoService")
public class LabControlPlanInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabControlPlanInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,LabControlPlanInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabControlPlanInfo> list=CoreBuilder.select().list(LabControlPlanInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabControlPlanInfo bean = BeanUtil.toBean(data,LabControlPlanInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("control_plan_id",data.getString("controlPlanId")).remove(LabControlPlanInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabControlPlanInfo bean = BeanUtil.toBean(data,LabControlPlanInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("control_plan_id",data.getString("controlPlanId")).one(LabControlPlanInfo.class);
          res.put("data",bean);
          return res;
      }

}