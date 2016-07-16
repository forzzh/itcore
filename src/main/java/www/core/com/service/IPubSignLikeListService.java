package www.core.com.service;

import www.core.com.pojo.Message;
import www.core.com.pojo.PubSignLikeList;

public interface IPubSignLikeListService {
	/**
	 * 点赞
	 * msg.status保存是否成功的状态
	 * @param user 谁点赞
	 * @param publishSign 给哪个
	 * @return
	 */
	public Message<PubSignLikeList> likeIt(Integer userID,Integer publishSignID);
}
