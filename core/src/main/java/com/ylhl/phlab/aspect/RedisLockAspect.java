package com.ylhl.phlab.aspect;

import com.ylhl.phlab.annotation.RedisLock;
import com.ylhl.phlab.utils.AssertUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class RedisLockAspect {
    @Resource
    StringRedisTemplate stringRedisTemplate;
    String key = null;


    @Pointcut("@annotation(com.ylhl.phlab.annotation.RedisLock)")
    public void cacheLock() {}

    @Before("cacheLock()")
    public void before(JoinPoint joinPoint) {
        log.info("开始锁校验");
        MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
        Method method = joinPointObject.getMethod();
        RedisLock cacheLock = method.getAnnotation(RedisLock.class);
        Map<String,Object> paramMap = new HashMap<>(0);
        String[] params=joinPointObject.getParameterNames();
        Object[] args =joinPoint.getArgs();
        if(params!=null){
            for(int i=0;i<params.length;i++){
                paramMap.put(params[i],args[i]);
            }
        }

        StringBuilder keyString = new StringBuilder();
        if(StringUtils.isEmpty(cacheLock.params())){
            keyString.append(method.getDeclaringClass().getName());
            keyString.append(".");
            keyString.append(method.getName());
        }else {
            String[] locks = cacheLock.params().trim().split(",");
            log.info(String.valueOf(locks.length));
            if(locks.length>0){
                for (String str:locks){
                    if(StringUtils.isBlank(str)){
                        continue;
                    }
                    Object obj=paramMap.get(str.trim());
                    if(obj!=null){
                        keyString.append(obj);
                        continue;
                    }
                    String[] names = str.trim().split("\\.");
                    if (names.length>0){

                        obj=paramMap.get(names[0]);
                        if(obj!=null&&names.length>1){
                            //目前只设计两级xx.xx,不支持xx.xx.xx
                            keyString.append(getProperty(obj,names[1].trim()));
                            continue;
                        }
                    }
                    keyString.append(str);
                }
            }
        }
        key=keyString.toString();
        //验锁
        AssertUtil.notNull(stringRedisTemplate.opsForValue().get(key),"业务已在处理，请等待完成");
        log.info("加锁:"+key);
        stringRedisTemplate.opsForValue().set(key,"0", cacheLock.timeLock());
    }

    @After("cacheLock()")
    public void after() {
        log.info("移除锁:"+key);
        stringRedisTemplate.delete(key);
    }

    public Object getProperty(Object obj,String name){
        try {
            Field field=obj.getClass().getField(name);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
