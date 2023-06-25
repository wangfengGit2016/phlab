package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraPaperAnswer;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraPaperAnswerService")
public class TraPaperAnswerService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<TraPaperAnswer> page = new Page<>(data);
          CoreBuilder.select().page(page,TraPaperAnswer.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<TraPaperAnswer> list=CoreBuilder.select().list(TraPaperAnswer.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraPaperAnswer bean = BeanUtil.toBean(data,TraPaperAnswer.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("paper_answer_id",data.getString("paperAnswerId")).remove(TraPaperAnswer.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraPaperAnswer bean = BeanUtil.toBean(data,TraPaperAnswer.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("paper_answer_id",data.getString("paperAnswerId")).one(TraPaperAnswer.class);
          res.put("data",bean);
          return res;
      }

}