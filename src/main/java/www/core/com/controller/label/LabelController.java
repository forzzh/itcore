package www.core.com.controller.label;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.Enum.CategroyEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.User;
import www.core.com.service.ILabelService;
import www.core.com.service.ITopicService;
import www.core.com.service.IUserService;

@RequestMapping("label/")
@Controller
public class LabelController extends BaseController<Label> {
	@Autowired
	ILabelService labelService;
	@Autowired
	IUserService userService;
	@Resource
	ITopicService topicService;
	/**
	 * 根据用户ID查找用户所创建的label
	 * 
	 * @param id
	 *            用户ID
	 * @param start
	 * @param row
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getUserFollowLabels")
	public Message<Label> getUserFollowLabels(Integer id, Integer start, Integer row) {
		Message<Label> message = new Message<>();
		if (id == null) {
			message.setMsg("userId不能为空");
			message.setStatus(false);
			return message;
		}

		return labelService.getLabelsWhoCreate(id, (start - 1) * row, row);
	}

	@RequestMapping("/list")
	public String index(ModelMap map) {
		//热门软件列表
		List<Topic> adTopics = topicService.getHotTopicByCategroyName(CategroyEnum.project, 0, 5);
		map.put("adTopics", adTopics);
		return "/web/label/label";
	}

	/**
	 * 根据页码和数目查询Label标记集合
	 * 
	 * @param pageNum
	 *            页码
	 * @param count
	 *            数量
	 * @return
	 */
	@ResponseBody
	@RequestMapping("queryPageData")
	public Message<List<Label>> queryPageData(Integer pageNum, Integer count) {
		Message<List<Label>> result = new Message<>();
		super.checkLabelPage(pageNum, count);
		return labelService.queryLabelPageData(pageNum, count);
	}

	@ResponseBody
	@RequestMapping("labelList")
	public Message<List<Label>> listLabel(Integer pageNum, Integer count,HttpSession session) {
		Message<List<Label>> result = new Message<>();
		super.checkLabelPage(pageNum, count);
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return labelService.queryLabelNamePage(pageNum, count,false,null);
		}else{
			
			return labelService.queryLabelNamePage(pageNum, count,true,user.getId());
		}
			
		
	}
	
	
	@ResponseBody
	@RequestMapping("searchByName")
	public Message<List<Label>> searchByName(String name,HttpSession session){
		Message<List<Label>> result = new Message<>();
		if(name==null || "".equals(name)){
			result.setMsg("搜索条件不能为空");
			return result;
		}
		
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return labelService.queryLabelByName(name,false,null);
		}else{
			return labelService.queryLabelByName(name,true,user.getId());
		}
		
	}
	@RequestMapping("/my")
	public String init(ModelMap map,HttpSession session,HttpServletRequest request){
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		map.put("userdata",	(userService.userQuery(user.getId())));
		map.put("id", user.getId());
		map.put("userId", user.getId());
		map.put("show", true);
		return "/web/user/my/label";
	}
	@RequestMapping("/other/{userid}")
	public String otherLabel(@PathVariable Integer userid,ModelMap map,HttpServletRequest request){
		if(userid==null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		map.put("userdata",	(userService.userQuery(userid)));
		map.put("id", userid);
		map.put("userId", userid);
		map.put("show", false);
		return "/web/user/my/label";
	}
	@ResponseBody
	@RequestMapping("/randomLabelList")
	public Message<Label> getRandomLabelList(Integer num){
		Message<Label> result = new Message<>();
		if(num == null || num <1){
			result.setMsg("查许参数错误：num :"+num);
			result.setStatus(false);
		}else{
			result.setList(labelService.getRandomLabelList(num));
			result.setStatus(true);
		}
		return result;
	}

}


