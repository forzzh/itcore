package www.core.com.controller.foucustopicorbook;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IFocusTopicOrBookService;

@Controller
@RequestMapping("/focusTopicOrBook")
public class FocusTopicOrBookController {

	@Autowired
	IFocusTopicOrBookService focusTopicOrBookService;
	
	@RequestMapping("focus")
	@ResponseBody
	public Message focusTopicOrBook(User user,Integer commonId,String type){
		Message result = new Message<>();
		if(user.getId() == null || commonId ==null || StringUtils.isBlank(type)){
			result.setStatus(false);
			result.setMsg("������userID:" +user.getId()+ " commonid :" + commonId+ " type: " +type );
			return result;
		}
		if(!"0".equals(type) && !"1".equals(type)){
			result.setStatus(false);
			result.setMsg("��ע���ʹ���type:" +type);
			return result;
		}
		return focusTopicOrBookService.focusTopicOrBook(user, commonId, type);
	}
}
