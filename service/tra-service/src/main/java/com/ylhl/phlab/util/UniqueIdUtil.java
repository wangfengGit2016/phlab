package com.ylhl.phlab.util;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.CommonConstant;
import com.ylhl.phlab.mapper.CoreBuilder;

import java.util.ArrayList;
import java.util.List;


public class UniqueIdUtil {

    public static <T> Integer getId(Class<T> clazz){
        List<JSONObject> maxIdList ;
        try {
            maxIdList = CoreBuilder.select().select("MAX(unique_id) as unique_id").listJson(clazz);
        } catch (Exception e){
            maxIdList = new ArrayList<>();
        }
        return maxIdList.isEmpty() ? CommonConstant.NUM_START : maxIdList.get(0).getInteger("unique_id") + CommonConstant.NUM_STEP;
    }
}
