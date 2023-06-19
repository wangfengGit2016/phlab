package com.ylhl.phlab.service;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface BatchService {
    void batch(String id,List<JSONObject> list);
}
