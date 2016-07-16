package www.core.com.config;

import java.util.Properties;

import javax.servlet.ServletContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import www.core.com.filter.Dispatcher;
import www.core.com.filter.ExceptionClass;
@Configuration  
@EnableWebMvc
@ComponentScan(basePackages = "www",  includeFilters = {  
        @ComponentScan.Filter(type = FilterType.ANNOTATION)  
})  
public class MvcConfig extends WebMvcConfigurationSupport {
	

	@Bean(name="freemarkerConfig")
	public FreeMarkerConfigurer getFreeMarkerConfigurer(){
		FreeMarkerConfigurer bean = new FreeMarkerConfigurer();
		bean.setTemplateLoaderPath("/WEB-INF/");
		bean.setDefaultEncoding("gbk");
		return bean;
	}
	@Bean
	public FreeMarkerViewResolver getFreeMarkerViewResolver(){
		FreeMarkerViewResolver bean = new FreeMarkerViewResolver();
		bean.setViewClass(FreeMarkerView.class);
//		bean.setViewNames(new String[]{"*.ftl"});
		bean.setContentType("text/html; charset=utf-8");
		bean.setCache(true);
		bean.setSuffix(".ftl");
		
		bean.setExposeRequestAttributes(true);
		bean.setExposeSessionAttributes(true);
		bean.setRequestContextAttribute("request");
		bean.setExposeSpringMacroHelpers(true);
//		<!-- 此变量值为pageContext.request, 页面使用方法：rc.contextPath -->  
	
		return bean;
	}
	@Bean(name="exceptionResolver")
	public ExceptionClass getExceptionClass(){
		ExceptionClass exception = new ExceptionClass();
		Properties properties = new Properties();
		properties.setProperty("java.lang.Exception", "");  
		exception.setExceptionMappings(properties);
//		properties = new Properties();
//		properties.setProperty("errors/error", "500");  
//		properties.setProperty("errors/404", "404");  
//		exception.setStatusCodes(properties);
		return exception;
	}
	
}
