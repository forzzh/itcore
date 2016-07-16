package www.core.com.controller.manager.topic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import www.core.com.Enum.CategroyEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.AutoParmAndHQL;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.TopicContent;
import www.core.com.pojo.User;
import www.core.com.service.ITopicService;

@Controller
@RequestMapping("/manager/topic")
public class TopicManagerControl  extends BaseController{
	@Autowired
	public ITopicService topicService;
	@RequestMapping("list")
	public String list(ModelMap map, HttpServletRequest request, Integer start) {

		// TODO Auto-generated method stub


		// 生成搜索条件 自动搜索代码


		AutoParmAndHQL auto = super.createAuto(request);
		// 分页


		map.put("auto", auto);
		System.out.println(JSON.toJSONString(auto));

		// 如果开始页面是空情况下


		if (start == null)
			start = 1;
		Long recordCount = super.count("select count(*) from Topic   " + auto.getHql(), auto.getMap());

		// 分页算法


		autoPage(map, recordCount, start, "/manager/topic/list");

		// 加载数据


		List list = super.getAutoListByPage("from Topic   " + auto.getHql(), auto.getMap(), (start - 1) * 10, 10);
		map.put("entity", list);
		System.out.println("select count(*) from Topic   " + auto.getHql());

		// 找自动化页面
		return "manager/" + super.getAllName(Topic.class) + "/list";
	}

	@RequestMapping("/add")
	public String addInit(ModelMap map, HttpServletRequest request, Integer start) {
		map.put("categroy", CategroyEnum.values());

		return "/manager/topic/add";
	}
	
	@ResponseBody
	@RequestMapping(value="/addTopic",method=RequestMethod.POST)
	public Object addTopic(Topic topic, TopicContent detail, User user,String extLabels,HttpServletRequest request,Integer userId)
			throws Exception {
		Message<Topic> message = null;
		if (userId == null) {
			message = new Message<Topic>();
			message.setMsg("创建者为空");
			return message;
		}
		message= super.baseTopicAdd(topic,detail,user, extLabels, userId);
		if(!message.isStatus() ){
			return message;
		}
		if(StringUtils.isNotBlank(extLabels)){
			message = topicService.add(super.getQiqiuUrl(),super.getStaticUrl(),topic, detail, user,super.array_unique(super.extLabelUtils(topic.getLabel().getName(), extLabels)));
		}else{
			message = topicService.add(super.getQiqiuUrl(),super.getStaticUrl(), topic, detail, user, null);
		}
//		detail.setContent("123");
		return message;
	}
	@RequestMapping("/modify")
	public String modify(ModelMap map, HttpServletRequest request, Integer start) {
		map.put("categroy", CategroyEnum.values());

		return "/manager/topic/modify";
	}
//	@ResponseBody
//	@RequestMapping("/updateTopicContent")
//	public Message<Topic> update(HttpServletRequest request,String staticPath,String content, Integer id,String title) throws Exception  {
//		Message<Topic> message = null;
//		if (id == null) {
//			message = new Message<Topic>();
//			message.setMsg("帖子id为空");
//			return message;
//		}
//		if (StringUtils.isBlank(content)) {
//			message = new Message<Topic>();
//			message.setMsg("文章内容不能为空");
//			return message;
//		}
//		if (StringUtils.isBlank(title)) {
//			message = new Message<Topic>();
//			message.setMsg("标题不能为空");
//			return message;
//		}
//		return topicService.update(super.getStaticUrl(), content, id, title);
//	}
}