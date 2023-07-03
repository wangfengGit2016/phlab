package com.ylhl.phlab.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.constant.LabConstant;
import com.ylhl.phlab.domain.*;
import com.ylhl.phlab.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.jsoup.select.Evaluator;
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
                .eq(StringUtils.isNotBlank(data.getString("typeId")), "type_id", data.getString("typeId"))
                .eq(StringUtils.isNotBlank(data.getString("deptId")), "dept_id", data.getString("deptId"))
                .like(StringUtils.isNotBlank(data.getString("labName")), "lab_name", data.getString("labName"))
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
        if (dataPlan.getString("sampleStatus").equals(LabConstant.SAMPLE_STATUS_NO)) {
            AssertUtil.isTrue(true, "未领取样本不能上报实验结果！");
        }
        LabDataInfo bean = BeanUtil.toBean(data, LabDataInfo.class);
        //往数据附件表中存数据
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
        //TODO 往数据盲样结果关联表中存数据
        List<JSONObject> typeList = (List<JSONObject>) data.get("typeList");
        List<JSONObject> labDataTypeRelList = new ArrayList<>();
        typeList.forEach(typeInfo -> {
            LabDataTypeRel labDataTyeRel = new LabDataTypeRel();
            BeanUtil.copyProperties(typeInfo, labDataTyeRel);
            labDataTyeRel.setDataTypeRelId(IdUtil.fastSimpleUUID());
            labDataTyeRel.setDataId(bean.getDataId());
            JSONObject json = (JSONObject) JSONObject.toJSON(labDataTyeRel);
            labDataTypeRelList.add(json);
        });
        CoreBuilder.insert().saveBatch(labDataTypeRelList, new LabDataTypeRel());
        //如果数据上传 将该数据变为待评价状态
        if (data.getString("uploadDataStatus").equals(LabConstant.UPLOAD_DATA_STATUS_YES)) {
            bean.setEvalStatus(LabConstant.EVAL_STATUS_NO);
        }
        bean.setFileMessage("{\"fileList\":" + data.getString("fileList") + "}");
        bean.setDataExcelHead("{\"dataHeadList\":" + data.getString("dataHeadList") + "}");
        bean.setDataExcelBody("{\"dataBodyList\":" + data.getString("dataBodyList") + "}");
        String userId = (String) StpUtil.getLoginId();
        SysUserInfo user = CoreBuilder.select().eq("id", userId).oneT(SysUserInfo.class);
        String userName = user.getUserName();
        bean.setUploadUserName(userName);
        bean.setUploadUserName(user.getPhone());
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        bean.setUploadTime(year + "年" + cal.get(Calendar.MONTH) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日");
        CoreBuilder.update().edit(bean);
        return res;
    }

    public JSONObject detail(JSONObject data) {
        log.info("{}", data);
        JSONObject dataPlan = CoreBuilder.select()
                .eq("data_id", data.getString("dataId")).one(LabDataInfo.class);

        JSONObject bean = CoreBuilder.select()
                .eq("plan_id", dataPlan.getString("planId")).one(LabPlanInfo.class);
        /*//去计划文件关联表中拿附件信息
        List<LabPlanFileRel> fileList = CoreBuilder.select().eq("plan_id",dataPlan.getString("planId")).list(LabPlanFileRel.class);
        bean.put("fileList", fileList);
        //去数据文件关联表中拿附件信息
        List<LabDataFileRel> dataFileList = CoreBuilder.select().eq("business_id", data.getString("dataId")).list(LabDataFileRel.class);
        bean.put("dataFileList", dataFileList);*/
        //TODO 评价附件里去拿评价数据
        List<LabDataEvalDetail> dataEvalDetailList = CoreBuilder.select().eq("data_id", data.getString("dataId")).list(LabDataEvalDetail.class);
        bean.put("dataEvalDetailList", dataEvalDetailList);

        if (ObjectUtil.isNotNull(bean.getString("dataExcelHead"))) {
            String dataExcelHead = bean.getString("dataExcelHead");
            JSONObject jsonObjectHead = JSON.parseObject(dataExcelHead);
            bean.put("dataHeadList", jsonObjectHead.get("dataHeadList"));
        }

        if (ObjectUtil.isNotNull(bean.getString("dataExcelBody"))) {
            String dataExcelBody = bean.getString("dataExcelBody");
            JSONObject jsonObjectBody = JSON.parseObject(dataExcelBody);
            bean.put("dataBodyList", jsonObjectBody.get("dataBodyList"));
        }

        if (ObjectUtil.isNotNull(bean.getString("fileMessage"))) {
            String fileMessage = bean.getString("fileMessage");
            JSONObject jsonObject = JSON.parseObject(fileMessage);
            bean.put("fileList", jsonObject.get("fileList"));
        }
        if (ObjectUtil.isNotNull(bean.getString("deptMessage"))) {
            String deptMessage = bean.getString("deptMessage");
            JSONObject jsonObject1 = JSON.parseObject(deptMessage);
            bean.put("deptList", jsonObject1.get("deptList"));
        }
        if (ObjectUtil.isNotNull(bean.getString("excelHead"))) {
            String excelHead = bean.getString("excelHead");
            JSONObject jsonHead = JSON.parseObject(excelHead);
            bean.put("headList", jsonHead.get("headList"));
        }
        if (ObjectUtil.isNotNull(bean.getString("excelBody"))) {
            String excelBody = bean.getString("excelBody");
            JSONObject jsonBody = JSON.parseObject(excelBody);
            bean.put("bodyList", jsonBody.get("bodyList"));
        }
        bean.remove("excelHead");
        bean.remove("excelHead");
        bean.remove("deptMessage");
        bean.remove("fileMessage");
        dataPlan.put("plan", bean);
        dataPlan.remove("dataExcelHead");
        dataPlan.remove("dataExcelBody");
        dataPlan.remove("fileMessage");
        return dataPlan;
    }

    public JSONObject repair(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        LabDataInfo bean = BeanUtil.toBean(data, LabDataInfo.class);
        bean.setUploadDataStatus(LabConstant.UPLOAD_DATA_STATUS_NO);
        bean.setEvalStatus(LabConstant.EVAL_STATUS_WAIT);
        //将数据对应的下发计划的评价跳转按钮打开
        JSONObject dataPlan = CoreBuilder.select().eq("data_id", data.getString("dataId")).one(LabDataInfo.class);
        CoreBuilder.update().eq("plan_id", dataPlan.getString("planId")).set("need_eval", LabConstant.NEED_EVAL_NEED).edit(LabPlanInfo.class);
        //删除原来的附件
        CoreBuilder.delete().eq("business_id", data.getString("dataId")).remove(LabPlanFileRel.class);
        //往数据附件表中存数据
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

        //TODO 往数据盲样结果关联表中存数据
        List<JSONObject> typeList = (List<JSONObject>) data.get("typeList");
        List<JSONObject> labDataTypeRelList = new ArrayList<>();
        typeList.forEach(typeInfo -> {
            LabDataTypeRel labDataTyeRel = new LabDataTypeRel();
            BeanUtil.copyProperties(typeInfo, labDataTyeRel);
            labDataTyeRel.setDataTypeRelId(IdUtil.fastSimpleUUID());
            labDataTyeRel.setDataId(bean.getDataId());
            JSONObject json = (JSONObject) JSONObject.toJSON(labDataTyeRel);
            labDataTypeRelList.add(json);
        });
        CoreBuilder.insert().saveBatch(labDataTypeRelList, new LabDataTypeRel());

        bean.setFileMessage("{\"fileList\":" + data.getString("fileList") + "}");
        bean.setDataExcelHead("{\"dataExcelHead\":" + data.getString("dataExcelHead") + "}");
        bean.setDataExcelBody("{\"dataExcelBody\":" + data.getString("dataExcelBody") + "}");
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


    public JSONObject dataEval(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        LabDataInfo bean = BeanUtil.toBean(data, LabDataInfo.class);
        bean.setEvalStatus(LabConstant.EVAL_STATUS_YES);
        //TODO 待复查
        CoreBuilder.update().edit(bean);
        //查看所有数据是否全都评价完成
        LabDataInfo labDataInfo = CoreBuilder.select().eq("data_id", data.getString("dataId")).oneT(LabDataInfo.class);
        //往评价记录表里存数据
        LabDataEvalDetail evalDetail = BeanUtil.toBean(data, LabDataEvalDetail.class);
        evalDetail.setPlanId(labDataInfo.getPlanId());
        evalDetail.setEvalId((String) StpUtil.getLoginId());
        //TODO 评价人的姓名
        evalDetail.setEvalTime(new Date());
        evalDetail.setDataEvalId(IdUtil.fastSimpleUUID());
        CoreBuilder.insert().save(evalDetail);
        List<LabDataInfo> dataInfoList = CoreBuilder.select().eq("plan_id", labDataInfo.getPlanId()).list(LabDataInfo.class);
        int flag = 0;
        for (LabDataInfo dataInfo : dataInfoList) {
            if (!dataInfo.getEvalStatus().equals(LabConstant.EVAL_STATUS_YES)) {
                flag = 1;
                break;
            }
        }
        //如果所有数据评价完成 将该下发计划变成不需要评价状态
        if (flag == 0) {
            CoreBuilder.update().eq("plan_id", labDataInfo.getPlanId()).set("need_eval", LabConstant.NEED_EVAL_NOT_NEED).edit(LabPlanInfo.class);
        }
        //如果该考不通过 将数据保留到补考记录表中
        LabDataFailInfo failInfo = BeanUtil.toBean(labDataInfo, LabDataFailInfo.class);
        failInfo.setHistoryDataId(IdUtil.fastSimpleUUID());
        CoreBuilder.insert().save(failInfo);
        //往计划附件表中存数据
        List<LabDataFileRel> list = CoreBuilder.select().eq("data_id", data.getString("dataId")).list(LabDataFileRel.class);
        ArrayList<JSONObject> failList = new ArrayList<>();
        list.forEach(s -> {
            s.setBusinessId(failInfo.getHistoryDataId());
            failList.add((JSONObject) JSONObject.toJSON(s));
        });
        CoreBuilder.insert().saveBatch(failList, new LabDataFileRel());
        return res;
    }
}