package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraQuestionInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraQuestionInfoService")
public class TraQuestionInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<TraQuestionInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,TraQuestionInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<TraQuestionInfo> list=CoreBuilder.select().list(TraQuestionInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraQuestionInfo bean = BeanUtil.toBean(data,TraQuestionInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("question_id",data.getString("questionId")).remove(TraQuestionInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraQuestionInfo bean = BeanUtil.toBean(data,TraQuestionInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("question_id",data.getString("questionId")).one(TraQuestionInfo.class);
          res.put("data",bean);
          return res;
      }

}