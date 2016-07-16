
package www.core.com.controller.topic;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.Enum.CategroyEnum;
import www.core.com.Enum.ReplyEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Group;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.TopicContent;
import www.core.com.pojo.User;
import www.core.com.service.IGroupPersonService;
import www.core.com.service.IIndexLabelConfigService;
import www.core.com.service.ILabelService;
import www.core.com.service.ITopicService;
import www.core.com.service.impl.AbstractService;
import www.core.com.utils.CORER;
import www.core.com.utils.Page;

@RequestMapping("/topic")
@Controller
public class TopicController extends BaseController<Topic> {
	@Autowired
	private ITopicService topicService;

	@Autowired
	private IGroupPersonService groupPersonService;
	
	@Autowired
	IIndexLabelConfigService indexLabelConfigService;
	
	@Autowired
	ILabelService labelService;
	
	@ResponseBody
	@RequestMapping("/add")
	public Object add(HttpServletRequest request,Topic topic, TopicContent detail, String extLabel) throws Exception {
		Message message = null;
		
		User user = (User) request.getSession().getAttribute(CORER.SESSION_USER);//登陆用户
	
		if (user == null) {
			message = new Message<Topic>();
			message.setMsg("创建者为空");
			return message;
		}
		
		//帖子创建者
		topic.setUser(user);
		
		if (StringUtils.isBlank(topic.getTitle())) {
			message = new Message<Topic>();
			message.setMsg("标题不能为空");
			return message;
		}
	

		if (topic.getLabel() == null) {
			message = new Message<Topic>();
			message.setMsg("标签不能为空");
			return message;
		}

		if (StringUtils.isBlank(detail.getContent())) {
			message = new Message<Topic>();
			message.setMsg("文章内容不能为空");
			return message;
		}
		
		if(StringUtils.isNotBlank(extLabel)){
			message = topicService.add( super.getQiqiuUrl(),super.getStaticUrl(),topic, detail,null, super.array_unique(super.extLabelUtils(topic.getLabel().getName(), extLabel)));
		}else{
			message = topicService.add(super.getQiqiuUrl(),super.getStaticUrl(), topic, detail,null, null);
		}
		return message;
	}

	@ResponseBody
	@RequestMapping("update")
	public Object update(String content, Integer id,String title,HttpServletRequest request,String markdown) throws Exception {
		Message message = null;
		if (id == null) {
			message = new Message<Topic>();
			message.setMsg("帖子id为空");
			return message;
		}
		if (StringUtils.isBlank(content)) {
			message = new Message<Topic>();
			message.setMsg("帖子id为空");
			return message;
		}
		message = topicService.update(super.getStaticUrl(),content, id,title,markdown) ;
		return message;
	}
	@ResponseBody
	@RequestMapping("search")
	public Object search(String search, Integer page) throws InterruptedException, ParseException {
		Message message = null;
		
		if (StringUtils.isBlank(search)) {
			message = new Message<Topic>();
			message.setMsg("搜索内容为空");
			return message;
		}
		return  topicService.search(search,page);
	}

	
	/**
	 * 回复帖子
	 * @param topicid    帖子id
	 * @param CategroyEnum 帖子分类类型
	 * @param content	  回复内容
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reply")
	public Message<Topic> replyTopic(HttpServletRequest request,Integer topicid,Integer groupid,CategroyEnum categroyEnum, String content,HttpSession session){
		Message message = new Message();
		User user = (User) session.getAttribute(CORER.SESSION_USER);//登陆用户
		if(user == null){
			message.setStatus(false);
			message.setMsg("用户未登录");
			return message;
		}
		//回复类型为帖子回复
		ReplyEnum replayEnum = ReplyEnum.TopicReply;
		
		if(topicid == null){
			message.setStatus(false);
			message.setMsg("帖子id不能为空");
			return message;
		}
		
		if(StringUtils.isBlank(content)){
			message.setStatus(false);
			message.setMsg("回复内容不能为空");
			return message;
		}
		
		if(categroyEnum == null){
			message.setStatus(false);
			message.setMsg("帖子类型不能为空");
			return message;
		}

		message = topicService.addReply(topicid,groupid,user,replayEnum,content,categroyEnum);
		return message;
	}
	
	/**
	 * 查询对应userId所发布的技术帖子
	 * @param userId 要查询的用户ID
	 * @param start 
	 * @param row
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTecTopicsWhoCreate")
	public Message<Topic> getTopicsWhoCreate(Integer userId, Integer start, Integer row) {
		Message<Topic> message = new Message<>();
		if (userId == null) {
			message.setMsg("userId不能为空");
			message.setStatus(false);
			return message;
		}
		
		return topicService.getTopicsWhoCreate(CategroyEnum.topic,userId, (start - 1) * row, row);
	}
	
	/**
	 * 为帖子增加答案
	 * @param topicId 帖子id
	 * @param replyId 回复id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addAnser")
	public Message<Topic> addTopicAnser(Integer topicId,Integer replyId){
		Message<Topic> message = new Message<>();
		if(topicId == null){
			message.setStatus(false);
			message.setMsg("帖子id不能为空");
			return message;
		}
		
		if(replyId == null){
			message.setStatus(false);
			message.setMsg("回复id不能为空");
			return message;
		}
		
		message = topicService.addTopicAnser(topicId,replyId);
		return message;
	}
	
	@RequestMapping("result")
	public String search(Model model , @RequestParam String search){
		if(StringUtils.isBlank(search)){
			return  "/web/search/search";
		}
		
		Page page = null;
		try {
			Map map = topicService.searchAll(search, 1);
			page = (Page) map.get("pages");
			model.addAttribute("page", page);
			//软件发布帖子
			List topics = (List) map.get("topics");
			model.addAttribute("topics", topics);
			
			//技术问题帖子
			List questions = (List) map.get("questions");
			model.addAttribute("questions", questions);
			
			//标签
			List labels = (List) map.get("labels");
			model.addAttribute("labels", labels);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		model.addAttribute("static", super.getQiqiuUrl());
		model.addAttribute("search", search);
		return  "/web/search/search";
	}
	
	@ResponseBody
	@RequestMapping("/result/page")
	public Page searchaPage( @RequestParam String search,Integer page){
		if(StringUtils.isBlank(search)){
			return  new Page();
		}
		
		if(page == null){
			page = 2;
		}
		
		Page pages = null;
		try {
			pages = topicService.search(search,page);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  pages;
	}
	

	@RequestMapping("/addTopic")
	public String addTopic(HttpSession session,Model map,HttpServletRequest request){
		//用户数据
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user==null){
			map.addAttribute("show",false);
			return "redirect:http://"+request.getServerName()+"/user/login?redirect=/topic/addTopic"; 
		}else{
			List newIndexLabels =  labelService.queryLabelData(0,10);
			map.addAttribute("newIndexLabels", newIndexLabels);
			map.addAttribute("show",true);
			//用户圈子信息
			List<Group> groups = groupPersonService.getGroupByUser(user.getId());
			map.addAttribute("userGroup", groups);
		}
		CategroyEnum[] categroyEnums = CategroyEnum.values();
		map.addAttribute("categroyEnums", categroyEnums);		
		return "/web/topic/addTopic";
	}
	
	
	/**
	 * 删除指定的 topicId
	 * @param session
	 * @param topicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTopic")
	public Message<Topic> deleteTopic(HttpSession session,Integer topicId){
		
		Message<Topic> message = new Message<>();
		User user = (User) session.getAttribute(CORER.SESSION_USER);//登陆用户
		if(user == null){
			message.setStatus(false);
			message.setMsg("用户未登录");
			return message;
		}
		if(user.getId() == null){
			message.setStatus(false);
			message.setMsg("用户信息错误");
			return message;
		}
		if(topicId == null){
			message.setStatus(false);
			message.setMsg("帖子ID不能为空");
			return message;
		}
		
		return topicService.deleteTopid(user,topicId);
		
	}
	
	@RequestMapping("/modifyTopic")
	public String modifyTopic(HttpSession session, Model map, Integer topicId,HttpServletRequest request){
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user==null){
			map.addAttribute("show",false);
			return "redirect:http://"+request.getServerName()+"/user/login?redirect=/topic/addTopic"; 
		}
		Message<Topic> detail = topicService.detailMarkdown(topicId);
		map.addAttribute("topic", detail.getData());
		map.addAttribute("content", detail.getTopicContent().getContent());
//		topicService.
		
		//用户数据
		
		
		return "/web/topic/modifyTopic";
	}
	
}

