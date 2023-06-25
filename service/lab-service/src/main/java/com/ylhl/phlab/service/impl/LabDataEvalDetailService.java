package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabDataEvalDetail;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabDataEvalDetailService")
public class LabDataEvalDetailService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabDataEvalDetail> page = new Page<>(data);
          CoreBuilder.select().page(page,LabDataEvalDetail.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabDataEvalDetail> list=CoreBuilder.select().list(LabDataEvalDetail.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabDataEvalDetail bean = BeanUtil.toBean(data,LabDataEvalDetail.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("data_eval_id",data.getString("dataEvalId")).remove(LabDataEvalDetail.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabDataEvalDetail bean = BeanUtil.toBean(data,LabDataEvalDetail.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("data_eval_id",data.getString("dataEvalId")).one(LabDataEvalDetail.class);
          res.put("data",bean);
          return res;
      }

}