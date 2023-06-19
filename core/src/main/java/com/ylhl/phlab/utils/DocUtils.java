package com.ylhl.phlab.utils;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.ylhl.phlab.enums.DataTypeEnum;
import com.ylhl.phlab.mapper.CoreMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class DocUtils {

    public static void writeDoc(HttpServletResponse response, XWPFDocument document, String fileName) {
        try(OutputStream out=response.getOutputStream()) {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/msword; charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename="+ URLEncoder.encode(fileName,"UTF-8"));
            document.write(out);
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void buildObjectApi(XWPFDocument document){
        XWPFParagraph t4=document.createParagraph();
        t4.setStyle("2");
        t4.createRun().setText("四 接口设计");
        Map<String,Object> map= SpringUtil.getApplicationContext().getBeansWithAnnotation(Api.class);
        final int[] i = {1};
        map.forEach((k,v)->{
            Class<?> clazz = v.getClass();
            Api api = AnnotationUtils.findAnnotation(clazz,Api.class);
            if (api!=null){
                XWPFParagraph t4x=document.createParagraph();
                t4x.setStyle("3");
                t4x.createRun().setText("4."+ i[0] +" "+api.description());
                System.out.println(api.description());
                RequestMapping requestMapping = AnnotationUtils.findAnnotation(clazz, RequestMapping.class);
                int j=1;
                for(Method method:clazz.getDeclaredMethods()){
                    ApiOperation operation=AnnotationUtils.findAnnotation(method,ApiOperation.class);
                    if(operation==null||operation.hidden()){
                        continue;
                    }
                    XWPFParagraph t4x1=document.createParagraph();
                    t4x1.setStyle("4");
                    t4x1.createRun().setText("4."+i[0]+"."+j+" "+operation.value());
                    PostMapping postMapping=AnnotationUtils.findAnnotation(method,PostMapping.class);
                    document.createParagraph().createRun().setText("业务："+operation.value());
                    document.createParagraph().createRun().setText("接口地址："+requestMapping.value()[0]+postMapping.value()[0]);
                    document.createParagraph().createRun().setText("入参：");

                    XWPFTable table=document.createTable(1,3);
                    XWPFTableRow title41 = table.getRow(0);
                    title41.getCell(0).setText("字段名");
                    title41.getCell(1).setText("字段类型");
                    title41.getCell(2).setText("说明");
                    Class[] parameterTypes=method.getParameterTypes();
                    for(Class cls:parameterTypes){
                        Field[] fields =cls.getDeclaredFields();
                        for(Field field:fields){
                            ApiModelProperty comment=field.getAnnotation(ApiModelProperty.class);
                            if(comment!=null&& Lists.newArrayList(comment.reference().split(",")).contains(method.getName())){
                                XWPFTableRow row = table.createRow();
                                row.getCell(0).setText(field.getName());
                                row.getCell(1).setText(field.getType().getSimpleName());
                                row.getCell(2).setText(comment!=null?comment.value():"");
                            }

                        }
                    }

                    document.createParagraph().createRun().setText("出参：无");
                    j++;
                }
            }
            i[0]++;
        });
    }

    public static void buildDataDesign(XWPFDocument document,List<JSONObject> tables,CoreMapper coreMapper){
        //数据库设计
        XWPFParagraph t5=document.createParagraph();
        t5.setStyle("2");
        t5.createRun().setText("五 数据库设计");

        XWPFParagraph t51=document.createParagraph();
        t51.setStyle("3");
        t51.createRun().setText("5.1 库表总览");

        XWPFTable table=document.createTable(1,2);
        XWPFTableRow title = table.getRow(0);
        title.getCell(0).setText("表名");
        title.getCell(1).setText("说明");
        tables.forEach(item->{
            XWPFTableRow row = table.createRow();
            row.getCell(0).setText(item.getString("TABLE_NAME"));
            row.getCell(1).setText(item.getString("TABLE_COMMENT"));
        });

        XWPFParagraph t52=document.createParagraph();
        t52.setStyle("3");
        t52.createRun().setText("5.2 表字段介绍");
        int i =0;
        for(JSONObject item:tables){
            XWPFParagraph t52x=document.createParagraph();
            t52x.setStyle("4");
            t52x.createRun().setText("5.2."+(i+1)+" "+item.getString("TABLE_COMMENT"));
            List<JSONObject> columns=coreMapper.showColumns(item.getString("TABLE_NAME"));
            XWPFTable tablex=document.createTable(1,4);
            XWPFTableRow titlex = tablex.getRow(0);
            titlex.getCell(0).setText("字段名");
            titlex.getCell(1).setText("字段类型");
            titlex.getCell(2).setText("主键");
            titlex.getCell(3).setText("说明");
            columns.forEach(column->{
                XWPFTableRow row = tablex.createRow();
                row.getCell(0).setText(column.getString("Field"));
                String code = column.getString("Type").replaceAll("\\(.*\\)", "");;
                row.getCell(1).setText(DataTypeEnum.getEnum(code).getType());
                row.getCell(2).setText(column.getString("Key").contains("PRI")?"是":"");
                row.getCell(3).setText(column.getString("Comment"));
            });
            i++;
        }
    }
}
