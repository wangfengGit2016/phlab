package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysStaffInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysStaffInfoService")
public class SysStaffInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysStaffInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,SysStaffInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysStaffInfo> list=CoreBuilder.select().list(SysStaffInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysStaffInfo bean = BeanUtil.toBean(data,SysStaffInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("staff_id",data.getString("staffId")).remove(SysStaffInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysStaffInfo bean = BeanUtil.toBean(data,SysStaffInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("staff_id",data.getString("staffId")).one(SysStaffInfo.class);
          res.put("data",bean);
          return res;
      }

}