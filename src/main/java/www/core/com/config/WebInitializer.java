
package www.core.com.config;

import javax.servlet.Filter;

import org.springframework.core.annotation.Order;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(3)
//spring DispatcherServlet������,����servlet�ͼ���������Ҫ������������@Orderע���趨����˳��
public class WebInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
  /*
	  * DispatcherServlet��ӳ��·��
	  */
  
  @Override
protected String[] getServletMappings() {
      return new String[]{"/"};
  }

  /*
	  * Ӧ�������ģ���web����
	  */
  @Override
@SuppressWarnings({ "unchecked", "rawtypes" })
  
  protected Class[] getRootConfigClasses() {
      //���������ļ��࣬�����������xml�����Ƕ�Ӧ�ģ���Ҫʹ��@Configurationע����б�ע���Ժ����
      return new Class[] {};
  }

  /*
	  * web������
	  */
  @Override
@SuppressWarnings({ "unchecked", "rawtypes" })
  
  protected Class[] getServletConfigClasses() {
      return new Class[] {MvcConfig.class,AppConfig.class};
  }

  /*
	  * ע���������ӳ��·����DispatcherServletһ�£�·����һ�µĹ�������Ҫע�ᵽ�����WebApplicationInitializer��
	  */
  
  @Override
protected Filter[] getServletFilters() {
      CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
      characterEncodingFilter.setEncoding("UTF-8");
      characterEncodingFilter.setForceEncoding(true);
      return new Filter[] {characterEncodingFilter};
  } 	

}
