package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysDictInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysDictInfoService")
public class SysDictInfoService  implements IService{

      public JSONObject page(JSONObject data) {
          log.info("{}",data);
          Page<SysDictInfo> page = new Page<>(data);
          if(StringUtils.isBlank(data.getString("parentId"))){
              data.put("parentId", DictConstants.ROOT_NODE);
          }
          CoreBuilder.select()
                  .eq("parent_id",data.getString("parentId"))
                  .page(page,SysDictInfo.class);
          return page.toJson();
      }

      public JSONObject list(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          List<SysDictInfo> list=CoreBuilder.select()
                  .eq(StringUtils.isNotBlank(data.getString("dictCode")),"dict_code",data.getString("dictCode"))
                  .eq(StringUtils.isNotBlank(data.getString("parentId")),"parent_id",data.getString("parentId"))
                  .list(SysDictInfo.class);
          res.put("list", list);
          return res;
      }

      public JSONObject insert(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysDictInfo bean = BeanUtil.toBean(data,SysDictInfo.class);
          List<SysDictInfo> list=CoreBuilder.select().eq("parent_id",bean.getParentId())
                  .eq("value",bean.getValue()).list(SysDictInfo.class);
          AssertUtil.isTrue(list!=null&&list.size()>0,"字典已存在");
          bean.setId(IdUtil.fastSimpleUUID());
          res.put("status",CoreBuilder.insert().save(bean));
          res.put("id",bean.getId());
          return res;
      }

      public JSONObject delete(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          CoreBuilder.delete().eq("id",data.getString("id")).remove(SysDictInfo.class);
          return res;
      }

      public JSONObject update(JSONObject data) {
          log.info("{}",data);
          JSONObject res =new JSONObject();
          SysDictInfo bean = BeanUtil.toBean(data,SysDictInfo.class);
          bean.setDictCode(null);
          CoreBuilder.update().edit(bean);
          return res;
      }

      public JSONObject detail(JSONObject data) {
          log.info("{}",data);
          return CoreBuilder.select().eq("id",data.getString("id")).one(SysDictInfo.class);
      }

    public JSONObject itemList(JSONObject data) {
        log.info("{}",data);
        JSONObject res =new JSONObject();
        if(StringUtils.isBlank(data.getString("parentId"))){
            SysDictInfo dictInfo= CoreBuilder.select().eq("parent_id","-1").eq("dict_code",data.getString("dictCode")).oneT(SysDictInfo.class);
            data.put("parentId",dictInfo.getId());
        }
        List<SysDictInfo> list=CoreBuilder.select()
                .eq(StringUtils.isNotBlank(data.getString("parentId")),"parent_id",data.getString("parentId"))
                .list(SysDictInfo.class);
        res.put("list", list);
        return res;
      }

    public SysDictInfo detailByCode(String code) {
        log.info("{}",code);
        SysDictInfo sysDictInfo = CoreBuilder.select().eq("dict_code", code).oneT(SysDictInfo.class);
        return sysDictInfo;
    }

    public SysDictInfo detailByParent(String id,String type) {
        log.info("{}",id);
        SysDictInfo sysDictInfo = CoreBuilder.select().eq("parent_id", id).eq("type",type).oneT(SysDictInfo.class);
        return sysDictInfo;
    }
}