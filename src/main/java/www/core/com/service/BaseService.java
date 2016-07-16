package www.core.com.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public  class BaseService {
	
	public String getAllName(Class T){
		return StringUtils.replace(T.getName(), ".", "") ;
	}
}
