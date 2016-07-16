package www.core.com.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;

@Configuration 
@ComponentScan(basePackages = "www", excludeFilters = { @ComponentScan.Filter(type = FilterType.ANNOTATION) })  
@EnableAspectJAutoProxy(proxyTargetClass=true)  
public class AppConfig {  
}  
