package www.core.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
//����ע���������ʹ��CGLib����
@EnableTransactionManagement(proxyTargetClass = true)
public class UploadConfig {

	
	@Bean(name="multipartResolver")
	public CommonsMultipartResolver getCommonsMultipartResolver(){
		CommonsMultipartResolver common = new CommonsMultipartResolver();
		common.setMaxUploadSize(1024*10000);//10M
		return common;
	}
	
}
