package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysRoleMenuRel;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysRoleMenuRelService")
public class SysRoleMenuRelService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysRoleMenuRel> page = new Page<>(data);
          CoreBuilder.select().page(page,SysRoleMenuRel.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysRoleMenuRel> list=CoreBuilder.select().list(SysRoleMenuRel.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysRoleMenuRel bean = BeanUtil.toBean(data,SysRoleMenuRel.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

    public JSONObject batchAssign(String id,List<String> menus) {
        JSONObject res =new JSONObject();
        CoreBuilder.delete().eq("role_id",id).remove(SysRoleMenuRel.class);
        List<JSONObject> list = new ArrayList<>();
        menus.forEach(item->{
            if(StringUtils.isNotBlank(item)){
                SysRoleMenuRel bean = new SysRoleMenuRel();
                bean.setRoleId(id);
                bean.setMenuId(item);
                list.add((JSONObject) JSONObject.toJSON(bean));
            }
        });
        res.put("status",CoreBuilder.insert().saveBatch(list,new SysRoleMenuRel()));
        return res;
    }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("role_id",data.getString("roleId"))
                  .eq("menu_id",data.getString("menuId")).remove(SysRoleMenuRel.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysRoleMenuRel bean = BeanUtil.toBean(data,SysRoleMenuRel.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          return CoreBuilder.select().eq("role_id",data.getString("roleId")).eq("menu_id",data.getString("menuId")).one(SysRoleMenuRel.class);
      }

}