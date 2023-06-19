package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysLogInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysLogInfoService")
public class SysLogInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysLogInfo> page = new Page<>(data);
          CoreBuilder.select()
                  .eq(StringUtils.isNotBlank(data.getString("module")),"module", data.getString("module"))
                  .eq(StringUtils.isNotBlank(data.getString("userId")),"user_id", data.getString("userId"))
                  .eq(StringUtils.isNotBlank(data.getString("userName")),"user_name", data.getString("userName"))
                  .between(StringUtils.isNotBlank(data.getString("startTime"))&&StringUtils.isNotBlank(data.getString("endTime")),"create_time",data.getDate("startTime"),data.getDate("endTime"))
                  .desc("create_time")
                  .page(page,SysLogInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysLogInfo> list=CoreBuilder.select().list(SysLogInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysLogInfo bean = BeanUtil.toBean(data,SysLogInfo.class);
          bean.setId(IdUtil.fastSimpleUUID());
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("id",data.getString("id")).remove(SysLogInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysLogInfo bean = BeanUtil.toBean(data,SysLogInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          return CoreBuilder.select().eq("id",data.getString("id")).one(SysLogInfo.class);
      }

}