package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysSmsInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysSmsInfoService")
public class SysSmsInfoService implements IService {

    public JSONObject page(JSONObject data) {
        log.info("{}", data);
        Page<SysSmsInfo> page = new Page<>(data);
        CoreBuilder.select().page(page, SysSmsInfo.class);
        return page.toJson();
    }

    public JSONObject list(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        List<SysSmsInfo> list = CoreBuilder.select().list(SysSmsInfo.class);
        res.put("list", list);
        return res;
    }

    public JSONObject insert(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        SysSmsInfo bean = BeanUtil.toBean(data, SysSmsInfo.class);
        res.put("status", CoreBuilder.insert().save(bean));
        return res;
    }

    public JSONObject delete(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        CoreBuilder.delete().eq("id", data.getString("id")).remove(SysSmsInfo.class);
        return res;
    }

    public JSONObject update(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        SysSmsInfo bean = BeanUtil.toBean(data, SysSmsInfo.class);
        CoreBuilder.update().edit(bean);
        return res;
    }

    public JSONObject detail(JSONObject data) {
        log.info("{}", data);
        return CoreBuilder.select().eq("id", data.getString("id")).one(SysSmsInfo.class);
    }

    public void submitBatch(List<JSONObject> list) {
        CoreBuilder.insert().saveBatch(list, new SysSmsInfo());
    }
}