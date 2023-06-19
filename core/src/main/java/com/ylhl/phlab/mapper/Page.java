package com.ylhl.phlab.mapper;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.Collections;
import java.util.List;

@Data
public class Page<T> {
    protected List<T> records = Collections.emptyList();

    protected long total = 0;

    protected long size = 10;

    protected long current = 1;

    public Page(){

    }

    public Page(JSONObject param) {
        if(StringUtils.isNotBlank(param.getString("current"))){
            this.current = param.getIntValue("current");
        }
        if(StringUtils.isNotBlank(param.getString("size"))){
            this.size = param.getIntValue("size");
        }

    }

    public JSONObject toJson(){
        JSONObject res = new JSONObject();
        res.put("total",total);
        res.put("size",size);
        res.put("current",current);
        res.put("records",records);
        return res;
    }
}
