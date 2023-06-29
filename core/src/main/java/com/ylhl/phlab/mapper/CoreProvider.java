package com.ylhl.phlab.mapper;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.annotation.TableId;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

@Slf4j
public class CoreProvider {
    public String insertSelective(Object obj) throws IllegalAccessException {
        SQL sql = new SQL();
        Table table=obj.getClass().getAnnotation(Table.class);
        String tableName =table!=null?table.value():obj.getClass().getSimpleName().toLowerCase();
        sql.INSERT_INTO(tableName);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields){
            TableField provider = field.getAnnotation(TableField.class);
            if(provider==null||!provider.ignore()){
                String fieldName = provider!=null?provider.alias():field.getName();
                field.setAccessible(true);
                if(field.get(obj)!=null){
                    sql.VALUES(fieldName, "#{"+field.getName()+"}");
                }
            }


        }
        return sql.toString();
    }

    public String deleteSelective(Object obj) throws IllegalAccessException {
        SQL sql = new SQL();
        Table table=obj.getClass().getAnnotation(Table.class);
        String tableName =table!=null?table.value():obj.getClass().getSimpleName().toLowerCase();
        sql.DELETE_FROM(tableName);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields){
            TableField provider = field.getAnnotation(TableField.class);
            String fieldName = provider!=null?provider.alias():field.getName();
            field.setAccessible(true);
            if(field.get(obj)!=null){
                if(field.getAnnotation(TableId.class)!=null){
                    sql.WHERE(fieldName+" = #{"+field.getName()+"}");
                }
            }
        }
        return sql.toString();
    }

    public String updateSelective(Object obj) throws IllegalAccessException {
        SQL sql = new SQL();
        Table table=obj.getClass().getAnnotation(Table.class);
        String tableName =table!=null?table.value():obj.getClass().getSimpleName().toLowerCase();
        sql.UPDATE(tableName);
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields){
            TableField provider = field.getAnnotation(TableField.class);
            if(provider==null||!provider.ignore()){
                String fieldName = provider!=null?provider.alias():field.getName();
                field.setAccessible(true);
                if(field.getAnnotation(TableId.class)!=null){
                    sql.WHERE(fieldName+" = #{"+field.getName()+"}");
                }else {
                    if(field.get(obj)!=null){
                        sql.SET(fieldName+" = #{"+field.getName()+"}");
                    }
                }
            }
        }
        return sql.toString();
    }


    public String exec(String sql) {
        return sql;
    }

    public String execObject(JSONObject sql) {

        return sql.getString("baseSql");
    }

    public String countObject(JSONObject sql) {

        return sql.getString("baseSql").replaceAll("\\r\\n|\\r|\\n"," ")
                .replaceFirst("SELECT(.*)FROM","SELECT count(1) FROM");
    }

    public String count(String sql) {
        return sql.replaceAll("\\r\\n|\\r|\\n"," ")
                .replaceFirst("SELECT(.*)FROM","SELECT count(1) FROM");
    }

}
