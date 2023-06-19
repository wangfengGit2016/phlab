package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysUserMessageRel;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysUserMessageRelService")
public class SysUserMessageRelService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysUserMessageRel> page = new Page<>(data);
          CoreBuilder.select().page(page,SysUserMessageRel.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysUserMessageRel> list=CoreBuilder.select().list(SysUserMessageRel.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysUserMessageRel bean = BeanUtil.toBean(data,SysUserMessageRel.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("user_id",data.getString("userId")).eq("message_id",data.getString("messageId")).remove(SysUserMessageRel.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysUserMessageRel bean = BeanUtil.toBean(data,SysUserMessageRel.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          return CoreBuilder.select().eq("user_id",data.getString("userId")).eq("message_id",data.getString("messageId")).one(SysUserMessageRel.class);
      }

}