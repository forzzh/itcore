package www.core.com.controller.messagesys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import www.core.com.Enum.StatusEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Book;
import www.core.com.pojo.Message;
import www.core.com.pojo.MessageSys;
import www.core.com.pojo.User;
import www.core.com.service.IMessageSysService;
import www.core.com.utils.CORER;

@Controller
@RequestMapping("/msg")
public class MessageSysController extends BaseController<MessageSys> {

	@Autowired
	IMessageSysService messageSysService;
	
	/**
	 * 增加消息
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Message<MessageSys> addMessageSys(MessageSys messageSys) {
		Message message = new Message<MessageSys>();
		
		//消息内容校验
		if(StringUtils.isBlank(messageSys.getContent())){
			message.setStatus(false);
			message.setMsg("消息内容不能为空");
			return message;
		}
		
		//消息接收者为空
		if(messageSys.getUser() == null){
			message.setStatus(false);
			message.setMsg("消息接收者不能为空");
			return message;
		}
		
		//消息设为未读
		messageSys.setStatus(StatusEnum.noread);
		message = messageSysService.addMessageSys(messageSys);
		return message;
	}

	
	/**
	 * 用户消息删除
	 * @param msgID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Message<MessageSys> deleteMessageSys(Integer msgID) {
		Message message = new Message<MessageSys>();
		
		if(msgID == null){
			message.setStatus(false);
			message.setMsg("删除消息id为空");
			return message;
		}
		
		message = messageSysService.deleteMessageSys(msgID);
		return message;
	}

	/**
	 * 更新消息状态为已读
	 * @param msgID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateMsgRead")
	public Message<MessageSys> updateMsgRead(Integer msgID) {
		Message message = new Message<MessageSys>();
		if(msgID == null){
			message.setStatus(false);
			message.setMsg("更新数据ID为空");
			return message;
		}
		message = messageSysService.updateMsgRead(msgID);
		return message;
	}

	// 1分页 2总数 3当前页面 status
	@ResponseBody
	@RequestMapping("/getMsgList")
	public Message<MessageSys> getMsgList(Integer page,Integer limit,@RequestParam StatusEnum status) {
		Message message = new Message<MessageSys>();
		if(status == null){
			message.setStatus(false);
			message.setMsg("状态参数错误");
			return message;
		}
		message = messageSysService.getMsgList(page,limit,status);
		return message;
	}

	/**
	 * 圈子创建者处理加群消息
	 * @param 消息id
	 * @param 消息处理结果，值为		StatusEnum.agree或者StatusEnum.noagree
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agree")
	public Message<MessageSys> agreeMsg(HttpSession session,Integer msgID,StatusEnum status) {
		Message message = new Message<MessageSys>();
		
		if(msgID == null){
			message.setStatus(false);
			message.setMsg("消息id为空");
			return message;
		}
		
		if(status != StatusEnum.agree && status != StatusEnum.noagree){
			message.setStatus(false);
			message.setMsg("状态参数错误");
			return message;
		}
		
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user == null){
			message.setStatus(false);
			message.setMsg("用户未登陆");
			return message;
		}

		message = messageSysService.agreeMsg(user,msgID,status);
		return message;
	}

	@RequestMapping("/queryUserMsgList")
	@ResponseBody
	public Message<MessageSys> queryUserMsgList(Integer pageNum,Integer count,Integer userId,StatusEnum status){
		Message result = new Message<>();
		if(pageNum <1 ){
			result.setMsg("参数错误：pageNum ：" +pageNum);
			result.setStatus(false);
			return result;
		}
		if(count == null || count <0){
			count = 10;
		}
		return messageSysService.queryUserMsgList(pageNum,count,userId,status);
	}
	/**
	 * 用户消息列表
	 * @param 
	 * @param 消息处理结果，值为		
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/messagelsitbyuserid")
	public Message<MessageSys> messageList(Integer userid,Integer start,Integer pageNum){
		Message<MessageSys> message = new Message<>();
		
		if(!super.checkLabelPage(start, pageNum).isStatus()){
			return super.checkLabelPage(start, pageNum);
		}
		message = messageSysService.list(userid,start,pageNum);
		return message;
	}
	
	/**
	 * 把消息设为已读，并跳转指定路径
	 * @param msgid
	 * @param redirect
	 * @return
	 */
	@RequestMapping("/controller")
	public ModelAndView redirectController(HttpServletRequest request,Integer msgid,String redirect){
		if(msgid == null || redirect == null){
			throw new RuntimeException("参数出错！");
		}
		
		Message msg = messageSysService.updateMsgRead(msgid);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:http://"+request.getServerName()+redirect);
		return mav;
	}
	
	@RequestMapping("/unreadMessageCount")
	@ResponseBody
	public Message unreadMessageCount(HttpSession session){
		Message message = new Message<>();
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user == null){
			message.setStatus(false);
			return message;
		}

		message =  messageSysService.unreadMessageCount(user.getId());
		message.setUser(user);
		return message;
	}
	
	/**
	 * 设置所有消息为已读
	 * @return
	 */
	@RequestMapping("/setAllRead")
	@ResponseBody
	public Message setAllRead(HttpSession session){
		Message message = new Message<>();
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user == null){
			message.setStatus(false);
			return message;
		}
		return messageSysService.setAllRead(user);
	}
	
	/**
	 * 设置所有消息为已读
	 * @return
	 */
	@RequestMapping("/delteAllMsg")
	@ResponseBody
	public Message delteAllMsg(HttpSession session){
		Message message = new Message<>();
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user == null){
			message.setStatus(false);
			return message;
		}
		return messageSysService.delteAllMsg(user);
	}
}
