package com.ylhl.phlab.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;

import java.util.*;
import java.util.stream.Collectors;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.domain.SysUserInfo;
import com.ylhl.phlab.domain.TraPaperQuestionRel;
import com.ylhl.phlab.util.UniqueIdUtil;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraQuestionInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service("TraQuestionInfoService")
public class TraQuestionInfoService implements IService {


    public JSONObject page(JSONObject data) {
        log.info("{}", data);
        Page<TraQuestionInfo> page = new Page<>(data);
        String score = data.getString("score");
        String createName = data.getString("createName");
        String id = data.getString("uniqueId");
        String type = data.getString("questionType");
        String title = data.getString("questionTitle");
        CoreBuilder.select()
                .like(StringUtils.isNotBlank(id), "unique_id", id)
                .like(StringUtils.isNotBlank(title), "question_title", title)
                .eq(StringUtils.isNotBlank(score), "score", score)
                .like(StringUtils.isNotBlank(createName), "create_name", createName)
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
        String score = data.getString("score");
        String createName = data.getString("createName");
        String id = data.getString("unique_id");
        String type = data.getString("questionType");
        String title = data.getString("questionTitle");
        List<TraQuestionInfo> list = CoreBuilder.select()
                .like(StringUtils.isNotBlank(id), "unique_id", id)
                .like(StringUtils.isNotBlank(title), "question_title", title)
                .eq(StringUtils.isNotBlank(score), "score", score)
                .like(StringUtils.isNotBlank(createName), "create_name", createName)
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
        bean.setUniqueId(UniqueIdUtil.getId(TraQuestionInfo.class));
        String id = (String) StpUtil.getLoginId();
        SysUserInfo userInfo = CoreBuilder.select().eq("id", id).oneT(SysUserInfo.class);
        bean.setCreateId(id);
        bean.setCreateName(userInfo.getRealName());
        int affect = CoreBuilder.insert().save(bean);
        res.put("status", affect);
        return res;
    }

    public JSONObject delete(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        List<String> deleteIds = data.getJSONArray("questionIds").toJavaList(String.class);
        // 已被引用的题目不可删除
        List<String> failIds = new ArrayList<>();
        Set<String> quoteIds = CoreBuilder.select().list(TraPaperQuestionRel.class)
                .stream().map(TraPaperQuestionRel::getQuestionId).collect(Collectors.toSet());
        for (String id : deleteIds){
            if (quoteIds.contains(id)){
                failIds.add(id);
            }
        }
        deleteIds.removeAll(failIds);
        CoreBuilder.delete().in("question_id", deleteIds).remove(TraQuestionInfo.class);
        if (!failIds.isEmpty()){
            List<TraQuestionInfo> questionInfos = CoreBuilder.select().in("question_id", failIds).list(TraQuestionInfo.class);
            List<Integer> uniqueIds = questionInfos.stream().map(TraQuestionInfo::getUniqueId).collect(Collectors.toList());
            StringBuilder builder = new StringBuilder();
            for (Integer id : uniqueIds){
                builder.append(",").append(id);
            }
            throw new RuntimeException("ID为:" + builder.substring(1) + "的题目已被试卷选用，无法删除");
        }
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