package com.ylhl.phlab.utils;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.enums.DataTypeEnum;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

/**
 * @author zhengyq
 * @date 2022/1/26 17:47
 */
public class ClassUtils {

    public static <T> String getFunctionName(Function<T,?> function){
        try {
            Method declaredMethods = function.getClass().getDeclaredMethod("writeReplace");
            System.out.println("=========================");
            System.out.println("========================="+declaredMethods.getName());

            System.out.println("=========================");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return "";

    }


    public static String humpToLine(String str) {
        StringBuilder ss = new StringBuilder(str);
        while (ss.indexOf("_")>-1){
            int i = ss.indexOf("_");
            ss.replace(i,i+2, String.valueOf(ss.charAt(i+1)).toUpperCase(Locale.ROOT));
        }
        ss.replace(0,1,String.valueOf(ss.charAt(0)).toUpperCase(Locale.ROOT));
        return ss.toString();
    }
    public static String humpToLine2(String str) {
        StringBuilder ss = new StringBuilder(str);
        while (ss.indexOf("_")>-1){
            int i = ss.indexOf("_");
            ss.replace(i,i+2, String.valueOf(ss.charAt(i+1)).toUpperCase(Locale.ROOT));
        }
        return ss.toString();
    }

    public static void dataToFile(String data, File file){
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileCopyUtils.copy(data.getBytes(StandardCharsets.UTF_8), file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void build(String module,String basePackage,String className, String tableName, List<JSONObject> columns){

        String path = System.getProperty("user.dir") +module+".src.main.java.";
        path=(path+basePackage+".domain").replaceAll("\\.","\\\\");
        File file = new File(path, className+".java");

        StringBuilder clazz =new StringBuilder();
        clazz.append("package ").append(basePackage).append(".domain").append(";\r\n\r\n");
        clazz.append("import lombok.Data;\r\n");
        clazz.append("import java.util.Date;\r\n");
        clazz.append("import ").append(basePackage).append(".annotation.Table;\r\n");
        clazz.append("import ").append(basePackage).append(".annotation.TableId;\r\n");
        clazz.append("import ").append(basePackage).append(".annotation.TableField;\r\n");
        clazz.append("import ").append(basePackage).append(".annotation.Comment;\r\n\r\n");

        clazz.append("@Data\r\n");
        clazz.append("@Table(\"").append(tableName).append("\")\r\n");
        clazz.append("public class ").append(className).append(" {");
        clazz.append("\r\n\r\n");
        columns.forEach(column->{
            if(column.getString("Key").contains("PRI")){
                clazz.append("    @TableId\r\n");
            }
            if(column.getString("Field").contains("_")){
                clazz.append("    @TableField(alias = \"").append(column.getString("Field")).append("\")\r\n");
            }
            if(column.containsKey("Comment")){
                clazz.append("    @Comment(\"").append(column.getString("Comment").replaceAll("\\r\\n", " ")).append("\")\r\n");
            }
            clazz.append("    private ");
            String code = column.getString("Type").replaceAll("\\(.*\\)", "");;
            clazz.append(DataTypeEnum.getEnum(code).getType());
            clazz.append(" ");
            clazz.append(humpToLine2(column.getString("Field")));
            clazz.append(";\r\n");
        });
        clazz.append("\r\n");
        clazz.append("}");
        dataToFile(clazz.toString(),file);
    }

    public static void buildService(String module,String basePackage){
        String path = System.getProperty("user.dir")+module +".src.main.java.";
        path=(path+basePackage+".service").replaceAll("\\.","\\\\");
        File file = new File(path, "IService.java");

        StringBuilder clazz =new StringBuilder();
        clazz.append("package ").append(basePackage).append(".service").append(";\r\n\r\n");

        clazz.append("import com.alibaba.fastjson.JSONObject;\r\n");

        clazz.append("public interface IService{");
        clazz.append("\r\n\r\n");

        clazz.append("      JSONObject page(JSONObject data);\n\n");
        clazz.append("      JSONObject list(JSONObject data);\n\n");
        clazz.append("      JSONObject insert(JSONObject data);\n\n");
        clazz.append("      JSONObject delete(JSONObject data);\n\n");
        clazz.append("      JSONObject update(JSONObject data);\n\n");
        clazz.append("      JSONObject detail(JSONObject data);\n");

        clazz.append("\r\n");
        clazz.append("}");
        dataToFile(clazz.toString(),file);
    }

    public static void buildServiceImpl(String module,String basePackage,String objectName, List<JSONObject> columns){
        String className =objectName+"Service";
        String path = System.getProperty("user.dir") +module+".src.main.java.";
        path=(path+basePackage+".service.impl").replaceAll("\\.","\\\\");
        File file = new File(path, className+".java");

        StringBuilder clazz =new StringBuilder();
        clazz.append("package ").append(basePackage).append(".service.impl;\r\n\r\n");

        clazz.append("import cn.hutool.core.bean.BeanUtil;\r\n");
        clazz.append("import java.util.List;\r\n");
        clazz.append("import com.alibaba.fastjson.JSONObject;\r\n");
        clazz.append("import lombok.extern.slf4j.Slf4j;\r\n");
        clazz.append("import ").append(basePackage).append(".service.IService;\r\n");
        clazz.append("import ").append(basePackage).append(".domain.").append(objectName).append(";\r\n");
        clazz.append("import ").append(basePackage).append(".mapper.CoreBuilder;\r\n");
        clazz.append("import ").append(basePackage).append(".mapper.Page;\r\n");
        clazz.append("import org.springframework.stereotype.Service;\r\n\r\n");

        clazz.append("@Slf4j\r\n");
        clazz.append("@Service(\"").append(className).append("\")\r\n");
        clazz.append("public class ").append(className).append("  implements IService{\r\n");
        clazz.append("\r\n");
        //增删改查
        //分页
        clazz.append("      public JSONObject page(JSONObject data) {\r\n");
        clazz.append("          log.info(\"{}\",data);\r\n");
        clazz.append("          Page<").append(objectName).append("> page = new Page<>(data);\n");
        clazz.append("          CoreBuilder.select().page(page,").append(objectName).append(".class);\n");
        clazz.append("          return page.toJson();\n");
        clazz.append("      }\n\n");

        //列表
        clazz.append("      public JSONObject list(JSONObject data) {\r\n");
        clazz.append("          log.info(\"{}\",data);\r\n");
        clazz.append("          JSONObject res =new JSONObject();\n");
        clazz.append("          List<").append(objectName).append("> list=CoreBuilder.select().list(").append(objectName).append(".class);\n");
        clazz.append("          res.put(\"list\", list);\n");
        clazz.append("          return res;\n");
        clazz.append("      }\n\n");

        //插入
        clazz.append("      public JSONObject insert(JSONObject data) {\r\n");
        clazz.append("          log.info(\"{}\",data);\r\n");
        clazz.append("          JSONObject res =new JSONObject();\n");
        clazz.append("          ").append(objectName).append(" bean = BeanUtil.toBean(data,").append(objectName).append(".class);\n");
        clazz.append("          res.put(\"status\",CoreBuilder.insert().save(bean));\n");
        clazz.append("          return res;\n");
        clazz.append("      }\n\n");

        //删除
        clazz.append("      public JSONObject delete(JSONObject data) {\r\n");
        clazz.append("          log.info(\"{}\",data);\r\n");
        clazz.append("          JSONObject res =new JSONObject();\n");
        clazz.append("          CoreBuilder.delete()");
        columns.forEach(item->{
            if(item.getString("Key").contains("PRI")){
                clazz.append(".eq(\"");
                clazz.append(item.getString("Field")).append("\",data.getString(\"");
                clazz.append(humpToLine2(item.getString("Field"))).append("\"))");
            }
        });
        clazz.append(".remove(").append(objectName).append(".class);\n");

        clazz.append("          return res;\n");
        clazz.append("      }\n\n");
        //修改
        clazz.append("      public JSONObject update(JSONObject data) {\r\n");
        clazz.append("          log.info(\"{}\",data);\r\n");
        clazz.append("          JSONObject res =new JSONObject();\n");
        clazz.append("          ").append(objectName).append(" bean = BeanUtil.toBean(data,").append(objectName).append(".class);\n");
        clazz.append("          CoreBuilder.update().edit(bean);\n");
        clazz.append("          return res;\n");
        clazz.append("      }\n\n");
        //查询
        clazz.append("      public JSONObject detail(JSONObject data) {\r\n");
        clazz.append("          log.info(\"{}\",data);\r\n");
        clazz.append("          JSONObject res =new JSONObject();\n");
        clazz.append("          JSONObject bean= CoreBuilder.select()");
        columns.forEach(item->{
            if(item.getString("Key").contains("PRI")){
                clazz.append(".eq(\"");
                clazz.append(item.getString("Field")).append("\",data.getString(\"");
                clazz.append(humpToLine2(item.getString("Field"))).append("\"))");
            }
        });
        clazz.append(".one(").append(objectName).append(".class);\n");
        clazz.append("          res.put(\"data\",bean);\n");
        clazz.append("          return res;\n");
        clazz.append("      }\n");

        clazz.append("\r\n");
        clazz.append("}");
        dataToFile(clazz.toString(),file);
    }


}
