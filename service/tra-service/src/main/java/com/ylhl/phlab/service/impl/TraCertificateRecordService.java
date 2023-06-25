package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraCertificateRecord;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraCertificateRecordService")
public class TraCertificateRecordService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<TraCertificateRecord> page = new Page<>(data);
          CoreBuilder.select().page(page,TraCertificateRecord.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<TraCertificateRecord> list=CoreBuilder.select().list(TraCertificateRecord.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraCertificateRecord bean = BeanUtil.toBean(data,TraCertificateRecord.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("certificate_record_id",data.getString("certificateRecordId")).remove(TraCertificateRecord.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          TraCertificateRecord bean = BeanUtil.toBean(data,TraCertificateRecord.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("certificate_record_id",data.getString("certificateRecordId")).one(TraCertificateRecord.class);
          res.put("data",bean);
          return res;
      }

}