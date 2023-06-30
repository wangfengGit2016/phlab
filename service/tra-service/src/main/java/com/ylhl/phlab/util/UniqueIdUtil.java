package com.ylhl.phlab.util;


import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.CommonConstant;
import com.ylhl.phlab.mapper.CoreBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class UniqueIdUtil {

    public static <T> Integer getId(Class<T> clazz){
        List<JSONObject> maxIdList ;
        try {
            maxIdList = CoreBuilder.select().select("MAX(unique_id) as unique_id").listJson(clazz);
        } catch (Exception e){
            maxIdList = new ArrayList<>();
        }
        if (maxIdList.isEmpty() || maxIdList.get(0)==null || maxIdList.get(0).getInteger("unique_id") == null){
            return CommonConstant.NUM_START;
        }
        Integer existId = maxIdList.get(0).getInteger("unique_id");
        if (Objects.isNull(existId)){
            return CommonConstant.NUM_START;
        }
        return existId + CommonConstant.NUM_STEP;
    }
}
