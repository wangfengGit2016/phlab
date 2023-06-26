package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraPaperQuestionRel;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraPaperQuestionRelService")
public class TraPaperQuestionRelService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<TraPaperQuestionRel> page = new Page<>(data);
          CoreBuilder.select().page(page,TraPaperQuestionRel.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<TraPaperQuestionRel> list=CoreBuilder.select().list(TraPaperQuestionRel.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraPaperQuestionRel bean = BeanUtil.toBean(data,TraPaperQuestionRel.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("id",data.getString("id")).remove(TraPaperQuestionRel.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraPaperQuestionRel bean = BeanUtil.toBean(data,TraPaperQuestionRel.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("id",data.getString("id")).one(TraPaperQuestionRel.class);
          res.put("data",bean);
          return res;
      }

}