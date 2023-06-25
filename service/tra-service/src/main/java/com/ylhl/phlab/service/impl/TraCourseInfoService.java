package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraCourseInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraCourseInfoService")
public class TraCourseInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<TraCourseInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,TraCourseInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<TraCourseInfo> list=CoreBuilder.select().list(TraCourseInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraCourseInfo bean = BeanUtil.toBean(data,TraCourseInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("course_id",data.getString("courseId")).remove(TraCourseInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraCourseInfo bean = BeanUtil.toBean(data,TraCourseInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("course_id",data.getString("courseId")).one(TraCourseInfo.class);
          res.put("data",bean);
          return res;
      }

}