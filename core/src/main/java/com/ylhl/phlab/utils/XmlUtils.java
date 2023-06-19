package com.ylhl.phlab.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * 格式工具
 *
 * @author zhengyq
 *
 */
public class XmlUtils {

	//普通对象转XML
	public static String toXml(Object obj){
		return toXml(obj,null);
	}
	public static String toXml(Object obj,String name) {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buildXml2(xml,name,obj,true);
		return xml.toString();
	}

	private static void buildXml2(StringBuilder xml, String name, Object obj,boolean flag){
		if(obj!=null){
			if(obj instanceof Map){
				String itemName = flag?name:"map";
				xml.append("<").append(itemName).append(">");
				Map<String,Object> map = (Map) obj;
				map.forEach((k,v)-> buildXml2(xml, k,v,false));
				xml.append("</").append(itemName).append(">");
			}else if(obj instanceof List){
				xml.append("<").append(name).append(">");
				List<Object> list = (List) obj;
				list.forEach(item-> buildXml2(xml,name,item,false));
				xml.append("</").append(name).append(">");
			}else{
				if(obj.getClass().getClassLoader()!=null){
					Class<?> clazz = obj.getClass();
					XmlRootElement rootElement = AnnotationUtils.findAnnotation(clazz,XmlRootElement.class);
					String className =rootElement!=null?rootElement.name():clazz.getSimpleName();
					String nodeName = StringUtils.isNotBlank(name)&&flag?name:className;
					xml.append("<").append(nodeName).append(">");
					Field[] fields=clazz.getDeclaredFields();
					for(Field field:fields){
						XmlElement xmlElement = AnnotationUtils.findAnnotation(field,XmlElement.class);
						field.setAccessible(true);
						try {
							String fieldName = xmlElement!=null?xmlElement.name():field.getName();
							if(field.get(obj)!=null){
								buildXml2(xml, fieldName,field.get(obj),false);
							}
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
					}
					xml.append("</").append(nodeName).append(">");
				}else {
					xml.append("<").append(name).append(">");
					xml.append(obj);
					xml.append("</").append(name).append(">");
				}

			}
		}
	}

	//JSONObject对象转XML
	public static String toXml(JSONObject data) {
		return toXml(data,"xml");
	}
	public static String toXml(JSONObject data,String name) {
		StringBuilder xml = new StringBuilder();
		xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buildXml(xml,name,data);
		return xml.toString();
	}
	private static void buildXml(StringBuilder xml, String name, Object obj){
		if(obj!=null){
			if(obj instanceof Map){
				JSONObject data = (JSONObject) JSONObject.toJSON(obj);
				xml.append("<").append(name).append(">");
				data.keySet().forEach(item-> buildXml(xml,item,data.get(item)));
				xml.append("</").append(name).append(">");
			}else if(obj instanceof List){
				JSONArray json = (JSONArray) JSONArray.toJSON(obj);
				json.forEach(item-> buildXml(xml,name,item));
			}else{
				xml.append("<").append(name).append(">");
				xml.append(obj);
				xml.append("</").append(name).append(">");
			}
		}
	}

}
