package www.core.com.service;

import www.core.com.pojo.Message;
import www.core.com.pojo.PubSignLikeList;

public interface IPubSignLikeListService {
	/**
	 * ����
	 * msg.status�����Ƿ�ɹ���״̬
	 * @param user ˭����
	 * @param publishSign ���ĸ�
	 * @return
	 */
	public Message<PubSignLikeList> likeIt(Integer userID,Integer publishSignID);
}
