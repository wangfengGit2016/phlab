package com.ylhl.phlab.service.impl;

import cn.hutool.core.bean.BeanUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.domain.TraPaperQuestionRel;
import com.ylhl.phlab.util.UniqueIdUtil;
import com.ylhl.phlab.util.UserUtil;
import lombok.extern.slf4j.Slf4j;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.domain.TraPaperInfo;
import com.ylhl.phlab.mapper.CoreBuilder;
import com.ylhl.phlab.mapper.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("TraPaperInfoService")
public class TraPaperInfoService implements IService {

    public JSONObject page(JSONObject data) {
        log.info("{}", data);
        Page<TraPaperInfo> page = new Page<>(data);
        CoreBuilder.select().page(page, TraPaperInfo.class);
        return page.toJson();
    }

    public JSONObject list(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        List<TraPaperInfo> list = CoreBuilder.select().list(TraPaperInfo.class);
        res.put("list", list);
        return res;
    }

    @Transactional
    public JSONObject insert(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        paperInsert(data, IdUtil.fastSimpleUUID());
        return res;
    }

    public JSONObject delete(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        List<String> paperIds = data.getJSONArray("paperIds").toJavaList(String.class);
        CoreBuilder.delete().in("paper_id", paperIds).remove(TraPaperInfo.class);
        CoreBuilder.delete().in("paper_id", paperIds).remove(TraPaperQuestionRel.class);
        return res;
    }

    public JSONObject update(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        TraPaperInfo bean = BeanUtil.toBean(data, TraPaperInfo.class);
        CoreBuilder.update().edit(bean);
        return res;
    }

    public JSONObject detail(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        JSONObject bean = CoreBuilder.select().eq("paper_id", data.getString("paperId")).one(TraPaperInfo.class);
        res.put("data", bean);
        return res;
    }

    public JSONObject publish(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        return res;
    }

    public TraPaperInfo paperInsert(JSONObject data, String id){
        TraPaperInfo bean = new TraPaperInfo();
        bean.setPaperId(id);
        bean.setPaperName(data.getString("paperName"));
        bean.setScore(data.getString("score"));
        bean.setExamDuration(data.getInteger("examDuration"));
        bean.setUniqueId(UniqueIdUtil.getId(TraPaperInfo.class));
        bean.setPassScore(data.getString("passScore"));
        bean.setStartExamTime(data.getDate("startExamTime"));
        bean.setEndExamTime(data.getDate("endExamTime"));
        bean.setCreateId(UserUtil.userId());
        bean.setCreateName(UserUtil.userName());
        // 插入关联表
        final AtomicInteger count = new AtomicInteger(0);
        List<JSONObject> relInsert = new ArrayList<>();
        List<JSONObject> items = data.getJSONArray("items").toJavaList(JSONObject.class);
        items.forEach(item -> {
            JSONArray questions = item.getJSONArray("questions");
            count.addAndGet(questions.size());
            questions.forEach(question -> {
                TraPaperQuestionRel rel = new TraPaperQuestionRel();
                // 通用属性
                rel.setId(IdUtil.fastSimpleUUID());
                rel.setModuleName(item.getString("moduleName"));
                rel.setModuleSort(item.getInteger("moduleSort"));
                // 题目属性
                JSONObject ques = JSONObject.parseObject(JSONObject.toJSONString(question));
                rel.setPaperId(id);
                rel.setQuestionId(ques.getString("questionId"));
                rel.setScore(ques.getString("score"));
                rel.setQuestionSort(ques.getInteger("questionSort"));
                rel.setQuestionType(ques.getString("questionType"));
                relInsert.add((JSONObject) JSONObject.toJSON(rel));
            });
        });
        bean.setQuestionCount(count.get());
        CoreBuilder.insert().save(bean);
        CoreBuilder.insert().saveBatch(relInsert, new TraPaperQuestionRel());
        return bean;
    }
}