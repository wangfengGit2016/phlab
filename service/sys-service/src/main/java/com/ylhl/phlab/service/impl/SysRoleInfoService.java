package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.domain.SysRoleMenuRel;
import com.ylhl.phlab.domain.SysUserRoleRel;
import com.ylhl.phlab.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysRoleInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysRoleInfoService")
public class SysRoleInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysRoleInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,SysRoleInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          res.put("list", CoreBuilder.select().listJson(SysRoleInfo.class));
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysRoleInfo bean = BeanUtil.toBean(data,SysRoleInfo.class);
          if(StringUtils.isBlank(bean.getParentId())){
              bean.setParentId(DictConstants.ROOT_NODE);
          }
          SysRoleInfo info=CoreBuilder.select().eq("role_code",bean.getRoleCode()).oneT(SysRoleInfo.class);
          AssertUtil.isTrue(info!=null,"角色编码已存在");
          bean.setId(IdUtil.fastSimpleUUID());
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("id",data.getString("id")).remove(SysRoleInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysRoleInfo bean = BeanUtil.toBean(data,SysRoleInfo.class);
          if(StringUtils.isBlank(bean.getParentId())){
              bean.setParentId(DictConstants.ROOT_NODE);
          }
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject bean= CoreBuilder.select().eq("id",data.getString("id")).one(SysRoleInfo.class);
          List<SysRoleMenuRel> list=CoreBuilder.select().eq("role_id",data.getString("id")).list(SysRoleMenuRel.class);
          List<String> menus = new ArrayList<>();
          list.forEach(item->menus.add(item.getMenuId()));
          bean.put("menus",menus);
          return bean;
      }

    public JSONObject batchAssign(String userId,List<String> roles) {
        JSONObject res =new JSONObject();
        CoreBuilder.delete().eq("user_id",userId).remove(SysUserRoleRel.class);
        List<JSONObject> list = new ArrayList<>();
        roles.forEach(item->{
            if(StringUtils.isNotBlank(item)){
                SysUserRoleRel bean = new SysUserRoleRel();
                bean.setUserId(userId);
                bean.setRoleId(item);
                list.add((JSONObject) JSONObject.toJSON(bean));
            }
        });
        res.put("status",CoreBuilder.insert().saveBatch(list,new SysUserRoleRel()));
        return res;
    }

}