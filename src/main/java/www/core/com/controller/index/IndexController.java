package www.core.com.controller.index;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IIndexService;
import www.core.com.utils.CORER;

@RequestMapping
@Controller
public class IndexController extends BaseController{

	@Resource
	IIndexService indexService;
	
	@RequestMapping("index.html")
	public String index(HttpSession session,Model model){
		//用户数据
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user==null){
			model.addAttribute("show",false);
		}else{
			model.addAttribute("show",true);
			model.addAttribute("userId",user.getId());
		}
		
		Map map = indexService.index(user);
		model.addAttribute("staticUrl", super.getQiqiuUrl());
		//软件发布帖子
		model.addAttribute("topics", map.get("topics"));
		//问题帖子
		model.addAttribute("questions", map.get("questions"));
		//标签
		model.addAttribute("labels", map.get("labels"));
		
		//index标签
		model.addAttribute("indexLabels", map.get("indexLabels"));
		model.addAttribute("newIndexLabels", map.get("newIndexLabels"));
		
		//index 帖子
		model.addAttribute("indexTopics", map.get("indexTopics"));
		model.addAttribute("newIndexTopics", map.get("newIndexTopics"));
		
		//index 图书
		model.addAttribute("indexBooks", map.get("indexBooks"));
		
		//帖子类型
		model.addAttribute("categroyEnums", map.get("categroyEnums"));
		
		//用户所在圈子
		model.addAttribute("userGroup", map.get("userGroup"));
		
		//关注的话题
		model.addAttribute("focusTopics", map.get("focusTopics"));

		return "/web/user/index/index";
	}
	
	@RequestMapping("/search")
	@ResponseBody
	public Message indexSearch(String search){
		Message message = new Message();
		if(StringUtils.isBlank(search)){
			message.setStatus(false);
			return message;
		}
		try{
			message =  indexService.indexSearch(search);
		}catch(Exception e){
			e.printStackTrace();
		}
		return message;
	}
}
