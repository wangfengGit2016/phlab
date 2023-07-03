package com.ylhl.phlab.mapper;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.config.DataConfig;
import com.ylhl.phlab.domain.SysUserInfo;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.core.annotation.AnnotationUtils;

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
    public UpdateBuilder inner(Class clazz,String as,String... ons){
        Table table= AnnotationUtils.findAnnotation(clazz,Table.class);
        String tableName =table!=null?table.value():clazz.getSimpleName().toLowerCase();
        StringBuffer stringBuffer = new StringBuffer();
        for (String str: ons) {
            stringBuffer.append(" ").append(str.trim()).append(" ");
        }
        sql.INNER_JOIN(tableName+" as "+as+" on "+ stringBuffer);
        data.put("inner",1);
        return this;
    }



    public UpdateBuilder set(String column,Object val){
        data.put("baseNum",data.getIntValue("baseNum")+1);
        String key = "key"+data.getIntValue("baseNum");
        data.put(key,val);
        sql.SET(column +"=#{"+key+"}");
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


    public UpdateBuilder like(Boolean flag,String column,String var){
        if(flag){
            return like(column,var);
        }
        return this;
    }
    public UpdateBuilder like(String column,String val){
        data.put("baseNum",data.getIntValue("baseNum")+1);
        String key = "key"+data.getIntValue("baseNum");
        data.put(key,val);
        sql.WHERE(column + " like CONCAT('%',#{"+key+"},'%')");
        return this;
    }
    public UpdateBuilder in(String column, List<String> list){
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
    public UpdateBuilder eq(String column,Object val){
        data.put("baseNum",data.getIntValue("baseNum")+1);
        String key = "key"+data.getIntValue("baseNum");
        data.put(key,val);
        sql.WHERE(column +"=#{"+key +"}");
        return this;
    }
    public UpdateBuilder gt(String column,Object val){
        data.put("baseNum",data.getIntValue("baseNum")+1);
        String key = "key"+data.getIntValue("baseNum");
        data.put(key,val);
        sql.WHERE(column + " > #{"+key+"}");
        return this;
    }
    public UpdateBuilder lt(String column,Object val){
        data.put("baseNum",data.getIntValue("baseNum")+1);
        String key = "key"+data.getIntValue("baseNum");
        data.put(key,val);
        sql.WHERE(column + " < #{"+key+"}");
        return this;
    }

    public  void edit(Object obj){
        String userId = (String) StpUtil.getLoginId();
        SysUserInfo user = CoreBuilder.select().eq("id", userId).oneT(SysUserInfo.class);
        String userName = user.getUserName();
        for(Field field:obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if(field.getName().equals(dataConfig.getUpdateTime())&&field.get(obj)==null){
                    field.set(obj,new Date());
                }
                if(field.getName().equals(dataConfig.getUpdateId())&&field.get(obj)==null){
                    field.set(obj,userId);
                }
                if(field.getName().equals(dataConfig.getUpdateName())&&field.get(obj)==null){
                    field.set(obj,userName);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        mapper.updateSelective(obj);
    }

    public  void edit(Class clazz){
        Table table= AnnotationUtils.findAnnotation(clazz,Table.class);
        String tableName =table!=null?table.value():clazz.getSimpleName().toLowerCase();
        if(data.containsKey("as")){
            sql.UPDATE(tableName+" as "+data.getString("as"));
        }else {
            sql.UPDATE(tableName);
        }
        data.put("baseSql",sql.toString());
        mapper.execObject(data);
    }

}
