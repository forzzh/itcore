package www.core.com.service;

import java.util.List;

import www.core.com.Enum.ModelEnum;
import www.core.com.pojo.FoucusTopicorBook;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;

public interface IFocusTopicOrBookService {

	Message focusTopicOrBook(User user,Integer commonId,String type);
	
	/**
	 * ��ѯ�ҵĹ�ע
	 * @param start
	 * @param count
	 * @param user
	 * 		�û�
	 * @param modelEnum
	 * 		����
	 * @return
	 */
	Message<List<FoucusTopicorBook>> queryMyFocusList(Integer start, Integer count, User user, ModelEnum modelEnum);
}
