package com.ylhl.phlab.mapper;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CoreMapper {

    @InsertProvider(type= CoreProvider.class, method="insertSelective")
    int insertSelective(Object record);

    @DeleteProvider(type= CoreProvider.class, method="deleteSelective")
    void deleteSelective(Object record);

    @UpdateProvider(type= CoreProvider.class, method="updateSelective")
    void updateSelective(Object record);

    @SelectProvider(type= CoreProvider.class, method="execObject")
    List<JSONObject> execObject(JSONObject data);

    @SelectProvider(type= CoreProvider.class, method="countObject")
    int countObject(JSONObject data);

    @SelectProvider(type= CoreProvider.class, method="exec")
    List<JSONObject> exec(String sql);

    @SelectProvider(type= CoreProvider.class, method="execObject")
    List<String> execListString(JSONObject data);

    @SelectProvider(type= CoreProvider.class, method="count")
    int count(String sql);

    @Select({"show tables"})
    List<String> showTables();

    @Select({"SELECT TABLE_NAME, TABLE_COMMENT FROM information_schema.`TABLES` WHERE TABLE_SCHEMA = '${database}'"})
    List<JSONObject> showTableInfo(@Param("database") String database);

    @Select({"show full columns from ${tableName}"})
    List<JSONObject> showColumns(@Param("tableName") String tableName);
}
