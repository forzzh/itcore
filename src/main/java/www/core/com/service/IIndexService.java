package www.core.com.service;

import java.util.Map;

import www.core.com.pojo.Message;
import www.core.com.pojo.User;

public interface IIndexService {
	
	public Map index(User user);

	/**
	 * Ö÷Ò³ËÑË÷
	 * @param search
	 * @return
	 */
	public Message indexSearch(String search);
}
