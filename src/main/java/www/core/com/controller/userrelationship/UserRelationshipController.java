package www.core.com.controller.userrelationship;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.pojo.UserRelationship;
import www.core.com.service.IUserRelationshipService;

@Controller
@RequestMapping("userRelationship/")
public class UserRelationshipController extends BaseController {
	@Autowired
	IUserRelationshipService userRelationshipService;

	/**
	 * @param passiveUserId
	 * @param positiveUserId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("switchfollowStatus")
	public Message<UserRelationship> switchfollowStatus(Integer passiveUserId, Integer positiveUserId) {
		Message<UserRelationship> message = new Message<>();
		
		if (passiveUserId == null) {
			message.setMsg("null passiveUserId");
			message.setStatus(false);
			return message;
		}
		if (positiveUserId == null) {
			message.setMsg("null positiveUserId");
			message.setStatus(false);
			return message;
		}

		if (positiveUserId.equals(passiveUserId)) {
			message.setMsg("自己不能关注自己");
			message.setStatus(false);
			return message;
		}
		Message<UserRelationship> userRelationship = userRelationshipService.switchfollowStatus(passiveUserId,
				positiveUserId);

		return userRelationship;

	}

	@ResponseBody
	@RequestMapping("isFollowTogether")
	public Message<UserRelationship> isFollowTogether(Integer passiveUserId, Integer positiveUserId) {

		Message<UserRelationship> message = new Message<>();
		if (passiveUserId == null) {
			message.setMsg("null passiveUserId");
			message.setStatus(false);
			return message;
		}
		if (positiveUserId == null) {
			message.setMsg("null positiveUserId");
			message.setStatus(false);
			return message;
		}

		if (positiveUserId.equals(passiveUserId)) {
			message.setMsg("自己不能关注自己");
			message.setStatus(false);
			return message;
		}

		message = userRelationshipService.isFollowTogether(passiveUserId, positiveUserId);
		return message;
	}

	@ResponseBody
	@RequestMapping("getFansList")
	public Message<UserRelationship> getFansList(Integer passiveUserId, Integer start, Integer row) {
		Message<UserRelationship> message = new Message<>();

		if (passiveUserId == null) {
			message.setMsg("null passiveUserId");
			message.setStatus(false);
			return message;
		}

		if (start == null) {
			message.setMsg("null start");
			message.setStatus(false);
			return message;
		}

		if (row == null) {
			message.setMsg("null row");
			message.setStatus(false);
			return message;
		}

		message = userRelationshipService.getFansList(passiveUserId, (start - 1), row);

		return message;
	}

	@ResponseBody
	@RequestMapping("getFollowerList")
	public Message<User> getFollowerList(Integer positiveUserId, Integer start, Integer row) {
		Message<User> message = new Message<>();

		if (positiveUserId == null) {
			message.setMsg("null positiveUserId");
			message.setStatus(false);
			return message;
		}

		if (start == null) {
			message.setMsg("null start");
			message.setStatus(false);
			return message;
		}

		if (row == null) {
			message.setMsg("null row");
			message.setStatus(false);
			return message;
		}

		message = userRelationshipService.getFollowerList(positiveUserId, (start - 1), row);

		return message;
	}
	
	@RequestMapping("/addFriend")
	@ResponseBody
	public Message addFriend(Integer passiveUserId,Integer positiveUserId,HttpServletRequest request){
		Message result = new Message<>();
		User user = (User) request.getSession().getAttribute(SESSION_USER);
		if(user == null){
			result.setStatus(false);
			result.setMsg("用户未登录");
			return result;
		}
		else if(passiveUserId == user.getId()){
			result.setStatus(false);
			result.setMsg("不能添加自己！");
			return result;
		}
		return userRelationshipService.addFriend(passiveUserId,user);
	}
	
	@RequestMapping("/isFocus")
	@ResponseBody
	public Message isFocus(Integer passiveUserId,HttpServletRequest request){
		Message message = new Message();
		User user = (User) request.getSession().getAttribute(SESSION_USER);
		if(user == null){
			message.setStatus(false);
			message.setMsg("用户未登录");
			return message;
		}
		if (user.getId().intValue() == passiveUserId.intValue()) {
			message.setStatus(false);
			message.setMsg("自己不用关注自己");
			return message;
		}
		message = userRelationshipService.getUserRelationship(passiveUserId,user.getId());
		return message;
	}

}
