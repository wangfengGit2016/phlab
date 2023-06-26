package com.ylhl.phlab.mapper;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.config.DataConfig;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.List;

public class DeleteBuilder {
    DataConfig dataConfig;
    CoreMapper mapper;
    SQL sql = new SQL();
    JSONObject data = new JSONObject();

    DeleteBuilder(){
        this.mapper=SpringUtil.getBean(CoreMapper.class);
        this.dataConfig=SpringUtil.getBean(DataConfig.class);
    }

    public DeleteBuilder like(Boolean flag,String column,String var){
        if(flag){
            return like(column,var);
        }
        return this;
    }
    public DeleteBuilder like(String column,String val){
        data.put("baseNum",data.getIntValue("baseNum")+1);
        String key = "key"+data.getIntValue("baseNum");
        data.put(key,val);
        sql.WHERE(column + " like CONCAT('%',#{"+key+"},'%')");
        return this;
    }
    public DeleteBuilder in(String column, List<String> list){
        StringBuilder stringBuffer = new StringBuilder();
        for (String var : list) {
            data.put("baseNum",data.getIntValue("baseNum")+1);
            String key = "key"+data.getIntValue("baseNum");
            data.put(key,var);
            stringBuffer.append("#{").append(key).append("},");
        }
        sql.WHERE(column +" in ("+stringBuffer.substring(0,stringBuffer.lastIndexOf(","))+")");
        return this;
    }
    public DeleteBuilder eq(String column,Object val){
        data.put("baseNum",data.getIntValue("baseNum")+1);
        String key = "key"+data.getIntValue("baseNum");
        data.put(key,val);
        sql.WHERE(column +"=#{"+key +"}");
        return this;
    }
    public  void remove(Class clazz){
        Table table= AnnotationUtils.findAnnotation(clazz,Table.class);
        String tableName =table!=null?table.value():clazz.getSimpleName().toLowerCase();
        for(Field field:clazz.getDeclaredFields()){
            if(dataConfig.getDeleted().equals(field.getName())){
                sql.UPDATE(tableName);
                sql.SET( dataConfig.getDeleted()+"="+ dataConfig.getIsDeleted());
                data.put("baseSql",sql.toString());
                mapper.execObject(data);
                return;
            }
        }
        sql.DELETE_FROM(tableName);
        data.put("baseSql",sql.toString());
        mapper.execObject(data);
    }
    public  void remove(Object obj){
        mapper.deleteSelective(obj);
    }
}
