package www.core.com.service.impl;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Message;
import www.core.com.pojo.PubSignLikeList;
import www.core.com.pojo.PublishSign;
import www.core.com.pojo.User;
import www.core.com.service.IPubSignLikeListService;
import www.core.com.service.IPublicSignService;
import www.core.com.service.IUserService;

@Transactional
@Service("pubSignLikeListService")
public class PubSignLikeListServiceImpl implements IPubSignLikeListService {

	@Resource(name = "hibernateDAO")
	BaseDAO<PubSignLikeList> hibernateDAO;

	@Autowired
	IUserService userService;

	@Autowired
	IPublicSignService publicSignService;

	@Override
	public Message<PubSignLikeList> likeIt(Integer userID, Integer publishSignID) {
		Message<PubSignLikeList> publishSignMsg;

		User user = userService.userQueryById(userID);
		PublishSign publishSign = publicSignService.pubSignExistenceById(publishSignID);

		if (user == null || publishSign == null) {
			publishSignMsg = new Message<>();
			publishSignMsg.setMsg("点赞失败");
			publishSignMsg.setStatus(false);
			return publishSignMsg;
		}
		
		PubSignLikeList result ;




		publishSignMsg = new Message<>();
		publishSignMsg.setMsg("点赞成功");
		publishSignMsg.setStatus(true);
		return null;
	}

}
