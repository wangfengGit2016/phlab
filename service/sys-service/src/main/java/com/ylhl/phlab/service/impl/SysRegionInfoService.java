package com.ylhl.phlab.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;

import java.util.List;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.DictConstants;
import com.ylhl.phlab.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.SysRegionInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("SysRegionInfoService")
public class SysRegionInfoService implements IService {

    public JSONObject page(JSONObject data) {
        log.info("{}", data);
        Page<SysRegionInfo> page = new Page<>(data);
        CoreBuilder.select().page(page, SysRegionInfo.class);
        return page.toJson();
    }

    public JSONObject list(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        res.put("list", CoreBuilder.select().listJson(SysRegionInfo.class));
        return res;
    }

    public JSONObject insert(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        SysRegionInfo bean = BeanUtil.toBean(data, SysRegionInfo.class);
        if (StringUtils.isBlank(bean.getParentId())) {
            bean.setParentId(DictConstants.ROOT_NODE);
        }
        List<SysRegionInfo> list = CoreBuilder.select()
                .eq("parent_id", bean.getParentId())
                .eq("region_code", bean.getRegionCode()).list(SysRegionInfo.class);
        AssertUtil.isTrue(list != null && list.size() > 0, "同一地区下编码不能重复");
        bean.setId(IdUtil.fastSimpleUUID());

        CoreBuilder.insert().save(bean);
        res.put("status", true);
        res.put("id", bean.getId());
        return res;
    }

    public JSONObject delete(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        CoreBuilder.delete().eq("id", data.getString("id")).remove(SysRegionInfo.class);
        res.put("status", true);
        return res;
    }

    public JSONObject update(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        SysRegionInfo bean = BeanUtil.toBean(data, SysRegionInfo.class);
        if (StringUtils.isBlank(bean.getParentId())) {
            bean.setParentId(DictConstants.ROOT_NODE);
        }
        CoreBuilder.update().edit(bean);
        res.put("status", true);
        return res;
    }

    public JSONObject detail(JSONObject data) {
        log.info("{}", data);
        return CoreBuilder.select().eq("id", data.getString("id")).one(SysRegionInfo.class);
    }

    public List<String> getChildNodes() {
        String regionId = StpUtil.getSession().getString("regionId");
        List<String> nodes = CoreBuilder.select().select("id").eq("parent_id", regionId).listString(SysRegionInfo.class);
        nodes.add(regionId);
        return nodes;
    }
}