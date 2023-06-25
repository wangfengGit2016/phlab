package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.LabApprovalProcessNodeInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabApprovalProcessNodeInfoService")
public class LabApprovalProcessNodeInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<LabApprovalProcessNodeInfo> page = new Page<>(data);
          CoreBuilder.select().page(page,LabApprovalProcessNodeInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<LabApprovalProcessNodeInfo> list=CoreBuilder.select().list(LabApprovalProcessNodeInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabApprovalProcessNodeInfo bean = BeanUtil.toBean(data,LabApprovalProcessNodeInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("node_id",data.getString("nodeId")).remove(LabApprovalProcessNodeInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          LabApprovalProcessNodeInfo bean = BeanUtil.toBean(data,LabApprovalProcessNodeInfo.class);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          JSONObject bean= CoreBuilder.select().eq("node_id",data.getString("nodeId")).one(LabApprovalProcessNodeInfo.class);
          res.put("data",bean);
          return res;
      }

}