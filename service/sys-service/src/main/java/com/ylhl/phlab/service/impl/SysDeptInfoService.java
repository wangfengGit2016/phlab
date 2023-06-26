package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysDeptInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysDeptInfoService")
public class SysDeptInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysDeptInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,SysDeptInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysDeptInfo> list=CoreBuilder.select().list(SysDeptInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysDeptInfo bean = BeanUtil.toBean(data,SysDeptInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("id",data.getString("id")).remove(SysDeptInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysDeptInfo bean = BeanUtil.toBean(data,SysDeptInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("id",data.getString("id")).one(SysDeptInfo.class);
          res.put("data",bean);
          return res;
      }

}