package www.core.com.controller.foucus;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.controller.base.BaseController;
import www.core.com.pojo.FocusTag;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IFocusTagService;

@Controller
@RequestMapping("focus")
public class FocusTagController extends BaseController<FocusTag>{
	@Autowired
	IFocusTagService focusTagService;
	
	@RequestMapping("dofocus")
	@ResponseBody
	public Message dofocus(HttpSession session,Model model,Integer label){
		Message<FocusTag> focus = null;
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			focus = new Message<>();
			focus.setMsg("用户未登录");
			focus.setStatus(false);
			return focus;
		}
		if(label == null){
			focus = new Message<>();
			focus.setMsg("用户不存在");
			focus.setStatus(false);
			return focus;
		}
		
		return focusTagService.dofocus(user.getId(), label);
	}
	
	
}
