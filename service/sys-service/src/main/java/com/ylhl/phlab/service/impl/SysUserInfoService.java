package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.domain.*;
import com.ylhl.phlab.utils.AssertUtil;
import com.ylhl.phlab.utils.NodesUtils;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysUserInfoService")
public class SysUserInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysUserInfo> page = new Page<>(data);
          //获取父区域 获取父角色
          List<String> roles = new ArrayList<>();
          if(StringUtils.isNotBlank(data.getString("roleId"))){
              SysRoleInfo  roleInfo=CoreBuilder.select().oneT(SysRoleInfo.class);
              if(roleInfo!=null&&!DictConstants.ROOT_NODE.equals(roleInfo.getParentId())){
                  List<JSONObject>  roleInfos=CoreBuilder.select().listJson(SysRoleInfo.class);
                  List<JSONObject> all = new ArrayList<>();
                  NodesUtils.toList(roleInfos,data.getString("roleId"),all);
                  all.forEach(item->roles.add(item.getString("id")));
                  roles.add(data.getString("roleId"));
              }
          }
          List<String> regions = new ArrayList<>();
          if(StringUtils.isNotBlank(data.getString("regionId"))){
              SysRegionInfo  regionInfo=CoreBuilder.select().eq("id",data.getString("regionId")).oneT(SysRegionInfo.class);
              if(regionInfo!=null&&!DictConstants.ROOT_NODE.equals(regionInfo.getParentId())){
                  List<JSONObject> regionInfos=CoreBuilder.select().listJson(SysRegionInfo.class);
                  List<JSONObject> all = new ArrayList<>();
                  NodesUtils.toList(regionInfos,data.getString("regionId"),all);
                  all.forEach(item->regions.add(item.getString("id")));
                  regions.add(data.getString("regionId"));
              }
          }
          CoreBuilder.select().select("sui.*,sri.full_name fullName,ri.id roleId,ri.role_name roleName").as("sui")
                  .inner(SysOrganInfo.class,"sri","sri.id=sui.region_id")
                  .inner(SysUserRoleRel.class,"urr","sui.id=urr.user_id")
                  .inner(SysRoleInfo.class,"ri","ri.id=urr.role_id")
                  .in(roles.size()>0,"urr.role_id",roles)
                  .in(regions.size()>0,"sui.region_id",regions)
                  .like(StringUtils.isNotBlank(data.getString("userName")),"user_name", data.getString("userName"))
                  .like(StringUtils.isNotBlank(data.getString("realName")),"real_name", data.getString("realName"))
                  .like(StringUtils.isNotBlank(data.getString("phone")),"phone", data.getString("phone"))
                  .like(StringUtils.isNotBlank(data.getString("userStatus")),"user_status", data.getString("userStatus"))
                  .between(StringUtils.isNotBlank(data.getString("startTime"))&&StringUtils.isNotBlank(data.getString("endTime"))
                          ,"sui.create_time",data.getDate("startTime"),data.getDate("endTime"))
                  .eq(StringUtils.isNotBlank(data.getString("organId")),"organ_id", data.getString("organId"))
                  .or()
                  .eq(StringUtils.isNotBlank(data.getString("organId")),"region_id", data.getString("organId"))
                  .and()
                  .desc("sui.create_time")
                  .page(page,SysUserInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysUserInfo> list=CoreBuilder.select().list(SysUserInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysUserInfo bean = BeanUtil.toBean(data,SysUserInfo.class);
          SysUserInfo userInfo=CoreBuilder.select().eq("user_name",bean.getUserName()).oneT(SysUserInfo.class);
          AssertUtil.isTrue(userInfo!=null,"用户名已存在");
          bean.setId(IdUtil.fastSimpleUUID());
          res.put("status",CoreBuilder.insert().save(bean));
          res.put("id",bean.getId());
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysUserInfo bean = BeanUtil.toBean(data,SysUserInfo.class);
          CoreBuilder.delete().eq("id",bean.getId()).remove(SysUserInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysUserInfo bean = BeanUtil.toBean(data,SysUserInfo.class);
          AssertUtil.isTrue(StringUtils.isBlank(bean.getId()),"用户id不存在");
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject bean= CoreBuilder.select().eq("id",data.getString("id")).one(SysUserInfo.class);
          List<SysRoleInfo> roles=CoreBuilder.select().as("ri")
                  .inner(SysUserRoleRel.class,"urr","urr.role_id=ri.id")
                  .eq("urr.user_id",data.getString("id")).list(SysRoleInfo.class);
          roles.forEach(item-> bean.put("roleCode",item.getRoleCode()));
          List<String> menus=CoreBuilder.select().as("mi")
                  .select("mi.permission")
                  .inner(SysRoleMenuRel.class,"rmr","rmr.menu_id=mi.id")
                  .inner(SysRoleInfo.class,"ri","rmr.role_id=ri.id")
                  .inner(SysUserRoleRel.class,"urr","urr.role_id=ri.id")
                  .eq("urr.user_id",data.getString("id"))
                  .listString(SysMenuInfo.class);
          List<String> menus1=CoreBuilder.select().as("mi")
                  .select("mi.permission")
                  .inner(SysMenuInfo.class,"mi1","mi1.parent_id = mi.id")
                  .inner(SysRoleMenuRel.class,"rmr","rmr.menu_id=mi1.id")
                  .inner(SysRoleInfo.class,"ri","rmr.role_id=ri.id")
                  .inner(SysUserRoleRel.class,"urr","urr.role_id=ri.id")
                  .eq("urr.user_id",data.getString("id"))
                  .listString(SysMenuInfo.class);
          menus.addAll(menus1);
          bean.put("permission",new ArrayList<>(new LinkedHashSet<>(menus)));
          List<String> buttonsAuth=CoreBuilder.select().as("mi")
                  .select("mi.permission")
                  .inner(SysRoleMenuRel.class,"rmr","rmr.menu_id=mi.id")
                  .inner(SysRoleInfo.class,"ri","rmr.role_id=ri.id")
                  .inner(SysUserRoleRel.class,"urr","urr.role_id=ri.id")
                  .eq("urr.user_id",data.getString("id"))
                  .eq("mi.type","2")
                  .listString(SysMenuInfo.class);
          bean.put("buttonsAuth",buttonsAuth);
          SysRegionInfo regionInfo=CoreBuilder.select().eq("id",bean.getString("regionId")).oneT(SysRegionInfo.class);
          if(regionInfo!=null){
              bean.put("regionName",regionInfo.getRegionName());
          }

          bean.remove("password");
          return bean;
      }

    public SysUserInfo info(JSONObject data) {

        return CoreBuilder.select().eq("user_name",data.getString("userName"))
                .oneT(SysUserInfo.class);
    }

}