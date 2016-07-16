package www.core.com.filter;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.orm.hibernate4.support.OpenSessionInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.util.Log4jConfigListener;
@Order(1)  
public class CommonInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		 //Log4jConfigListener  
        servletContext.setInitParameter("log4jConfigLocation", "classpath:config/xml/log4J.xml");  
        servletContext.addListener(Log4jConfigListener.class);  
          
     
        OpenSessionInViewFilter hibernateSessionInViewFilter = new OpenSessionInViewFilter();  
        hibernateSessionInViewFilter.setSessionFactoryBeanName("sessionFactory");
        FilterRegistration.Dynamic filterRegistration = servletContext.addFilter(  
                "hibernateFilter", hibernateSessionInViewFilter);  
        filterRegistration.addMappingForUrlPatterns(  
                EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE), false, "/");  
         
       
	}

}
