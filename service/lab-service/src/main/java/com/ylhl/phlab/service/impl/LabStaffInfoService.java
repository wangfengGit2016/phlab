package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.domain.LabSiteInfo;
import com.ylhl.phlab.domain.LabStaffInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import com.ylhl.phlab.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service("LabStaffInfoService")
public class LabStaffInfoService implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabStaffInfo> page = new Page<>(data);
          CoreBuilder.select().page(page, LabStaffInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabStaffInfo> list=CoreBuilder.select().list(LabStaffInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabStaffInfo bean = BeanUtil.toBean(data,LabStaffInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("staff_id",data.getString("staffId")).remove(LabStaffInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabStaffInfo bean = BeanUtil.toBean(data,LabStaffInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("staff_id",data.getString("staffId")).one(LabStaffInfo.class);
          res.put("data",bean);
          return res;
      }

}