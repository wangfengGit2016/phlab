package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraCourseRecord;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraCourseRecordService")
public class TraCourseRecordService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<TraCourseRecord> page = new Page<>(data);
          CoreBuilder.select().page(page,TraCourseRecord.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<TraCourseRecord> list=CoreBuilder.select().list(TraCourseRecord.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraCourseRecord bean = BeanUtil.toBean(data,TraCourseRecord.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("course_record_id",data.getString("courseRecordId")).remove(TraCourseRecord.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraCourseRecord bean = BeanUtil.toBean(data,TraCourseRecord.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("course_record_id",data.getString("courseRecordId")).one(TraCourseRecord.class);
          res.put("data",bean);
          return res;
      }

}