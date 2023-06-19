package com.ylhl.phlab.handler;


import com.alibaba.fastjson.JSONObject;

public interface RuleHandler {

    JSONObject ruleCheck(JSONObject obj);
}
