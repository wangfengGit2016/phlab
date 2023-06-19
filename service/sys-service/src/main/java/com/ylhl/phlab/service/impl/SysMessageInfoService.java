package com.ylhl.phlab.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.domain.SysRegionInfo;
import com.ylhl.phlab.domain.SysUserMessageRel;
import com.ylhl.phlab.enums.MessageEnum;
import com.ylhl.phlab.service.BatchService;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysMessageInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysMessageInfoService")
public class SysMessageInfoService  implements IService, BatchService {


      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysMessageInfo> page = new Page<>(data);
          CoreBuilder.select()
                  .eq(StringUtils.isNotBlank(data.getString("type")),"type",data.getString("type"))
                  .page(page,SysMessageInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysMessageInfo> list=CoreBuilder.select().limit(100).list(SysMessageInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysMessageInfo bean = BeanUtil.toBean(data,SysMessageInfo.class);
          res.put("status",CoreBuilder.insert().save(bean));
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("id",data.getString("id")).remove(SysMessageInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysMessageInfo bean = BeanUtil.toBean(data,SysMessageInfo.class);
          CoreBuilder.update().as("s1")
                  .inner(SysMessageInfo.class,"s2","s2.id=s1.id")
                  .set(bean)
                  .edit(SysMessageInfo.class);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          return CoreBuilder.select().eq("id",data.getString("id")).one(SysMessageInfo.class);
      }

      public void batch(String id,List<JSONObject> list) {
          log.info("{}",list);
          list.forEach(item->{
            item.put("id", IdUtil.fastSimpleUUID());
              item.putIfAbsent("status","1");
          });
          CoreBuilder.insert().saveBatch(list,new SysMessageInfo());
     }

    public void submitBatch(List<JSONObject> list){
        CoreBuilder.insert().saveBatch(list,new SysMessageInfo());
    }

    public void submit(MessageEnum messageEnum,String businessId,String title,String content,String regionId){
        SysMessageInfo messageInfo=new SysMessageInfo();
        String id =IdUtil.fastSimpleUUID();
        messageInfo.setId(id);
        messageInfo.setBusinessId(businessId);
        messageInfo.setRegionId(regionId);
        messageInfo.setTitle(title);
        messageInfo.setContent(content);
        messageInfo.setType(messageEnum.getType());
        CoreBuilder.insert().save(messageInfo);
    }

    public JSONObject data(JSONObject data) {
        log.info("{}",data);
        JSONObject res =new JSONObject();
        List<SysMessageInfo> list=CoreBuilder.select().as("smi")
                .select("smi.*,smr.is_read isRead")
                .inner(SysUserMessageRel.class,"smr","smr.message_id = smi.id")
                .in("smi.type",MessageEnum.CRC_ORGAN.getType(),MessageEnum.CRC_OPERATE.getType(),MessageEnum.CRC_FUND.getType())
                .eq(StringUtils.isNotBlank(data.getString("userId")),"smr.user_id",data.getString("userId"))
                .limit(100)
                .list(SysMessageInfo.class);
        res.put(MessageEnum.CRC_ORGAN.getCode(), list);
        List<SysMessageInfo> list1=CoreBuilder.select().as("smi")
                .select("smi.*,smr.is_read isRead")
                .inner(SysUserMessageRel.class,"smr","smr.message_id = smi.id")
                .in("smi.type",MessageEnum.CFR_EXCEPTION.getType())
                .eq(StringUtils.isNotBlank(data.getString("userId")),"smr.user_id",data.getString("userId"))
                .limit(100)
                .list(SysMessageInfo.class);
        res.put(MessageEnum.CFR_EXCEPTION.getCode(), list1);
        List<SysMessageInfo> list2=CoreBuilder.select().as("smi")
                .select("smi.*,smr.is_read isRead")
                .inner(SysUserMessageRel.class,"smr","smr.message_id = smi.id")
                .in("smi.type",MessageEnum.EWS_WARN.getType())
                .eq(StringUtils.isNotBlank(data.getString("userId")),"smr.user_id",data.getString("userId"))
                .limit(100)
                .list(SysMessageInfo.class);
        res.put(MessageEnum.EWS_WARN.getCode(), list2);
        long i1=list.stream().filter(item->"0".equals(item.getIsRead())).count();
        long i2=list1.stream().filter(item->"0".equals(item.getIsRead())).count();
        long i3=list2.stream().filter(item->"0".equals(item.getIsRead())).count();
        res.put("status", i1>0||i2>0||i3>0);
        res.put("EWS_NUM", i3);
        return res;
    }

    public JSONObject readAll(JSONObject data) {
        log.info("{}",data);
        JSONObject res =new JSONObject();
        CoreBuilder.update()
                .set("is_read","1")
                .eq("code",data.getString("type"))
                .eq("user_id",data.getString("userId"))
                .edit(SysUserMessageRel.class);
        return res;
    }

    public JSONObject readOne(JSONObject data) {
        log.info("{}",data);
        JSONObject res =new JSONObject();
        CoreBuilder.update()
                .set("is_read","1")
                .eq("message_id",data.getString("id"))
                .eq("user_id",data.getString("userId"))
                .edit(SysMessageInfo.class);
        return res;
    }

    public JSONObject readFirst(JSONObject data) {
        log.info("{}",data);
        JSONObject res =new JSONObject();
        res.put("isNew",false);
        SysRegionInfo regionInfo = CoreBuilder.select().eq("id", data.getString("regionId")).oneT(SysRegionInfo.class);
        List<String> regions = new ArrayList<>();
        if(!DictConstants.ROOT_NODE.equals(regionInfo.getParentId())){
            List<String> strings =CoreBuilder.select().select("id")
                    .eq("parent_id", StpUtil.getSession().getString("regionId"))
                    .listString(SysRegionInfo.class);
            regions.addAll(strings);
            regions.add(data.getString("regionId"));
        }
        SysMessageInfo messageInfo=CoreBuilder.select()
                .eq("type",MessageEnum.EWS_WARN.getType())
                .in(regions.size()>0,"region_id",regions)
                .desc("create_time")
                .limit(1)
                .oneT(SysMessageInfo.class);
        if(messageInfo==null){
            return res;
        }
        SysUserMessageRel rel=CoreBuilder.select()
                .eq("message_id",messageInfo.getId())
                .eq("user_id",data.getString("userId"))
                .oneT(SysUserMessageRel.class);
        if(rel!=null){
            return res;
        }
        res.put("isNew",true);
        res.put("id",messageInfo.getId());
        res.put("title",messageInfo.getTitle());
        rel = new SysUserMessageRel();
        rel.setUserId(data.getString("userId"));
        rel.setMessageId(messageInfo.getId());
        rel.setCode(MessageEnum.EWS_WARN.getType());
        CoreBuilder.insert().save(rel);
        return res;
    }
}