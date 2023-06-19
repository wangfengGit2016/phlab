package com.ylhl.phlab.invoker;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSONObject;
import com.ylhl.phlab.exception.ServiceException;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Method;

public class ServiceInvoker {
	public static JSONObject doService(Service service) {
		JSONObject res = new JSONObject();
		try {
			String className = service.getName();
			String methodName = service.getMethod();
			JSONObject params = service.getParams();
			Object obj = SpringUtil.getBean(className);
			if (obj == null) {
				res.put("code", -1);
				res.put("message", "失败");
				return res;
			}
			if (params == null) {
				Method method = obj.getClass().getMethod(methodName);
				res = (JSONObject) method.invoke(obj);
			} else {
				Method method = obj.getClass().getMethod(methodName, JSONObject.class);
				res = (JSONObject) method.invoke(obj,service.getParams());
			}
		} catch (Exception e) {
			res.put("code", -1);
			res.put("message", "失败");
			throw new ServiceException(StringUtils.isNotBlank(e.getMessage())?e.getMessage():e.getCause().getMessage());
		}
		return res;
	}

}
