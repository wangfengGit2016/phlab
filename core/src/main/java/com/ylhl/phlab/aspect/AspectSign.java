package com.ylhl.phlab.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.utils.AssertUtil;
import com.ylhl.phlab.utils.SignUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * FeignClient出参处理
 * RestController出参处理
 * @author zhengyq
 */
@Aspect
@Component
@Slf4j
public class AspectSign {


    @Pointcut("@annotation(com.ylhl.phlab.annotation.Sign)")
    public void sign() {}

    @Before(value = "sign()",argNames = "point")
    public void preHandle(JoinPoint point){
        log.info("接口入参:"+ JSONObject.toJSONString(point.getArgs()[0]));
        JSONObject param= (JSONObject) JSONObject.toJSON(point.getArgs()[0]);
        AssertUtil.isTrue(!param.containsKey("sign"),"校验失败，签名不能为空");
        AssertUtil.isTrue(!param.getString("sign").equals(SignUtil.sign(param,"ZFB0020")),"验签失败");
    }
}
