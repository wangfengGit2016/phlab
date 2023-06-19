package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysUserRoleRel;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysUserRoleRelService")
public class SysUserRoleRelService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysUserRoleRel> page = new Page<>(data);
          CoreBuilder.select().page(page,SysUserRoleRel.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysUserRoleRel> list=CoreBuilder.select().list(SysUserRoleRel.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysUserRoleRel bean = BeanUtil.toBean(data,SysUserRoleRel.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("role_id",data.getString("roleId")).eq("user_id",data.getString("userId")).remove(SysUserRoleRel.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysUserRoleRel bean = BeanUtil.toBean(data,SysUserRoleRel.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          return CoreBuilder.select().eq("role_id",data.getString("roleId")).eq("user_id",data.getString("userId")).one(SysUserRoleRel.class);
      }

}