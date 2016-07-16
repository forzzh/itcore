
package www.core.com.config;

import javax.servlet.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(3)
//spring DispatcherServlet的配置,其它servlet和监听器等需要额外声明，用@Order注解设定启动顺序
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  /*
	  * DispatcherServlet的映射路径
	  */
  
  @Override
protected String[] getServletMappings() {
      return new String[]{"/"};
  }

  /*
	  * 应用上下文，除web部分
	  */
  @Override
@SuppressWarnings({ "unchecked", "rawtypes" })
  
  protected Class[] getRootConfigClasses() {
      //加载配置文件类，这里与上面的xml配置是对应的，需要使用@Configuration注解进行标注，稍后介绍
      return new Class[] {};
  }

  /*
	  * web上下文
	  */
  @Override
@SuppressWarnings({ "unchecked", "rawtypes" })
  
  protected Class[] getServletConfigClasses() {
      return new Class[] {MvcConfig.class,AppConfig.class};
  }

  /*
	  * 注册过滤器，映射路径与DispatcherServlet一致，路径不一致的过滤器需要注册到另外的WebApplicationInitializer中
	  */
  
  @Override
protected Filter[] getServletFilters() {
      CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
      characterEncodingFilter.setEncoding("UTF-8");
      characterEncodingFilter.setForceEncoding(true);
      return new Filter[] {characterEncodingFilter};
  } 	

}
