package com.ylhl.phlab.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.Excel;
import com.ylhl.phlab.annotation.ExcelField;
import com.ylhl.phlab.service.BatchService;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.*;


public class ExcelUtils {

    public static <T> JSONObject getTitles(Class<T> clazz) {
        JSONObject res = new JSONObject();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ExcelField provider = field.getAnnotation(ExcelField.class);
            String fieldName = provider != null ? provider.alias() : field.getName();
            res.put(fieldName, field.getName());
        }
        return res;
    }


    public static String getCell(Row row, int i) {
        Cell cell = row.getCell(i);
        if(cell!=null){
            if (cell.getCellType().equals(CellType.NUMERIC)) {
                return String.valueOf(cell.getNumericCellValue());
            }
            if (cell.getCellType().equals(CellType.STRING)) {
                return cell.getStringCellValue();
            }
        }
        return "";
    }

    public static String getCell(Cell cell) {
        if (cell.getCellType().equals(CellType.NUMERIC)) {
            return String.valueOf(cell.getNumericCellValue());
        }
        if (cell.getCellType().equals(CellType.STRING)) {
            return cell.getStringCellValue();
        }
        return "";
    }

    public static void writeWorkbook(HttpServletResponse response, Workbook wb, String fileName) {
        try (OutputStream out = response.getOutputStream()) {
            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/vnd.ms-excel; charset=utf-8");
            response.setHeader("Content-disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
            wb.write(out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<JSONObject> excel(BatchService batchService, Class cls, @RequestBody MultipartFile file) throws IOException {
        JSONObject res = new JSONObject();
        Workbook wb = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = wb.getSheetAt(0);
        Row title = sheet.getRow(1);
        Iterator<Cell> cells = title.cellIterator();
        JSONObject titles = new JSONObject();
        JSONObject beanTitle = setTitles(cls);
        while (cells.hasNext()) {
            Cell cell = cells.next();
            titles.put(beanTitle.getString(ExcelUtils.getCell(cell)), cell.getColumnIndex());
        }
        Iterator<Row> iterator = sheet.rowIterator();
        List<JSONObject> list = new ArrayList<>();

        while (iterator.hasNext()) {
            Row row = iterator.next();
            if (row.getRowNum() > 1) {
                JSONObject data = new JSONObject();
                titles.forEach((key, value) -> {
                    data.put(key, ExcelUtils.getCell(row, (Integer) value));
                });
                Set<String> strings = data.keySet();
                Boolean flag = false;
                for (String string : strings) {
                    if (ObjectUtil.isNotNull(data.get(string)) && StringUtils.isNotBlank((String) data.get(string))) {
                        flag = true;
                    }
                }
                if (flag == true) {
                    BeanUtil.toBean(data, cls);
                    list.add(data);
                }
            }
            //清理数据
            if (list.size() > 1000) {
                //batchService.batch(list);
                list.clear();
            }
        }
        res.put("list", list);
        return list;
    }

    public static <T> JSONObject setTitles(Class<T> clazz) {
        JSONObject res = new JSONObject();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ExcelField provider = field.getAnnotation(ExcelField.class);
            String fieldName = provider != null ? provider.value() : field.getName();
            res.put(fieldName, field.getName());
        }
        return res;
    }

    public static List<JSONObject> execToData(MultipartFile file, BatchService batchService, Class clazz, String id) {
        List<JSONObject> errorList = new ArrayList<>();
        try {
            Workbook wb = new XSSFWorkbook(file.getInputStream());
            Excel excel = AnnotationUtils.findAnnotation(clazz, Excel.class);
            Sheet sheet = null;
            Row title = null;
            int dataRowNum = 1;
            if (excel != null && StringUtils.isNotBlank(excel.value())) {
                sheet = wb.getSheet(excel.value());
                title = sheet.getRow(excel.title());
                dataRowNum = excel.data();
            } else {
                sheet = wb.getSheetAt(0);
                title = sheet.getRow(0);

            }
            AssertUtil.isNull(sheet, "请按要求上传");

            Iterator<Cell> cells = title.cellIterator();
            JSONObject titles = new JSONObject();
            JSONObject beanTitle = setTitles(clazz);
            while (cells.hasNext()) {
                Cell cell = cells.next();
                titles.put(beanTitle.getString(ExcelUtils.getCell(cell)), cell.getColumnIndex());
            }
            Iterator<Row> iterator = sheet.rowIterator();
            List<JSONObject> list = new ArrayList<>();

            while (iterator.hasNext()) {
                Row row = iterator.next();
                if (row.getRowNum() < dataRowNum) {
                    continue;
                }
                JSONObject data = new JSONObject();
                titles.forEach((key, value) -> data.put(key, ExcelUtils.getCell(row, (Integer) value)));
                ValidatedUtils.check(clazz,data);
                if (data.containsKey("errorMassage")) {
                    errorList.add(data);
                } else {
                    list.add(data);
                }
                //清理数据
                if (list.size() > 1000) {
                    batchService.batch(id, list);
                    list.clear();
                }
            }
            if (list.size() > 0) {
                batchService.batch(id, list);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorList;
    }


    public static void dataToExcel(Workbook wb, Object obj, List infos) {
        Sheet sheet = wb.createSheet();
        Row title = sheet.createRow(0);
        Field[] fields = obj.getClass().getDeclaredFields();
        JSONObject data = new JSONObject();
        for (int i = 0; i < fields.length; i++) {
            ExcelField excelField = fields[i].getAnnotation(ExcelField.class);
            if (excelField != null) {
                title.createCell(i).setCellValue(excelField.alias());
                data.put(fields[i].getName(), i);
            }
        }
        for (int i = 0; i < infos.size(); i++) {
            Row row = sheet.createRow(i + 1);
            Field[] fields1 = infos.get(i).getClass().getDeclaredFields();
            for (Field field : fields1) {
                field.setAccessible(true);
                ExcelField excelField = field.getAnnotation(ExcelField.class);
                if (excelField != null) {
                    try {
                        row.createCell(data.getIntValue(field.getName())).setCellValue(String.valueOf(field.get(infos.get(i))));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
