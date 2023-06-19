package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysMenuInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysMenuInfoService")
public class SysMenuInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysMenuInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,SysMenuInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysMenuInfo> list=CoreBuilder.select().asc("sort").list(SysMenuInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysMenuInfo bean = BeanUtil.toBean(data,SysMenuInfo.class);
          SysMenuInfo info=CoreBuilder.select()
                  .eq("parent_id",bean.getParentId())
                  .eq("title",bean.getTitle()).oneT(SysMenuInfo.class);
          AssertUtil.isTrue(info!=null,"同级菜单名不能重复");
          bean.setId(IdUtil.fastSimpleUUID());
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("id",data.getString("id")).remove(SysMenuInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysMenuInfo bean = BeanUtil.toBean(data,SysMenuInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          return CoreBuilder.select().eq("id",data.getString("id")).one(SysMenuInfo.class);
      }

}