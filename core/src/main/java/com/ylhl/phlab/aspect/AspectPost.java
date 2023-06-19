package com.ylhl.phlab.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.ReturnConstants;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;

/**
 * FeignClient出参处理
 * RestController出参处理
 * @author zhengyq
 */
@Aspect
@Component
@Slf4j
public class AspectPost {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void post() {}


    @AfterReturning(value = "post()", returning = "data")
    public void AfterReturning(JoinPoint point,JSONObject data) {
        log.info("出参：{}",data);
        MethodSignature joinPointObject = (MethodSignature) point.getSignature();
        Method method= joinPointObject.getMethod();
        RestController controller = AnnotationUtils.findAnnotation(method.getDeclaringClass(),RestController.class);
        if(controller!=null&&data!=null){
            JSONObject bean = new JSONObject();
            bean.putAll(data);
            data.clear();
            data.put("data",bean);
            data.putIfAbsent("code", ReturnConstants.SUCCESS);
            data.putIfAbsent("message", "成功");
        }
    }

}
