package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraQuestionBak;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraQuestionBakService")
public class TraQuestionBakService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<TraQuestionBak> page = new Page<>(data);
          CoreBuilder.select().page(page,TraQuestionBak.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<TraQuestionBak> list=CoreBuilder.select().list(TraQuestionBak.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraQuestionBak bean = BeanUtil.toBean(data,TraQuestionBak.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("question_bak_id",data.getString("questionBakId")).remove(TraQuestionBak.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraQuestionBak bean = BeanUtil.toBean(data,TraQuestionBak.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("question_bak_id",data.getString("questionBakId")).one(TraQuestionBak.class);
          res.put("data",bean);
          return res;
      }

}