package com.ylhl.phlab.handler;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;

@Primary
@Component
public class RuleHandlerImpl implements RuleHandler{
    @Override
    public JSONObject ruleCheck(JSONObject obj) {
        // 获取handler与config
        JSONObject ruleMap = obj.getJSONObject("ruleMap");
        // 获取实现类对象
        Map<String, RuleHandler> map = SpringUtil.getBeansOfType(RuleHandler.class);
        List<JSONObject> deviceInfos = new ArrayList<>();
        if (!ruleMap.keySet().isEmpty()){
            for (String handler : ruleMap.keySet()){
                JSONObject rsp = map.get(handler).ruleCheck(obj);
                if (!Objects.isNull(rsp)){
                    deviceInfos.add(rsp);
                }
            }
        }
        JSONObject res = new JSONObject();
        res.put("list", deviceInfos);
        return res;
    }
}
