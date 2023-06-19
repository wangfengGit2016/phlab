package com.ylhl.phlab.filter;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.annotation.RedisLock;
import com.ylhl.phlab.enums.MethodEnum;
import com.ylhl.phlab.enums.ModuleEnum;
import com.ylhl.phlab.service.IService;
import com.ylhl.phlab.utils.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

@Slf4j
@Component
public class LoginFilter implements HandlerInterceptor {

    @Resource
    StringRedisTemplate stringRedisTemplate;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("权限拦截"+request.getServletPath());
        String ip = HttpUtils.getIp(request);
        log.info("IP拦截"+ HttpUtils.getIp(request));
        MDC.put("request-id", IdUtil.randomUUID());
        StpUtil.checkLogin();

        if(!MethodEnum.getEnum(request.getServletPath()).match(MethodEnum.UNKNOWN.getCode())){
            JSONObject log =new JSONObject();
            log.put("userId",StpUtil.getLoginId());
            log.put("userName",StpUtil.getSession().get("username"));
            log.put("ip",ip);
            log.put("module", ModuleEnum.getModule(request.getServletPath()));
            log.put("content",MethodEnum.getEnum(request.getServletPath()).getMsg()+log.getString("module"));
            IService sysLogInfoService = SpringUtil.getBean("SysLogInfoService");
            sysLogInfoService.insert(log);
        }
        return true;
    }

    @RedisLock()
    public Set<String> getSysUrlList(Long userId) {

        return null;
    }

}
