package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysOrganInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysOrganInfoService")
public class SysOrganInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysOrganInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,SysOrganInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysOrganInfo> list=CoreBuilder.select().list(SysOrganInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          String id = IdUtil.objectId();
          SysOrganInfo bean = BeanUtil.toBean(data,SysOrganInfo.class);
          bean.setId(id);
          res.put("status",CoreBuilder.insert().save(bean));
          res.put("id",bean.getId());
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("id",data.getString("id")).remove(SysOrganInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysOrganInfo bean = BeanUtil.toBean(data,SysOrganInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("id",data.getString("id")).one(SysOrganInfo.class);
          res.put("data",bean);
          return res;
      }

}