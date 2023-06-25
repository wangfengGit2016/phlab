package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabCortrolPlanSummary;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabCortrolPlanSummaryService")
public class LabCortrolPlanSummaryService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabCortrolPlanSummary> page = new Page<>(data);
          CoreBuilder.select().page(page,LabCortrolPlanSummary.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabCortrolPlanSummary> list=CoreBuilder.select().list(LabCortrolPlanSummary.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabCortrolPlanSummary bean = BeanUtil.toBean(data,LabCortrolPlanSummary.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("cortrol_plan_summary_id",data.getString("cortrolPlanSummaryId")).remove(LabCortrolPlanSummary.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabCortrolPlanSummary bean = BeanUtil.toBean(data,LabCortrolPlanSummary.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("cortrol_plan_summary_id",data.getString("cortrolPlanSummaryId")).one(LabCortrolPlanSummary.class);
          res.put("data",bean);
          return res;
      }

}