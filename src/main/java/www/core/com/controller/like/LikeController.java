package www.core.com.controller.like;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Like;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.ILikeService;
import www.core.com.service.IUserService;

@RequestMapping("like")
@Controller
public class LikeController  extends BaseController<Like>{

	@Autowired
	private ILikeService likeService;
	
	@Autowired
	private IUserService userService;
	
	
	
	@RequestMapping("/dolike")
	public @ResponseBody Message<Like>  dolike(Like like){
		Message<Like> message = null;
		
		if(like.getUser()==null||like.getUser().getId()==null){
			message = new Message<Like>();
			message.setStatus(false);
			message.setMsg("用户不存在");
			return message;
		}
		if(like.getType() == null){
			message = new Message<Like>();
			message.setStatus(false);
			message.setMsg("类型不存在");
			return message;
		}
		if(like.getParm1() ==null){
			message = new Message<Like>();
			message.setStatus(false);
			message.setMsg("操作失败");
			return message;
		}
		message = likeService.like(like);
		return message;
	}
	
	/**
	 * 用户是否点过赞
	 * @param id
	 * 		文章id
	 * @param userId
	 * 		用户id，如果没传取当前登录用户
	 * @return
	 */
	@RequestMapping("/islike")
	public @ResponseBody Message<Boolean> islike(Integer id, Integer userId, HttpSession session){
		Message<Boolean> message = null;
		User user = null;
		if (userId == null) {
			user = (User) session.getAttribute(SESSION_USER);
		} else {
			user = userService.userQuery(userId);
		}
		if (user == null) {
			message = new Message<Boolean>();
			message.setStatus(false);
			message.setMsg("用户不存在");
			return message;
		}
		message = likeService.islike(id, user);
		return message;
	}
}
