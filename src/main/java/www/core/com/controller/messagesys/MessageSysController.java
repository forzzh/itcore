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
	 * ������Ϣ
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Message<MessageSys> addMessageSys(MessageSys messageSys) {
		Message message = new Message<MessageSys>();
		
		//��Ϣ����У��
		if(StringUtils.isBlank(messageSys.getContent())){
			message.setStatus(false);
			message.setMsg("��Ϣ���ݲ���Ϊ��");
			return message;
		}
		
		//��Ϣ������Ϊ��
		if(messageSys.getUser() == null){
			message.setStatus(false);
			message.setMsg("��Ϣ�����߲���Ϊ��");
			return message;
		}
		
		//��Ϣ��Ϊδ��
		messageSys.setStatus(StatusEnum.noread);
		message = messageSysService.addMessageSys(messageSys);
		return message;
	}

	
	/**
	 * �û���Ϣɾ��
	 * @param msgID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public Message<MessageSys> deleteMessageSys(Integer msgID) {
		Message message = new Message<MessageSys>();
		
		if(msgID == null){
			message.setStatus(false);
			message.setMsg("ɾ����ϢidΪ��");
			return message;
		}
		
		message = messageSysService.deleteMessageSys(msgID);
		return message;
	}

	/**
	 * ������Ϣ״̬Ϊ�Ѷ�
	 * @param msgID
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateMsgRead")
	public Message<MessageSys> updateMsgRead(Integer msgID) {
		Message message = new Message<MessageSys>();
		if(msgID == null){
			message.setStatus(false);
			message.setMsg("��������IDΪ��");
			return message;
		}
		message = messageSysService.updateMsgRead(msgID);
		return message;
	}

	// 1��ҳ 2���� 3��ǰҳ�� status
	@ResponseBody
	@RequestMapping("/getMsgList")
	public Message<MessageSys> getMsgList(Integer page,Integer limit,@RequestParam StatusEnum status) {
		Message message = new Message<MessageSys>();
		if(status == null){
			message.setStatus(false);
			message.setMsg("״̬��������");
			return message;
		}
		message = messageSysService.getMsgList(page,limit,status);
		return message;
	}

	/**
	 * Ȧ�Ӵ����ߴ����Ⱥ��Ϣ
	 * @param ��Ϣid
	 * @param ��Ϣ��������ֵΪ		StatusEnum.agree����StatusEnum.noagree
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/agree")
	public Message<MessageSys> agreeMsg(HttpSession session,Integer msgID,StatusEnum status) {
		Message message = new Message<MessageSys>();
		
		if(msgID == null){
			message.setStatus(false);
			message.setMsg("��ϢidΪ��");
			return message;
		}
		
		if(status != StatusEnum.agree && status != StatusEnum.noagree){
			message.setStatus(false);
			message.setMsg("״̬��������");
			return message;
		}
		
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user == null){
			message.setStatus(false);
			message.setMsg("�û�δ��½");
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
			result.setMsg("��������pageNum ��" +pageNum);
			result.setStatus(false);
			return result;
		}
		if(count == null || count <0){
			count = 10;
		}
		return messageSysService.queryUserMsgList(pageNum,count,userId,status);
	}
	/**
	 * �û���Ϣ�б�
	 * @param 
	 * @param ��Ϣ��������ֵΪ		
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
	 * ����Ϣ��Ϊ�Ѷ�������תָ��·��
	 * @param msgid
	 * @param redirect
	 * @return
	 */
	@RequestMapping("/controller")
	public ModelAndView redirectController(HttpServletRequest request,Integer msgid,String redirect){
		if(msgid == null || redirect == null){
			throw new RuntimeException("��������");
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
	 * ����������ϢΪ�Ѷ�
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
	 * ����������ϢΪ�Ѷ�
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
