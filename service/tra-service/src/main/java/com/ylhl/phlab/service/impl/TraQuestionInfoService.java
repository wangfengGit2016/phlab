package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.CommonConstant;
import com.ylhl.phlab.domain.TraTagInfo;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraQuestionInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service("TraQuestionInfoService")
public class TraQuestionInfoService implements IService {


    public JSONObject page(JSONObject data) {
        log.info("{}", data);
        Page<TraQuestionInfo> page = new Page<>(data);
        String tag = data.getString("tag");
        String deptName = data.getString("deptName");
        String id = data.getString("uniqueId");
        String type = data.getString("questionType");
        CoreBuilder.select()
                .like(StringUtils.isNotBlank(tag), "tag", tag)
                .like(StringUtils.isNotBlank(deptName), "dept_name", deptName)
                .like(StringUtils.isNotBlank(id), "unique_id", id)
                .eq(StringUtils.isNotBlank(type), "question_type", type)
                .desc("create_time").page(page, TraQuestionInfo.class);
        Page<JSONObject> pageJson = new Page<>();
        BeanUtils.copyProperties(page, pageJson, "records");
        pageJson.setRecords(elementConvert(page.getRecords(), "questionContent"));
        return pageJson.toJson();
    }

    public JSONObject list(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        String tag = data.getString("tag");
        String deptName = data.getString("deptName");
        String id = data.getString("unique_id");
        String type = data.getString("questionType");
        List<TraQuestionInfo> list = CoreBuilder.select()
                .like(StringUtils.isNotBlank(tag), "tag", tag)
                .like(StringUtils.isNotBlank(deptName), "dept_name", deptName)
                .like(StringUtils.isNotBlank(id), "unique_id", id)
                .eq(StringUtils.isNotBlank(type), "question_type", type)
                .desc("create_time").list(TraQuestionInfo.class);
        res.put("list", elementConvert(list, "questionContent"));
        return res;
    }

    public JSONObject insert(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        TraQuestionInfo bean = BeanUtil.toBean(data, TraQuestionInfo.class);
        bean.setQuestionId(IdUtil.fastSimpleUUID());
        List<TraQuestionInfo> maxIdList ;
        try {
            maxIdList = CoreBuilder.select().select("MAX(unique_id) as unique_id").list(TraQuestionInfo.class);
        } catch (Exception e){
            maxIdList = new ArrayList<>();
        }
        Integer maxId = maxIdList.isEmpty() ? CommonConstant.NUM_START : maxIdList.get(0).getUniqueId() + CommonConstant.NUM_STEP;
        bean.setUniqueId(maxId);
        int affect = CoreBuilder.insert().save(bean);
        res.put("status", affect);
        if (affect==0){
            return res;
        }
        // 新建成功则同步标签表
        String[] tags = data.getString("tag").split("、");
        List<String> existTag = CoreBuilder.select().list(TraTagInfo.class).stream().map(TraTagInfo::getTagContent).collect(Collectors.toList());
        List<JSONObject> readyTag = new ArrayList<>();
        for (String tag : tags){
            if (!existTag.contains(tag)){
                TraTagInfo tagInfo = new TraTagInfo();
                tagInfo.setTagId(IdUtil.fastSimpleUUID());
                tagInfo.setTagContent(tag);
                tagInfo.setBusinessType(CommonConstant.QUESTION_TYPE);
                readyTag.add((JSONObject) JSONObject.toJSON(tagInfo));
            }
        }
        if (!readyTag.isEmpty()){
            CoreBuilder.insert().saveBatch(readyTag, new TraTagInfo());
        }
        return res;
    }

    public JSONObject delete(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        CoreBuilder.delete().eq("question_id", data.getString("questionId")).remove(TraQuestionInfo.class);
        return res;
    }

    public JSONObject update(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        TraQuestionInfo bean = BeanUtil.toBean(data, TraQuestionInfo.class);
        CoreBuilder.update().edit(bean);
        return res;
    }

    public JSONObject detail(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        List<TraQuestionInfo> questionInfos = CoreBuilder.select().eq("question_id", data.getString("questionId")).list(TraQuestionInfo.class);
        List<JSONObject> beans = elementConvert(questionInfos, "questionContent");
        res.put("data", beans.get(0));
        return res;
    }

    private List<JSONObject> elementConvert(List<TraQuestionInfo> questionInfos, String element) {
        List<JSONObject> res = new ArrayList<>();
        for (TraQuestionInfo questionInfo : questionInfos){
            JSONObject obj = (JSONObject) JSONObject.toJSON(questionInfo);
            String content = questionInfo.getQuestionContent();
            List<JSONObject> options = JSONObject.parseArray(content).toJavaList(JSONObject.class);
            obj.put(element, options);
            res.add(obj);
        }
        return res;
    }
}