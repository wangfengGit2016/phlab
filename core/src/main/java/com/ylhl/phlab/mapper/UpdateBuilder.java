package com.ylhl.phlab.mapper;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.config.DataConfig;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UpdateBuilder {

    CoreMapper mapper;
    DataConfig dataConfig;
    SQL sql = new SQL();
    JSONObject data = new JSONObject();

    UpdateBuilder(){
        this.mapper= SpringUtil.getBean(CoreMapper.class);
        this.dataConfig=SpringUtil.getBean(DataConfig.class);
    }
    public UpdateBuilder as(String as){
        data.put("as",as);
        return this;
    }
    public UpdateBuilder inner(Class cls,String as,String... ons){
        Table table= (Table) cls.getAnnotation(Table.class);
        String tableName =table!=null?table.value():cls.getSimpleName().toLowerCase();
        StringBuffer stringBuffer = new StringBuffer();
        for (String str: ons) {
            stringBuffer.append(" ").append(str.trim()).append(" ");
        }
        sql.INNER_JOIN(tableName+" as "+as+" on "+ stringBuffer);
        data.put("inner",1);
        return this;
    }

    public UpdateBuilder like(Boolean flag,String column,String var){
        if(flag){
            return like(column,var);
        }
        return this;
    }
    public UpdateBuilder like(String column,String var){
        sql.WHERE(column + " like '%"+var+"%'");
        return this;
    }

    public UpdateBuilder set(String key,Object val){
        if(val instanceof String){
            sql.SET(key +"='"+ val+"'");
        }else {
            sql.SET(key +"="+ val);
        }
        return this;
    }
    public UpdateBuilder in(String column, List<String> list){
        StringBuilder stringBuffer = new StringBuilder();
        for (String var : list) {
            stringBuffer.append("'").append(var).append("',");
        }
        sql.WHERE(column +" in ("+stringBuffer.substring(0,stringBuffer.lastIndexOf(","))+")");
        return this;
    }

    public UpdateBuilder set(Object obj){
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (Field field : fields){
                TableField provider = field.getAnnotation(TableField.class);
                if(provider==null||!provider.ignore()){
                    String fieldName = provider!=null?provider.alias():field.getName();
                    if (data.containsKey("as")){
                        fieldName=data.getString("as")+"."+fieldName;
                    }
                    field.setAccessible(true);
                    if(field.get(obj)!=null){
                        sql.SET(fieldName+" = '"+field.get(obj)+"'");
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    public UpdateBuilder eq(String key,Object val){
        if(val instanceof String){
            sql.WHERE(key +"='"+ val+"'");
        }else {
            sql.WHERE(key +"="+ val);
        }
        return this;
    }
    public UpdateBuilder gt(String column,Object var){
        if (var.getClass().isInstance(new Date())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var = sdf.format(var);
        }
        sql.WHERE(column+"> '" + var+"'");
        return this;
    }
    public UpdateBuilder lt(String column,Object var){
        if (var.getClass().isInstance(new Date())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var = sdf.format(var);
        }
        sql.WHERE(column+"< '" + var+"'");
        return this;
    }
    public  void edit(Object obj){
        for(Field field:obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if(field.getName().equals(dataConfig.getUpdateTime())&&field.get(obj)==null){
                    field.set(obj,new Date());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mapper.updateSelective(obj);
    }

    public  void edit(Class clazz){
        Table table= (Table) clazz.getAnnotation(Table.class);
        String tableName =table!=null?table.value():clazz.getSimpleName().toLowerCase();
        if(data.containsKey("as")){
            sql.UPDATE(tableName+" as "+data.getString("as"));
        }else {
            sql.UPDATE(tableName);
        }
        mapper.exec(sql.toString());
    }

}
