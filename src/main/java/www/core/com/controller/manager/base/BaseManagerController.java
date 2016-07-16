package www.core.com.controller.manager.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import www.core.com.Enum.StatusEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Message;
import www.core.com.service.impl.AbstractService;

@Controller
@RequestMapping("/manager/base")
public class BaseManagerController extends BaseController{
	@Autowired
	AbstractService abstractService;
	@ResponseBody
	@RequestMapping(value="/delete/" )
	public Message delete(String delete,Integer id){
		Message message = new Message<>();
		abstractService.updateStatus(delete, id, StatusEnum.delete.toString());;
		message.setStatus(true);
		message.setMsg("É¾³ý³É¹¦");
		return message;
	}

}
