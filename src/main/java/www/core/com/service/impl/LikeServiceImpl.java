package www.core.com.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.Enum.LikeType;
import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Book;
import www.core.com.pojo.Like;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.User;
import www.core.com.service.ILikeService;
import www.core.com.service.IUserService;

@Service("likeService")
public class LikeServiceImpl extends AbstractService<Like> implements ILikeService {
	@Resource(name = "hibernateDAO")
	BaseDAO<Like> hibernateDAO;
	@Resource(name = "userService")
	private IUserService userService;

	@Override
	@Transactional
	public Message<Like> like(Like like) {
		Message<Like> message = new Message<Like>();
		User result = userService.userQueryById(like.getUser().getId());
		if (result == null) {
			message.setMsg("²Ù×÷Ê§°Ü");
			message.setStatus(false);
			return message;

		}

//		String hql = "from " + Group.class.getSimpleName() + " where lower(name)=:name and status='"
//				+ String.valueOf(StatusEnum.nodelete) + "'";

		if (LikeType.project == like.getType()) {
			Object topic = hibernateDAO.jdbcSelectLockRetunBean("select * from systopic where categroyName='"+String.valueOf(LikeType.project)+"'"+" and id="+like.getParm1()+" for update",Topic.class);

			if (topic == null) {
				message.setMsg("²Ù×÷Ê§°Ü");
				message.setStatus(false);
				return message;
			}
			return doLike(like, message,topic);
		}
		Object topic = null;
		if (LikeType.questionTopic == like.getType()) {
			
			 topic = hibernateDAO.jdbcSelectLockRetunBean("select * from systopic where categroyName='"+String.valueOf(LikeType.questionTopic)+"'"+" and id="+like.getParm1()+" for update",Topic.class);
			if (topic == null) {
				message.setMsg("²Ù×÷Ê§°Ü");
				message.setStatus(false);
				return message;
			}
			return doLike(like, message,topic);
		}

		if (LikeType.topic == like.getType()) {
			 topic = hibernateDAO.jdbcSelectLockRetunBean("select * from systopic where categroyName='"+String.valueOf(LikeType.topic)+"'"+" and id="+like.getParm1()+" for update",Topic.class);
			if (topic == null) {
				message.setMsg("²Ù×÷Ê§°Ü");
				message.setStatus(false);
				return message;
			}
			return doLike(like, message,topic);
		}
		if (LikeType.book == like.getType()) {
			 topic = super.findByColumn("form " + Book.class + " where id="+like.getParm1(),null );
			if (topic == null) {
				message.setMsg("²Ù×÷Ê§°Ü");
				message.setStatus(false);
				return message;
			}
			return doLike(like, message,topic);
		}
		message.setMsg("²Ù×÷Ê§°Ü");
		message.setStatus(false);
		return message;

	}

	public Message doLike(Like like, Message message,Object object) {

		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("parm1", like.getParm1());
		parm.put("createUserId", like.getUser().getId());
		
		Like result = super.findByColumn(
				"from " + Like.class.getSimpleName() + " where  parm1=:parm1 and createUserId=:createUserId and type='"+String.valueOf(like.getType())+"'", parm);
		boolean flag = true;
		if(result ==null){
			super.saveOrUpdate(like);
			message.setMsg("µãÔÞ³É¹¦");
			message.setStatus(true);
		}else{
			flag = false;
			hibernateDAO.delete(result);
			message.setMsg("È¡ÏûµãÔÞ");
			message.setStatus(true);
		}
		if(object  instanceof Topic){
			Topic covert = (Topic)object;
			if(flag){
				covert.setShare(covert.getShare()+1);
				
			}else{
				covert.setShare(covert.getShare()-1);
			}
		}else{
			Book covert = (Book)object;
			if(flag){
				covert.setShare(covert.getShare()+1);
				
			}else{
				covert.setShare(covert.getShare()-1);
			}
		}
		hibernateDAO.saveOrUpdate(object);
		return message;
	}

	@Override
	public Message<Boolean> islike(Integer id, User user) {
		Message<Boolean> message = new Message<Boolean>();
		Map<String, Object> parm = new HashMap<String, Object>();
		parm.put("parm1", id);
		parm.put("createUserId", user);
		
		Like result = super.findByColumn(
				"from " + Like.class.getSimpleName() + " where  parm1=:parm1 and createUserId=:createUserId", parm);
		if (result == null) {
			message.setData(false);
		} else {
			message.setData(true);
		}
		message.setStatus(true);
		return message;
	}

}
