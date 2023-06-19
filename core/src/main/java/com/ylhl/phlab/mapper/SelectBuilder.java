package com.ylhl.phlab.mapper;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.Table;
import com.ylhl.phlab.annotation.TableField;
import com.ylhl.phlab.config.DataConfig;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SelectBuilder {

    CoreMapper mapper;
    DataConfig dataConfig;

    SQL sql = new SQL();
    JSONObject data = new JSONObject();

    SelectBuilder(){
        this.mapper=SpringUtil.getBean(CoreMapper.class);
        this.dataConfig=SpringUtil.getBean(DataConfig.class);
    }

    public SelectBuilder select(String... columns){
        for(String column:columns){
            sql.SELECT(column);
        }
        data.put("column",1);
        return this;
    }

    private SelectBuilder select(Object obj){
        return select(obj.getClass());
    }

    private SelectBuilder select(Class cls){
        if(data.getIntValue("column")==0){
            Field[] fields =cls.getDeclaredFields();
            for (Field field : fields){
                if(dataConfig.getDeleted().equals(field.getName())){
                    String fieldName = dataConfig.getDeleted();
                    if(data.containsKey("as")){
                        fieldName=data.getString("as")+"."+fieldName;
                    }
                    sql.WHERE( fieldName+" = "+dataConfig.getNoDeleted());
                }
                TableField provider = field.getAnnotation(TableField.class);
                String fieldName = provider!=null?provider.alias():field.getName();
                if(provider==null||!provider.ignore()){
                    sql.SELECT(provider!=null?fieldName+" "+field.getName():fieldName);
                }
            }
        }else {
            Field[] fields =cls.getDeclaredFields();
            for (Field field : fields){
                if(dataConfig.getDeleted().equals(field.getName())){
                    String fieldName = dataConfig.getDeleted();
                    if(data.containsKey("as")){
                        fieldName=data.getString("as")+"."+fieldName;
                    }
                    sql.WHERE( fieldName+" = "+dataConfig.getNoDeleted());
                }
            }
        }
        from(cls);
        return this;
    }

    private SelectBuilder from(Object obj){
        return from(obj.getClass());
    }
    private SelectBuilder from(Class cls){
        Table table= AnnotationUtils.findAnnotation(cls,Table.class);
        String tableName =table!=null?table.value():cls.getSimpleName().toLowerCase();
        sql.FROM(tableName+(data.containsKey("as")?" as "+data.getString("as"):""));
        return this;
    }

    public SelectBuilder as(String as){
        data.put("as",as);
        return this;
    }

    public SelectBuilder inner(Class cls,String as,String... ons){
        Table table= AnnotationUtils.findAnnotation(cls,Table.class);
        String tableName =table!=null?table.value():cls.getSimpleName().toLowerCase();
        StringBuffer stringBuffer = new StringBuffer();
        for (String str: ons) {
            stringBuffer.append(" ").append(str.trim()).append(" ");
        }
        sql.INNER_JOIN(tableName+" as "+as+" on "+ stringBuffer);
        data.put("inner",1);
        return this;
    }

    public SelectBuilder leftJoin(Class cls,String as,String... ons){
        Table table= AnnotationUtils.findAnnotation(cls,Table.class);
        String tableName =table!=null?table.value():cls.getSimpleName().toLowerCase();
        StringBuffer stringBuffer = new StringBuffer();
        for (String str: ons) {
            stringBuffer.append(" ").append(str.trim()).append(" ");
        }
        sql.LEFT_OUTER_JOIN(tableName+" as "+as+" on "+ stringBuffer);
        data.put("inner",1);
        return this;
    }


    public SelectBuilder eq(Boolean flag,String column,String var){
        if(flag){
            return eq(column,var);
        }
        return this;
    }

    public SelectBuilder eq(String column,String var){
        sql.WHERE(column+"= '" + var+"'");
        return this;
    }
    public SelectBuilder eq(Method column, String var){
        sql.WHERE(column+"= '" + var+"'");
        return this;
    }

    public SelectBuilder notEq(Boolean flag,String column,String var){
        if(flag){
            return notEq(column,var);
        }
        return this;
    }
    public SelectBuilder notEq(String column,String var){
        sql.WHERE(column+"!= '" + var+"'");
        return this;
    }
    public SelectBuilder like(Boolean flag,String column,String var){
        if(flag){
            return like(column,var);
        }
        return this;
    }
    public SelectBuilder like(String column,String var){
        sql.WHERE(column + " like '%"+var+"%'");
        return this;
    }
    public SelectBuilder or(Boolean flag,String column,String var){
        if(flag){
            return or(column,var);
        }
        return this;
    }
    public SelectBuilder or(String column,String var){
        sql.OR().WHERE(column+"= '" + var+"'");
        return this;
    }
    public SelectBuilder likeLeft(String column,String var){
        sql.WHERE(column + " like '%"+var+"'");
        return this;
    }
    public SelectBuilder likeRight(String column,String var){
        sql.WHERE(column + " like '"+var+"%'");
        return this;
    }

    public SelectBuilder between(String column,Date startTime,Date endTime){
        sql.WHERE(column +" between '"+DateFormatUtils.format(startTime,"yyyy-MM-dd")
                +"' and '"+DateFormatUtils.format(endTime,"yyyy-MM-dd")+"'");
        return this;
    }

    public SelectBuilder between(Boolean flag,String column,Date startTime,Date endTime){
        if(flag){
            return between(column,startTime,endTime);
        }
        return this;
    }

    public SelectBuilder in(Boolean flag,String column,String... vars){
        if(flag){
            return in(column,vars);
        }
        return this;
    }

    public SelectBuilder in(Boolean flag,String column,List<String> list){
        if(flag){
            return in(column,list);
        }
        return this;
    }

    public SelectBuilder in(String column,String... vars){
        StringBuilder stringBuffer = new StringBuilder();
        for (String var : vars) {
            stringBuffer.append("'").append(var).append("',");
        }
        sql.WHERE(column +" in ("+stringBuffer.substring(0,stringBuffer.lastIndexOf(","))+")");
        return this;
    }

    public SelectBuilder in(String column,List<String> list){
        StringBuilder stringBuffer = new StringBuilder();
        for (String var : list) {
            stringBuffer.append("'").append(var).append("',");
        }
        sql.WHERE(column +" in ("+stringBuffer.substring(0,stringBuffer.lastIndexOf(","))+")");
        return this;
    }
    public SelectBuilder notIn(Boolean flag,String column,List<String> list){
        if(flag){
            return notIn(column,list);
        }
        return this;
    }
    public SelectBuilder notIn(String column,List<String> list){
        StringBuilder stringBuffer = new StringBuilder();
        for (String var : list) {
            stringBuffer.append("'").append(var).append("',");
        }
        sql.WHERE(column +" not in ("+stringBuffer.substring(0,stringBuffer.lastIndexOf(","))+")");
        return this;
    }

    public SelectBuilder gt(String column,Object var){
        if (var.getClass().isInstance(new Date())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var = sdf.format(var);
        }
        sql.WHERE(column+"> '" + var+"'");
        return this;
    }
    public SelectBuilder lt(String column,Object var){
        if (var.getClass().isInstance(new Date())){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            var = sdf.format(var);
        }
        sql.WHERE(column+"< '" + var+"'");
        return this;
    }

    public SelectBuilder distinct(String column){
        sql.SELECT_DISTINCT(column);
        return this;
    }

    public SelectBuilder distinct(String... columns){
        sql.SELECT_DISTINCT(columns);
        return this;
    }

    public SelectBuilder group(String... columns){
        sql.GROUP_BY(columns);
        return this;
    }
    public SelectBuilder limit(int length){
        sql.LIMIT(length);
        return this;
    }

    public SelectBuilder limit(int start,int end){
        sql.LIMIT(start+","+end);
        return this;
    }
    public SelectBuilder limit(long start,long end){
        sql.LIMIT(start + "," + end);
        return this;
    }

    public SelectBuilder asc(String column){
        sql.ORDER_BY(column);
        return this;
    }
    public SelectBuilder desc(String column){
        sql.ORDER_BY(column+" desc");
        return this;
    }
    public int count(){
        return mapper.count(this.toString());
    }

    public List<JSONObject> exec(){
        return mapper.exec(this.toString());
    }

    public <T> List<T> exec(Class<T> clazz){
        List<JSONObject> list = mapper.exec(this.toString());
        return list.stream().map(item-> item.toJavaObject(clazz)).collect(Collectors.toList());
    }

    public <T> JSONObject one(Class<T> clazz){
        select(clazz);
        List<JSONObject> list = mapper.exec(this.toString());
        if(list!=null&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public <T> T oneT(Class<T> clazz){
        JSONObject result=one(clazz);
        if(result!=null){
            return result.toJavaObject(clazz);
        }
        return null;
    }

    public <T> List<String> listString(Class<T> clazz){
        select(clazz);
        return mapper.execListString(this.toString());
    }

    public <T> List<JSONObject> listJson(Class<T> clazz){
        select(clazz);
        List<JSONObject> list = mapper.exec(this.toString());
        return list;
    }


    public <T> List<T> list(Class<T> clazz){
        select(clazz);
        List<JSONObject> list = mapper.exec(this.toString());
        return list.stream().map(item-> item.toJavaObject(clazz)).collect(Collectors.toList());
    }

    public <T> Page<T> page(Page<T> page, Class<T> clazz){
        select(clazz);
        page.setTotal(mapper.count(this.toString()));
        this.limit((page.current-1)*page.size,page.size);
        List<JSONObject> list = mapper.exec(this.toString());
        page.setRecords(list.stream().map(item->item.toJavaObject(clazz)).collect(Collectors.toList()));
        return page;
    }

    public String toString(){

        return this.sql.toString();
    }
}
