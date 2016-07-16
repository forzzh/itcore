package www.core.com.controller.manager.group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import www.core.com.Enum.StatusEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.AutoParmAndHQL;
import www.core.com.pojo.Group;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IGroupService;
import www.core.com.service.IUserService;
import www.core.com.utils.CORER;


@Controller
@RequestMapping("/manager/group")
public class GroupManagerControl extends BaseController<Group> {
	
	@Autowired
	private IGroupService groupService;
	
	@Autowired
	private IUserService userService;

	@RequestMapping("list")
	public String list(ModelMap map, HttpServletRequest request, Group group, Integer start) {

		// TODO Auto-generated method stub
		//生成搜索条件 自动搜索代码
		AutoParmAndHQL auto = super.createAuto(request);
		
		//寻找需要审核的
		auto.setHql("where status = " + "'" + StatusEnum.pending+ "'");
		
		//分页
		map.put("auto", auto);
		System.out.println(JSON.toJSONString(auto));
		
		//如果开始页面是空情况下
		if (start == null)
		start = 1;
		Long recordCount = super.count("select count(*) from Group   " + auto.getHql(), auto.getMap());
		
		//分页算法
		autoPage(map,recordCount,start,"/manager/group/list");
		
		//加载数据
		List list = super.getAutoListByPage("from Group " + auto.getHql(), auto.getMap(), (start - 1)*10, 10);
		map.put("entity", list);
		System.out.println("select count(*) from Group   " + auto.getHql());

		//找自动化页面
		return "manager/group/list";
	}
	
	/**
	 * 审核
	 */
	@ResponseBody
	@RequestMapping("auditGroup")
	public Message auditGroup(Integer groupId, Integer status, HttpSession session) {
		Message message = new Message();
		//获取用户信息
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if (user == null) {
			message.setStatus(false);
			message.setMsg("请先登录");
			return message; 
		}
//		User user = userService.userQueryById(4);
		StatusEnum statusEnum = null;
		if (status == 1) {
			statusEnum = StatusEnum.agree;
		} else {
			statusEnum = StatusEnum.noagree;
		}
		
		message = groupService.reviewGroup(user, groupId, statusEnum);
		return message;
	}
}
