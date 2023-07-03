package com.ylhl.phlab.mapper;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.config.DataConfig;
import com.ylhl.phlab.domain.SysUserInfo;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

public class InsertBuilder {

    CoreMapper mapper;
    DataConfig dataConfig;
    SqlSessionTemplate sqlSessionTemplate;

    InsertBuilder(){
        this.mapper=SpringUtil.getBean(CoreMapper.class);
        this.dataConfig=SpringUtil.getBean(DataConfig.class);
        this.sqlSessionTemplate=SpringUtil.getBean(SqlSessionTemplate.class);
    }

    public void initData(Object obj){
        String userId = (String) StpUtil.getLoginId();
        SysUserInfo user = CoreBuilder.select().eq("id", userId).oneT(SysUserInfo.class);
        String userName = user.getUserName();
        for(Field field:obj.getClass().getDeclaredFields()){
            field.setAccessible(true);
            try {
                if(field.get(obj) == null){
                    if (field.getName().equals(dataConfig.getCreateTime())){
                        field.set(obj, new Date());
                    }
                    if (field.getName().equals(dataConfig.getUpdateTime())) {
                        field.set(obj, new Date());
                    }
                    if (field.getName().equals(dataConfig.getCreateId())) {
                        field.set(obj, userId);
                    }
                    if (field.getName().equals(dataConfig.getUpdateId())) {
                        field.set(obj, userId);
                    }
                    if (field.getName().equals(dataConfig.getCreateName())) {
                        field.set(obj, userName);
                    }
                    if (field.getName().equals(dataConfig.getUpdateName())) {
                        field.set(obj, userName);
                    }
                }
            } catch(IllegalAccessException e){
                e.printStackTrace();
            }
        }
    }

    public  int save(Object obj){
        initData(obj);
        return mapper.insertSelective(obj);
    }

    public  int saveBatch(List<JSONObject> list,Class cls){
        SqlSession sqlSession =sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH,false);
        try {
            CoreMapper coreMapper = sqlSession.getMapper(CoreMapper.class);
            for(JSONObject item : list){
                Object obj=item.toJavaObject(cls);
                initData(obj);
                coreMapper.insertSelective(obj);
            }
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
        }finally {
            sqlSession.close();
        }
        return 0;
    }

    public  int saveBatch(List<JSONObject> list,Object obj){
        return saveBatch(list,obj.getClass());
    }

}
