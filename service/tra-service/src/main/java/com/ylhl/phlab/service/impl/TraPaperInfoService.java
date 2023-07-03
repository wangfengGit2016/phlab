package com.ylhl.phlab.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service("TraPaperInfoService")
public class TraPaperInfoService implements IService {

    public JSONObject page(JSONObject data) {
        log.info("{}", data);
        Page<TraPaperInfo> page = new Page<>(data);
        String uniqueId = String.valueOf(data.getInteger("uniqueId"));
        String paperName = data.getString("paperName");
        String createName = data.getString("createName");
        CoreBuilder.select()
                .like(StringUtils.isNotBlank(uniqueId), "unique_id", uniqueId)
                .like(StringUtils.isNotBlank(paperName), "paper_name", paperName)
                .like(StringUtils.isNotBlank(createName), "create_name", createName)
                .between(Objects.nonNull(data.getDate("startTime")), "create_time", data.getDate("startTime"), data.getDate("endTime"))
                .page(page, TraPaperInfo.class);
        return page.toJson();
    }

    public JSONObject list(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        String uniqueId = String.valueOf(data.getInteger("uniqueId"));
        String paperName = data.getString("paperName");
        String createName = data.getString("createName");
        List<TraPaperInfo> list = CoreBuilder.select()
                .like(StringUtils.isNotBlank(uniqueId), "unique_id", uniqueId)
                .like(StringUtils.isNotBlank(paperName), "paper_name", paperName)
                .like(StringUtils.isNotBlank(createName), "create_name", createName)
                .between(Objects.nonNull(data.getDate("startTime")), "create_time", data.getDate("startTime"), data.getDate("endTime"))
                .list(TraPaperInfo.class);
        res.put("list", list);
        return res;
    }

    @Transactional
    public JSONObject insert(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        paperControl(data, IdUtil.fastSimpleUUID(),"insert");
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

    @Transactional
    public JSONObject update(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        String paperId = data.getString("paperId");
        // 删除关联表
        CoreBuilder.delete().eq("paper_id", paperId).remove(TraPaperQuestionRel.class);
        paperControl(data, paperId,"update");
        return res;
    }

    public JSONObject detail(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        String paperId = data.getString("paperId");

        return res;
    }

    public JSONObject publish(JSONObject data) {
        log.info("{}", data);
        JSONObject res = new JSONObject();
        CoreBuilder.update().eq("paper_id", data.getString("paperId")).set("status", data.getString("status"));
        return res;
    }

    public TraPaperInfo paperControl(JSONObject data, String id, String method){
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
        if ("insert".equals(method)){
            CoreBuilder.insert().save(bean);
        }
        if ("update".equals(method)){
            bean.setUniqueId(data.getInteger("uniqueId"));
            CoreBuilder.update().edit(bean);
        }
        CoreBuilder.insert().saveBatch(relInsert, new TraPaperQuestionRel());
        return bean;
    }
}