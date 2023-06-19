package com.ylhl.phlab.invoker;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

@Data
public class Service {
	private String name;
	private String method;
	private JSONObject params;
}
