package com.ylhl.phlab.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.api.client.json.Json;
import com.ylhl.phlab.domain.*;
import com.ylhl.phlab.dto.SysFileInfoDTO;
import com.ylhl.phlab.mapper.DeleteBuilder;
import com.ylhl.phlab.utils.AssertUtil;
import com.ylhl.phlab.vo.SysOrganInfoVO;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.jni.FileInfo;
import org.jsoup.select.Evaluator;
import org.springframework.stereotype.Service;

@Slf4j
@Service("LabPlanInfoService")
public class LabPlanInfoService implements IService {

    public JSONObject page(JSONObject data) {
        log.info("{}", data);
        Page<LabPlanInfo> page = new Page<>(data);
        //TODO 模糊搜索后total等于0的问题
        CoreBuilder.select()
                .like(StringUtils.isNotBlank(data.getString("docNumber")), "doc_number", data.getString("docNumber"))
                .like(StringUtils.isNotBlank(data.getString("title")), "title", data.getString("title"))
                .eq(StringUtils.isNotBlank(data.getString("deptId")), "dept_id", data.getString("deptId"))
                .like(StringUtils.isNotBlank(data.getString("planUserName")), "plan_user_name", data.getString("planUserName"))
                .eq(StringUtils.isNotBlank(data.getString("status")), "status", data.getString("status"))
                .between(ObjectUtil.isNotNull(data.get("startTime")), "create_time", data.getDate("startTime"), data.getDate("endTime"))
                .desc("create_time")
                .page(page, LabPlanInfo.class);
        System.out.println(page.getTotal());
        Page page1 = new Page<>(data);
        page1.setTotal(page.getTotal());
        List<JSONObject> config = page.getRecords().stream().map(item -> {
            JSONObject dto = (JSONObject) JSONObject.toJSON(item);
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                Date end = sdf.parse(String.valueOf(new DateTime()));
                Date start = sdf.parse(String.valueOf(item.getCreateTime()));
                long cha = end.getTime() - start.getTime();
                double result = cha * 1.0 / (1000 * 60 * 60);
                if (result <= 24) {
                    //可撤回
                    dto.put("revokeStatus", "0");
                } else {
                    //不可撤回
                    dto.put("revokeStatus", "1");
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

            return dto;
        }).collect(Collectors.toList());
        page1.setRecords(config);
        return page1.toJson();
    }

    public JSONObject list(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        List<LabPlanInfo> list = CoreBuilder.select()
                .eq(StringUtils.isNotBlank(data.getString("deptId")), "dept_id", data.getString("deptId"))
                .list(LabPlanInfo.class);
        res.put("list", list);
        return res;
    }

    public JSONObject insert(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        LabPlanInfo bean = BeanUtil.toBean(data, LabPlanInfo.class);
        bean.setPlanId(IdUtil.fastSimpleUUID());
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //往计划表中存数据 同时填充年度信息 //科室信息// 发布时间 发布状态 地区的数量 反显字段
        String organId = (String) StpUtil.getSession().get("organId");
        SysOrganInfo sysOrganInfo = CoreBuilder.select().eq("id", organId).oneT(SysOrganInfo.class);
        bean.setDeptId(organId);
        bean.setDeptName(sysOrganInfo.getNodeName());
        bean.setYear(String.valueOf(year));
        List<JSONObject> siteList = (List<JSONObject>) data.get("siteList");
        bean.setSiteTotal(siteList.size());
        bean.setReleaseTime(year + "年" + cal.get(Calendar.MONTH) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日");
        res.put("status", CoreBuilder.insert().save(bean));
        //往计划附件表中存数据
        List<JSONObject> fileList = (List<JSONObject>) data.get("fileList");
        List<JSONObject> labPlanFileRelList = new ArrayList<>();
        fileList.forEach(sysFileInfo -> {
            LabPlanFileRel labPlanFileRel = new LabPlanFileRel();
            BeanUtil.copyProperties(sysFileInfo, labPlanFileRel);
            labPlanFileRel.setPlanId(bean.getPlanId());
            JSONObject json = (JSONObject) JSONObject.toJSON(labPlanFileRel);
            labPlanFileRelList.add(json);
        });
        CoreBuilder.insert().saveBatch(labPlanFileRelList, new LabPlanFileRel());
        //往计划场地关联表中存数据

        List<JSONObject> labPlanSiteList = new ArrayList<>();
        siteList.forEach(sysRegionInfo -> {
            LabPlanSiteRel labPlanSiteRel = new LabPlanSiteRel();
            BeanUtil.copyProperties(sysRegionInfo, labPlanSiteRel);
            labPlanSiteRel.setPlanId(bean.getPlanId());
            labPlanSiteRel.setPlanSiteId(IdUtil.fastSimpleUUID());
            JSONObject json = (JSONObject) JSONObject.toJSON(labPlanSiteRel);
            labPlanSiteList.add(json);
        });
        CoreBuilder.insert().saveBatch(labPlanSiteList, new LabPlanSiteRel());
        //往计划场地科室关联表中存数据
        List<JSONObject> deptList = (List<JSONObject>) data.get("deptList");
        List<JSONObject> labPlanSiteDeptList = new ArrayList<>();
        deptList.forEach(labDepartmentInfo -> {
            LabPlanSiteDepartmentRel labPlanSiteDeptRel = new LabPlanSiteDepartmentRel();
            BeanUtil.copyProperties(labDepartmentInfo, labPlanSiteDeptRel);
            labPlanSiteDeptRel.setPlanId(bean.getPlanId());
            labPlanSiteDeptRel.setPlanSiteDeptId(IdUtil.fastSimpleUUID());
            JSONObject json = (JSONObject) JSONObject.toJSON(labPlanSiteDeptRel);
            labPlanSiteDeptList.add(json);
        });
        CoreBuilder.insert().saveBatch(labPlanSiteDeptList, new LabPlanSiteDepartmentRel());
        //如果是发布状态 要下发试卷
        //TODO 下发员工id name 所在地区id name
        if (bean.getStatus().equals("1")) {
            List<JSONObject> labDataInfoList = new ArrayList<>();
            for (JSONObject jsonObject : deptList) {
                LabDataInfo labDataInfo = new LabDataInfo();
                BeanUtil.copyProperties(bean, labDataInfo);
                labDataInfo.setDataId(IdUtil.fastSimpleUUID());
                labDataInfo.setDataDeptId(jsonObject.getString("deptId"));
                labDataInfo.setDataDeptName(jsonObject.getString("deptName"));
                //labDataInfo.setDeptId(s.getDeptName());
                JSONObject json = (JSONObject) JSONObject.toJSON(labDataInfo);
                labDataInfoList.add(json);
            }
            CoreBuilder.insert().saveBatch(labDataInfoList, new LabDataInfo());
        }
        return res;
    }

    public JSONObject delete(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        String planIds = data.getString("planIds");
        String[] planId = planIds.split(",");
        List<String> list = Arrays.asList(planId);
        list.forEach(s -> {
            JSONObject bean = CoreBuilder.select().eq("plan_id", s).one(LabPlanInfo.class);
            if (bean.getString("status").equals("1")) {
                AssertUtil.isTrue(true, "已发布的计划不可删除！");
            }
        });
        CoreBuilder.delete().in("plan_id", list).remove(LabPlanInfo.class);
        return res;
    }

    public JSONObject update(JSONObject data) {
        log.info("{}", data);
        //只有没发布的下发计划才能编辑
        JSONObject res = new JSONObject();
        JSONObject plan = CoreBuilder.select().eq("plan_id", data.getString("planId")).one(LabPlanInfo.class);
        if (plan.getString("status").equals("1")) {
            AssertUtil.isTrue(true, "已发布的下发计划不可编辑！");
        }
        //删计划文件关联表
        CoreBuilder.delete().eq("plan_id", data.getString("planId")).remove(LabPlanFileRel.class);
        //删计划场地关联表
        CoreBuilder.delete().eq("plan_id", data.getString("planId")).remove(LabPlanSiteRel.class);
        //删计划科室关联表
        CoreBuilder.delete().eq("plan_id", data.getString("planId")).remove(LabPlanSiteDepartmentRel.class);
        LabPlanInfo bean = BeanUtil.toBean(data, LabPlanInfo.class);
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        //往计划表中存数据 同时填充年度信息 //科室信息// 发布时间 发布状态 地区的数量 反显字段
        String organId = (String) StpUtil.getSession().get("organId");
        SysOrganInfo sysOrganInfo = CoreBuilder.select().eq("id", organId).oneT(SysOrganInfo.class);
        bean.setDeptId(organId);
        bean.setDeptName(sysOrganInfo.getNodeName());
        bean.setYear(String.valueOf(year));
        bean.setReleaseTime(year + "年" + cal.get(Calendar.MONTH) + "月" + cal.get(Calendar.DAY_OF_MONTH) + "日");
        List<JSONObject> siteList = (List<JSONObject>) data.get("siteList");
        bean.setSiteTotal(siteList.size());
        CoreBuilder.update().edit(bean);
        //TODO 新增的部分拉过来
        //往计划附件表中存数据
        List<JSONObject> fileList = (List<JSONObject>) data.get("fileList");
        List<JSONObject> labPlanFileRelList = new ArrayList<>();
        fileList.forEach(sysFileInfo -> {
            LabPlanFileRel labPlanFileRel = new LabPlanFileRel();
            BeanUtil.copyProperties(sysFileInfo, labPlanFileRel);
            labPlanFileRel.setPlanId(bean.getPlanId());
            JSONObject json = (JSONObject) JSONObject.toJSON(labPlanFileRel);
            labPlanFileRelList.add(json);
        });
        CoreBuilder.insert().saveBatch(labPlanFileRelList, new LabPlanFileRel());
        //往计划场地关联表中存数据
        List<JSONObject> labPlanSiteList = new ArrayList<>();
        siteList.forEach(sysRegionInfo -> {
            LabPlanSiteRel labPlanSiteRel = new LabPlanSiteRel();
            BeanUtil.copyProperties(sysRegionInfo, labPlanSiteRel);
            labPlanSiteRel.setPlanId(bean.getPlanId());
            labPlanSiteRel.setPlanSiteId(IdUtil.fastSimpleUUID());
            JSONObject json = (JSONObject) JSONObject.toJSON(labPlanSiteRel);
            labPlanSiteList.add(json);
        });
        CoreBuilder.insert().saveBatch(labPlanSiteList, new LabPlanSiteRel());
        //往计划场地科室关联表中存数据
        List<JSONObject> deptList = (List<JSONObject>) data.get("deptList");
        List<JSONObject> labPlanSiteDeptList = new ArrayList<>();
        deptList.forEach(labDepartmentInfo -> {
            LabPlanSiteDepartmentRel labPlanSiteDeptRel = new LabPlanSiteDepartmentRel();
            BeanUtil.copyProperties(labDepartmentInfo, labPlanSiteDeptRel);
            labPlanSiteDeptRel.setPlanId(bean.getPlanId());
            labPlanSiteDeptRel.setPlanSiteDeptId(IdUtil.fastSimpleUUID());
            JSONObject json = (JSONObject) JSONObject.toJSON(labPlanSiteDeptRel);
            labPlanSiteDeptList.add(json);
        });
        CoreBuilder.insert().saveBatch(labPlanSiteDeptList, new LabPlanSiteDepartmentRel());
        //如果是发布状态 要下发试卷
        //TODO 下发员工id name 所在地区id name
        if (bean.getStatus().equals("1")) {
            List<JSONObject> labDataInfoList = new ArrayList<>();
            for (JSONObject jsonObject : deptList) {
                LabDataInfo labDataInfo = new LabDataInfo();
                BeanUtil.copyProperties(bean, labDataInfo);
                labDataInfo.setDataId(IdUtil.fastSimpleUUID());
                labDataInfo.setDataDeptId(jsonObject.getString("deptId"));
                labDataInfo.setDataDeptName(jsonObject.getString("deptName"));
                //labDataInfo.setDeptId(s.getDeptName());
                JSONObject json = (JSONObject) JSONObject.toJSON(labDataInfo);
                labDataInfoList.add(json);
            }
            CoreBuilder.insert().saveBatch(labDataInfoList, new LabDataInfo());
        }
        return res;
    }

    public JSONObject detail(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        JSONObject bean = CoreBuilder.select().eq("plan_id", data.getString("planId")).one(LabPlanInfo.class);
        String message = bean.getString("message");
        JSONObject jsonObject = JSON.parseObject(message);
        bean.put("message", jsonObject);
        //去计划区域关联表中拿区域信息
        List<LabPlanSiteRel> siteList = CoreBuilder.select().eq("plan_id", data.getString("planId")).list(LabPlanSiteRel.class);
        bean.put("siteList", siteList);
        //去计划文件关联表中拿附件信息
        List<LabPlanFileRel> fileList = CoreBuilder.select().eq("plan_id", data.getString("planId")).list(LabPlanFileRel.class);
        bean.put("fileList", fileList);
        res.put("data", bean);
        return res;
    }

    public JSONObject revoke(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        //该记录变成待提交
        CoreBuilder.update().eq("plan_id", data.getString("planId")).set("status", "0").edit(LabPlanInfo.class);
        //删除所有下发的资料
        CoreBuilder.delete().eq("plan_id", data.getString("planId")).remove(LabDataInfo.class);
        return res;
    }

    public JSONObject siteAndDeptTree(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        String level = data.getString("level");
        List<SysOrganInfo> list = CoreBuilder.select().eq("level", data.getString("level")).eq(StringUtils.isNotBlank(data.getString("deptId")), "parent_id", data.getString("deptId")).list(SysOrganInfo.class);
        if (level.equals("DistrictCounty")) {
            //找出所有的科室
            List<SysOrganInfo> deptList = CoreBuilder.select().eq("level", "Department").list(SysOrganInfo.class);
            HashMap<String, List<SysOrganInfo>> stringListHashMap = new HashMap<>();
            deptList.forEach(sysOrganInfo -> {
                String parentId = sysOrganInfo.getParentId();
                if (stringListHashMap.containsKey(parentId)) {
                    List<SysOrganInfo> sysOrganInfos = stringListHashMap.get(parentId);
                    sysOrganInfos.add(sysOrganInfo);
                } else {
                    List<SysOrganInfo> sysOrganInfos = new ArrayList<>();
                    sysOrganInfos.add(sysOrganInfo);
                    stringListHashMap.put(sysOrganInfo.getParentId(), sysOrganInfos);
                }
            });
            List<SysOrganInfoVO> organInfoVOList = list.stream().map(info -> {
                SysOrganInfoVO sysOrganInfoVO = new SysOrganInfoVO();
                try {
                    BeanUtils.copyProperties(sysOrganInfoVO, info);
                    sysOrganInfoVO.setDeptId(info.getId());
                } catch (Exception e) {
                    e.getMessage();
                }
                if (stringListHashMap.containsKey(sysOrganInfoVO.getDeptId())) {
                    sysOrganInfoVO.setDeptList(stringListHashMap.get(sysOrganInfoVO.getDeptId()));
                }
                return sysOrganInfoVO;
            }).collect(Collectors.toList());
            res.put("list", organInfoVOList);
        } else {
            List<SysOrganInfoVO> organInfoVOList = list.stream().map(info -> {
                SysOrganInfoVO sysOrganInfoVO = new SysOrganInfoVO();
                try {
                    BeanUtils.copyProperties(sysOrganInfoVO, info);
                    sysOrganInfoVO.setDeptId(info.getId());
                } catch (Exception e) {
                    e.getMessage();
                }
                return sysOrganInfoVO;
            }).collect(Collectors.toList());
            res.put("list", organInfoVOList);
        }

        return res;
    }

    public JSONObject detailByBefore(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        String loginId = (String) StpUtil.getLoginId();
        JSONObject bean = CoreBuilder.select().eq("create_id", loginId).one(LabPlanInfo.class);
        res.put("data", bean);
        return res;
    }
}