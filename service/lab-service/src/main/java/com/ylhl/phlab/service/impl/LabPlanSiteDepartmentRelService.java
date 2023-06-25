package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabPlanSiteDepartmentRel;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabPlanSiteDepartmentRelService")
public class LabPlanSiteDepartmentRelService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabPlanSiteDepartmentRel> page = new Page<>(data);
          CoreBuilder.select().page(page,LabPlanSiteDepartmentRel.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabPlanSiteDepartmentRel> list=CoreBuilder.select().list(LabPlanSiteDepartmentRel.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabPlanSiteDepartmentRel bean = BeanUtil.toBean(data,LabPlanSiteDepartmentRel.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("plan_site_dept_id",data.getString("planSiteDeptId")).remove(LabPlanSiteDepartmentRel.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabPlanSiteDepartmentRel bean = BeanUtil.toBean(data,LabPlanSiteDepartmentRel.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("plan_site_dept_id",data.getString("planSiteDeptId")).one(LabPlanSiteDepartmentRel.class);
          res.put("data",bean);
          return res;
      }

}