package com.ylhl.phlab.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.reflect.Field;

public class ValidatedUtils {
    public static  void check(Class clazz,JSONObject data) {
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            field.setAccessible(true);
            NotBlank notBlank = AnnotationUtils.findAnnotation(field,NotBlank.class);
            if (notBlank!=null){
                if(StringUtils.isBlank(data.getString(field.getName()))){
                    data.put("errorMassage",notBlank.message());
                }
            }
            Size size = AnnotationUtils.findAnnotation(field,Size.class);
            if(size!=null){
                if(data.get(field.getName())!=null){
                    String val=data.getString(field.getName());
                    if(val.length()<size.min()||val.length()>size.max()){
                        data.put("errorMassage",size.message());
                    }
                }
            }
        }
    }
}
