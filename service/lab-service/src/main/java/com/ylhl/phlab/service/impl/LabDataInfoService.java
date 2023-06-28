package com.ylhl.phlab.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.domain.*;
import com.ylhl.phlab.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabDataInfoService")
public class LabDataInfoService implements IService {

    public JSONObject page(JSONObject data) {
        log.info("{}", data);
        Page<LabDataInfo> page = new Page<>(data);
        CoreBuilder.select()
                .like(StringUtils.isNotBlank(data.getString("docNumber")), "doc_number", data.getString("docNumber"))
                .eq(StringUtils.isNotBlank(data.getString("siteId")), "site_id", data.getString("siteId"))
                .like(StringUtils.isNotBlank(data.getString("uploadUserName")), "upload_user_name", data.getString("uploadUserName"))
                .like(StringUtils.isNotBlank(data.getString("uploadPhone")), "upload_phone", data.getString("uploadPhone"))
                .eq(StringUtils.isNotBlank(data.getString("evalStatus")), "eval_status", data.getString("evalStatus"))
                .between(ObjectUtil.isNotNull(data.get("startTime")), "create_time", data.getDate("startTime"), data.getDate("endTime"))
                .desc("create_time")
                .page(page, LabDataInfo.class);
        return page.toJson();
    }

    public JSONObject list(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        List<LabDataInfo> list = CoreBuilder.select().list(LabDataInfo.class);
        res.put("list", list);
        return res;
    }

    public JSONObject insert(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        LabDataInfo bean = BeanUtil.toBean(data, LabDataInfo.class);
        res.put("status", CoreBuilder.insert().save(bean));
        return res;
    }

    public JSONObject delete(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        CoreBuilder.delete().eq("data_id", data.getString("dataId")).remove(LabDataInfo.class);
        return res;
    }

    @Override
    public JSONObject update(JSONObject data) {
        return null;
    }

    public JSONObject report(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        JSONObject dataPlan = CoreBuilder.select().eq("data_id", data.getString("dataId")).one(LabDataInfo.class);
        if (dataPlan.getString("sampleStatus").equals("0")) {
            AssertUtil.isTrue(true, "未领取样本不能上报实验结果！");
        }
        LabDataInfo bean = BeanUtil.toBean(data, LabDataInfo.class);
        //往计划附件表中存数据
        List<JSONObject> fileList = (List<JSONObject>) data.get("fileList");
        List<JSONObject> labDataFileRelList = new ArrayList<>();
        fileList.forEach(sysFileInfo -> {
            LabDataFileRel labDataFileRel = new LabDataFileRel();
            BeanUtil.copyProperties(sysFileInfo, labDataFileRel);
            labDataFileRel.setBusinessId(bean.getDataId());
            JSONObject json = (JSONObject) JSONObject.toJSON(labDataFileRel);
            labDataFileRelList.add(json);
        });
        CoreBuilder.insert().saveBatch(labDataFileRelList, new LabDataFileRel());
        if (data.getString("uploadDataStatus").equals("1")) {
            bean.setEvalStatus("1");
        }
        CoreBuilder.update().edit(bean);
        return res;
    }

    public JSONObject detail(JSONObject data) {
        log.info("{}", data);
        JSONObject dataPlan = CoreBuilder.select()
                .eq("data_id", data.getString("dataId")).one(LabDataInfo.class);

        JSONObject bean = CoreBuilder.select()
                .eq("plan_id", dataPlan.getString("planId")).one(LabPlanInfo.class);
        //去计划文件关联表中拿附件信息
        List<LabPlanFileRel> fileList = CoreBuilder.select().eq("plan_id",dataPlan.getString("planId")).list(LabPlanFileRel.class);
        bean.put("fileList", fileList);
        //去数据文件关联表中拿附件信息
        List<LabDataFileRel> dataFileList = CoreBuilder.select().eq("business_id", data.getString("dataId")).list(LabDataFileRel.class);
        bean.put("dataFileList", dataFileList);
        return bean;
    }

    public JSONObject repair(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        LabDataInfo bean = BeanUtil.toBean(data, LabDataInfo.class);
        //删除原来的附件
        CoreBuilder.delete().eq("business_id", data.getString("dataId")).remove(LabPlanFileRel.class);
        //往计划附件表中存数据
        List<JSONObject> fileList = (List<JSONObject>) data.get("fileList");
        List<JSONObject> labDataFileRelList = new ArrayList<>();
        fileList.forEach(sysFileInfo -> {
            LabDataFileRel labDataFileRel = new LabDataFileRel();
            BeanUtil.copyProperties(sysFileInfo, labDataFileRel);
            labDataFileRel.setBusinessId(bean.getDataId());
            JSONObject json = (JSONObject) JSONObject.toJSON(labDataFileRel);
            labDataFileRelList.add(json);
        });
        CoreBuilder.insert().saveBatch(labDataFileRelList, new LabDataFileRel());
        if (data.getString("uploadDataStatus").equals("1")) {
            bean.setEvalStatus("1");
        }
        CoreBuilder.update().edit(bean);
        return res;
    }

    public JSONObject receive(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        LabDataInfo bean = BeanUtil.toBean(data, LabDataInfo.class);
        CoreBuilder.update().edit(bean);
        return res;
    }

    public JSONObject detailByBefore(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        String loginId = (String) StpUtil.getLoginId();
        JSONObject bean = CoreBuilder.select().eq("create_id", loginId).one(LabDataInfo.class);
        res.put("data", bean);
        return res;
    }


}