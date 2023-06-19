package com.ylhl.phlab.mapper;

import cn.hutool.extra.spring.SpringUtil;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.config.DataConfig;
import org.apache.ibatis.jdbc.SQL;

import java.lang.reflect.Field;

public class DeleteBuilder {
    DataConfig dataConfig;
    CoreMapper mapper;
    SQL sql = new SQL();

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
    public DeleteBuilder like(String column,String var){
        sql.WHERE(column + " like '%"+var+"%'");
        return this;
    }
    public DeleteBuilder eq(String key,Object val){
        if(val instanceof String){
            sql.WHERE(key +"='"+ val+"'");
        }else {
            sql.WHERE(key +"="+ val);
        }
        return this;
    }
    public  void remove(Class clazz){
        Table table= (Table) clazz.getAnnotation(Table.class);
        String tableName =table!=null?table.value():clazz.getSimpleName().toLowerCase();
        for(Field field:clazz.getDeclaredFields()){
            if(dataConfig.getDeleted().equals(field.getName())){
                sql.UPDATE(tableName);
                sql.SET( dataConfig.getDeleted()+"="+ dataConfig.getIsDeleted());
                mapper.exec(sql.toString());
                return;
            }
        }
        sql.DELETE_FROM(tableName);
        mapper.exec(sql.toString());
    }
    public  void remove(Object obj){
         mapper.deleteSelective(obj);
    }
}
