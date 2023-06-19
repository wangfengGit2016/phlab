package com.ylhl.phlab.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class PathFilter implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		InterceptorRegistration registration = registry.addInterceptor(new LoginFilter());
		registration.addPathPatterns("/**");
		registration.excludePathPatterns(
				"/notify/**",
				"/sys/comm/**",
				"/sys/user/login",
				"/sys/file/**","/ews/warn/info","/mobile/ram/organ/**","/esr/user/login",
				"/swagger-ui.html","/webjars/**","/swagger-resources/**","/v2/**");
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("*.txt").addResourceLocations("classpath:/static/other/");
		registry.addResourceHandler("/data/**").addResourceLocations("file:/data/");
		registry.addResourceHandler("/swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
