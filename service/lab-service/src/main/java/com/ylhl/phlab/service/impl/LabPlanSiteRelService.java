package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabPlanSiteRel;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabPlanSiteRelService")
public class LabPlanSiteRelService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabPlanSiteRel> page = new Page<>(data);
          CoreBuilder.select().page(page,LabPlanSiteRel.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabPlanSiteRel> list=CoreBuilder.select().list(LabPlanSiteRel.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabPlanSiteRel bean = BeanUtil.toBean(data,LabPlanSiteRel.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("plan_site_id",data.getString("planSiteId")).remove(LabPlanSiteRel.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabPlanSiteRel bean = BeanUtil.toBean(data,LabPlanSiteRel.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("plan_site_id",data.getString("planSiteId")).one(LabPlanSiteRel.class);
          res.put("data",bean);
          return res;
      }

}