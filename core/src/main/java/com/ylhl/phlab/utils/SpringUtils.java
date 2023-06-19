package com.ylhl.phlab.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

@Component(value="SpringUtils")
public class SpringUtils implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringUtils.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	//通过name获取 Bean.
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) getApplicationContext().getBean(name);
	}
	//通过name获取 Bean.
	public static <T> Map<String, T> getBeansOfType(Class<T> type) {
		return getApplicationContext().getBeansOfType(type);
	}

	//通过name,以及Annotation返回指定的Bean
	public static Object getBeanForNameAndAnnotation(String name,Class<? extends Annotation> annotationType) {
		return getApplicationContext().getBeansWithAnnotation(annotationType).get(name);
	}
	//通过class获取Bean.
	public static <T> T getBean(Class<T> clazz) {
		return getApplicationContext().getBean(clazz);
	}

	//通过name,以及Clazz返回指定的Bean
	public static <T> T getBean(String name, Class<T> clazz) {
		return getApplicationContext().getBean(name, clazz);
	}

	//通过name,以及Clazz返回指定的Bean
	public static String[] getBeanNamesForAnnotation(Class<? extends Annotation> annotationType) {
		return getApplicationContext().getBeanNamesForAnnotation(annotationType);
	}
}
