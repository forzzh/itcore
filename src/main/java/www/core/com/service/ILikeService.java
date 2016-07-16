package www.core.com.service;

import www.core.com.pojo.Like;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;

public interface ILikeService {

	public Message<Like> like(Like like);

	/**
	 * 用户是否点赞
	 * @param id
	 * @param user
	 * @return
	 */
	public Message<Boolean> islike(Integer id, User user);
}
