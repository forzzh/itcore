package www.core.com.controller.groupperson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.pojo.Group;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IGroupPersonService;

@Controller
@RequestMapping("/groupPerson")
public class GroupPersonController {
	@Autowired
	public IGroupPersonService groupPersonService;
	
	@ResponseBody
	@RequestMapping("/getUsersByGroupId")
	public Message<User> getUserByGroupId(Integer groupid, Integer start, Integer row) {
		Message<User> message = new Message<>();

		if (groupid == null) {
			message.setMsg("groupid不能为空");
			message.setStatus(false);
			return message;
		}

		return groupPersonService.getUserByGroupId(groupid, (start - 1) * row, row);       
	
	}	
	
	@ResponseBody
	@RequestMapping("/getGroupByUserId")
	public Message getGroupByUserId(Integer userId){
		
		return groupPersonService.getGroupByUserId(userId);
	}
	
	@ResponseBody
	@RequestMapping("/getGroupByuserId")
	public Message<Group> getGroupByUserid(Integer userid, Integer start, Integer row) {
		Message<Group> message = new Message<>();

		if (userid == null) {
			message.setMsg("groupid不能为空");
			message.setStatus(false);
			return message;  
		}
		if( start==null || start <= 0 || row ==null || row <0){
			message.setMsg("参数错误：start:"+start+" count:"+row);
			message.setStatus(false);
			return message;
		}
		return groupPersonService.getGroupByUserid(userid, (start - 1) * row, (row+1));       
	}	
	
	@ResponseBody
	@RequestMapping("/getCreateGroupByuserId")
	public Message getCreateGroupByuserId(Integer userid){
		
		return groupPersonService.getCreateGroupByUserId(userid);
	}
	
	
	
}
