package com.ylhl.phlab.service;

import com.alibaba.fastjson.JSONObject;
public interface IService{

      JSONObject page(JSONObject data);

      JSONObject list(JSONObject data);

      JSONObject insert(JSONObject data);

      JSONObject delete(JSONObject data);

      JSONObject update(JSONObject data);

      JSONObject detail(JSONObject data);

}