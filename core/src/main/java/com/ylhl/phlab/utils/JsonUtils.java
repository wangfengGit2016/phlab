package com.ylhl.phlab.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 格式工具
 *
 * @author zhengyq
 *
 */
public class JsonUtils {
	//xml转JSON
	public static JSONObject toJson(String xml) {
		JSONObject res = new JSONObject();
		Document document= Jsoup.parse(xml);
		buildJson(res,document.body());
		return res;
	}
	private static void buildJson(JSONObject data,Element element ){
		//处理并合并list
		for(Element children :element.children()){
			if(data.containsKey(children.nodeName())){
				//数组节点
				if(children.childrenSize()>0){
					List<JSONObject> list=JsonUtils.getList(data,children.nodeName());
					JSONObject item = new JSONObject();
					buildJson(item,children);
					list.add(item);
					data.put(children.nodeName(),list);
				}else {
					List<String> list= new ArrayList<>();
					list.add(children.text());
					if(data.get(children.nodeName()) instanceof List){
						list.addAll(data.getJSONArray(children.nodeName()).toJavaList(String.class));
					}else {
						list.add(data.getString(children.nodeName()));
					}
					data.put(children.nodeName(),list);
				}
			}else {
				if(children.childrenSize()>0){
					JSONObject item = new JSONObject();
					buildJson(item,children);
					data.put(children.nodeName(),item);
				}else {
					data.put(children.nodeName(),children.text());
				}
			}
		}
	}

	//JSONObject对象获取list值
	public static List<JSONObject> getList(JSONObject data,String key){
		List<JSONObject> list = new ArrayList<>();
		if(data!=null){
			Object val = data.get(key);
			if(val instanceof Map){
				list.add((JSONObject) val);
			}
			if(val instanceof List){
				list.addAll(data.getJSONArray(key).toJavaList(JSONObject.class));
			}
		}
		list.removeAll(Collections.singleton(null));
		return list;
	}
	//对象获取list值
	public static <T> List<T> getList(Object source,String key, Class<T> clazz) {
		List<T> list = new ArrayList<>();
		JSONObject xxJson = (JSONObject) JSONObject.toJSON(source);
		Object object =xxJson.get(key);
		if(object instanceof JSONObject){
			JSONObject json = (JSONObject) JSONObject.toJSON(object);
			list.add(json.toJavaObject(clazz));
		}else if(object instanceof JSONArray){
			JSONArray json = (JSONArray) JSONArray.toJSON(object);
			list.addAll(json.toJavaList(clazz));
		}else if(object instanceof List){
			JSONArray json = (JSONArray) JSONArray.toJSON(object);
			list.addAll(json.toJavaList(clazz));
		}
		return list;
	}


}
