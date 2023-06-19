package com.ylhl.phlab.exception;

import cn.dev33.satoken.exception.NotLoginException;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.consts.ReturnConstants;
import com.ylhl.phlab.utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(Exception.class)
    public JSONObject handleException(Exception e){
        e.printStackTrace();
        log.info(e.getMessage());
        JSONObject data = new JSONObject();
        data.put("code",ReturnConstants.ERROR);
        data.put("message",e.getMessage()!=null?e.getMessage():e.getCause().getMessage());
        return data;
    }
    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public JSONObject handleException(DataIntegrityViolationException e){
        log.info(e.getCause().getMessage());
        JSONObject data = new JSONObject();
        data.put("code",ReturnConstants.ERROR);
        data.put("message",e.getCause().getMessage());
        String message = e.getCause().getMessage();
        if(e.getCause().getMessage().startsWith("Data truncation")){
            if(message.contains("Data too long")&&message.contains("'")){
                data.put("message",ClassUtils.humpToLine2(message.substring(message.indexOf("'")+1,message.lastIndexOf("'")))+"字段过长");
            }
        }
        return data;
    }
    @ResponseBody
    @ExceptionHandler(NullPointerException.class)
    public JSONObject handleException(NullPointerException e){
        e.printStackTrace();
        log.info(e.getMessage());
        JSONObject data = new JSONObject();
        data.put("code",ReturnConstants.ERROR);
        data.put("message","系统异常");
        return data;
    }
    @ResponseBody
    @ExceptionHandler(InvocationTargetException.class)
    public JSONObject handleException(InvocationTargetException e){
        log.info(e.getMessage());
        JSONObject data = new JSONObject();
        data.put("code",ReturnConstants.ERROR);
        data.put("message",e.getMessage()!=null?e.getMessage():e.getCause().getMessage());
        return data;
    }

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public JSONObject handleException(ServiceException e){
        log.info(e.getMessage());
        JSONObject data = new JSONObject();
        data.put("code",e.getCode());
        data.put("message",e.getMessage());
        return data;
    }

    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public JSONObject handleException(IllegalArgumentException e){
        log.info(e.getMessage());
        JSONObject data = new JSONObject();
        data.put("code",ReturnConstants.ERROR);
        data.put("message",e.getMessage());
        return data;
    }

    @ResponseBody
    @ExceptionHandler(IllegalAccessException.class)
    public JSONObject handleException(IllegalAccessException e){
        log.info(e.getMessage());
        JSONObject data = new JSONObject();
        data.put("code",ReturnConstants.ERROR);
        data.put("message",e.getMessage());
        return data;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public JSONObject handleException(MethodArgumentNotValidException e) {
        JSONObject res = new JSONObject();
        BindingResult bindingResult = e.getBindingResult();
        log.error("请求参数错误", Objects.requireNonNull(bindingResult.getTarget()));
        ObjectError objectError = bindingResult.getAllErrors().get(0);
        String defaultMessage = objectError.getDefaultMessage();
        res.put("code",ReturnConstants.ERROR);
        res.put("message",defaultMessage);
        return res;
    }

    @ResponseBody
    @ExceptionHandler(NotLoginException.class)
    public JSONObject handleException(NotLoginException e){
        log.info(e.getMessage());
        JSONObject data = new JSONObject();
        data.put("code", ReturnConstants.NO_LOGIN);
        data.put("message",e.getMessage());
        return data;
    }
}
