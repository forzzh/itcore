package www.core.com.controller.manager.GroupPerson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.pojo.Message;
import www.core.com.service.IGroupPersonService;

@Controller
@RequestMapping("/manager/groupPerson")
public class GroupPersonManagerController  {
	@Autowired
	public IGroupPersonService groupPersonService;
	@ResponseBody
	@RequestMapping("/getGroupByUserId")
	public Message getGroupByUserId(Integer userId){
		
		return groupPersonService.getGroupByUserId(userId);
	}
}
