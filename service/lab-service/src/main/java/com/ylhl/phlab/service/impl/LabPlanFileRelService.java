package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabPlanFileRel;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabPlanFileRelService")
public class LabPlanFileRelService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabPlanFileRel> page = new Page<>(data);
          CoreBuilder.select().page(page,LabPlanFileRel.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabPlanFileRel> list=CoreBuilder.select().list(LabPlanFileRel.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabPlanFileRel bean = BeanUtil.toBean(data,LabPlanFileRel.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().remove(LabPlanFileRel.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabPlanFileRel bean = BeanUtil.toBean(data,LabPlanFileRel.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().one(LabPlanFileRel.class);
          res.put("data",bean);
          return res;
      }

}