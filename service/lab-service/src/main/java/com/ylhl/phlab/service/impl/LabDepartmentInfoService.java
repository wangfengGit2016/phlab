package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabDepartmentInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabDepartmentInfoService")
public class LabDepartmentInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabDepartmentInfo> page = new Page<>(data);
          CoreBuilder.select()
                  .eq(StringUtils.isNotBlank(data.getString("deptName")), "dept_name", data.getString("deptName"))
                  .eq(StringUtils.isNotBlank(data.getString("siteId")), "site_id", data.getString("siteId"))
                  .eq(ObjectUtil.isNotNull(data.get("deptType")), "dept_type", data.getString("deptType"))
                  .between(ObjectUtil.isNotNull(data.get("startTime")), "create_time", data.getDate("startTime"), data.getDate("endTime"))
                  .desc("create_time")
                  .page(page,LabDepartmentInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabDepartmentInfo> list=CoreBuilder.select()
                  .eq(StringUtils.isNotBlank(data.getString("siteId")), "site_id", data.getString("siteId"))
                  .list(LabDepartmentInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabDepartmentInfo bean = BeanUtil.toBean(data,LabDepartmentInfo.class);
          bean.setDeptId(IdUtil.fastSimpleUUID());
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("dept_id",data.getString("deptId")).remove(LabDepartmentInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabDepartmentInfo bean = BeanUtil.toBean(data,LabDepartmentInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("dept_id",data.getString("deptId")).one(LabDepartmentInfo.class);
          res.put("data",bean);
          return res;
      }

    public JSONObject tree(JSONObject data) {
        log.info("{}",data);
        JSONObject res =new JSONObject();
        JSONObject bean= CoreBuilder.select().eq("dept_id",data.getString("deptId")).one(LabDepartmentInfo.class);
        res.put("data",bean);
        return res;
    }
}