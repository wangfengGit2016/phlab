package com.ylhl.phlab.utils;

import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.domain.SysUserInfo;
import com.ylhl.phlab.mapper.CoreBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FieldUtil {
    public static String userId;
    public static String userName;
    private static void getUser(){
        userId = (String) StpUtil.getLoginId();
        SysUserInfo user = CoreBuilder.select().eq("id", userId).oneT(SysUserInfo.class);
        userName = user.getUserName();
    }

    /**
     * 添加创建人id，创建人name
     */
    public static void setInsert(Object obj){
        setUpdate(obj);
        setFieldValue(obj, "createId", userId);
        setFieldValue(obj, "createName", userName);
    }
    /**
     * 更新人id，更新人name
     */
    public static void setUpdate(Object obj){
        setFieldValue(obj, "updateId", userId);
        setFieldValue(obj, "updateName", userName);
    }

    /**
     * 添加角色id，删除人id，删除人name
     */
    public static void setDelete(Object obj){
        setUpdate(obj);
        setFieldValue(obj, "deleteTime", new Date());
        setFieldValue(obj, "deleteId", userId);
        setFieldValue(obj, "deleteName", userName);
    }

    /**
     * 给对象的指定字段赋String值
     */
    private static void setFieldValue(Object obj, String field, String value){
        // 获取对象的Class对象
        Class<?> clazz = obj.getClass();
        // 获取对象的roleId属性
        Field Nfield;
        try {
            Nfield = clazz.getDeclaredField(field);
            Nfield.setAccessible(true);
            Nfield.set(obj, value);
        } catch (Exception ignored) {
            // 忽略设置失败
            ignored.getMessage();
        }
    }

    /**
     * 给对象的指定字段赋Integer值
     */
    private static void setFieldValue(Object obj, String field, Integer value){
        // 获取对象的Class对象
        Class<?> clazz = obj.getClass();
        // 获取对象的roleId属性
        Field Nfield;
        try {
            Nfield = clazz.getDeclaredField(field);
            Nfield.setAccessible(true);
            Nfield.set(obj, value);
        } catch (Exception ignored) {
            // 忽略设置失败
            ignored.getMessage();
        }
    }

    private static void setFieldValue(Object obj, String field, Date value){
        // 获取对象的Class对象
        Class<?> clazz = obj.getClass();
        // 获取对象的roleId属性
        Field Nfield;
        try {
            Nfield = clazz.getDeclaredField(field);
            Nfield.setAccessible(true);
            Nfield.set(obj, value);
        } catch (Exception ignored) {
            // 忽略设置失败
            ignored.getMessage();
        }
    }
}
