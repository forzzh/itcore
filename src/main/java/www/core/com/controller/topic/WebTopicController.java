package www.core.com.controller.topic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import www.core.com.Enum.AnswerState;
import www.core.com.Enum.CategroyEnum;
import www.core.com.Enum.StatusEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.Reply;
import www.core.com.pojo.Topic;
import www.core.com.pojo.User;
import www.core.com.service.ILabelService;
import www.core.com.service.IReplyService;
import www.core.com.service.ITopicService;
import www.core.com.service.impl.IndexService;
import www.core.com.utils.CORER;
import www.core.com.utils.HtmlGetImg;

@Controller
public class WebTopicController extends BaseController {
	@Autowired
	ITopicService topicService;
	@Autowired
	IReplyService replyService;
	@Autowired
	ILabelService labelService;
	@Autowired
	IndexService indexService;
	
	
	@RequestMapping("/post/all/{name}")
	public String toQA(Model model,@PathVariable String name,HttpServletRequest request){
		
		Map map = indexService.index(null);
		if(!StringUtils.isBlank(name)){
			Label label = labelService.queryLabelByName(name);
			
			if(label != null ){
				model.addAttribute("labelid", label.getId());
				model.addAttribute("labelname", label.getName());
			}
		}
		model.addAttribute("static",super.getQiqiuUrl());
		model.addAttribute("staticUrl",super.getQiqiuUrl());
		//软件发布帖子
		model.addAttribute("topics", map.get("topics"));
		//问题帖子
		model.addAttribute("questions", map.get("questions"));
		//标签
		model.addAttribute("labels", map.get("labels"));
		
		return "/web/qa/qa";
	}
	
	@RequestMapping("/post/all")
	public String toQA(Model model){ 
		Map map = indexService.index(null);

		//软件发布帖子
		model.addAttribute("topics", map.get("topics"));
		//问题帖子
		model.addAttribute("questions", map.get("questions"));
		//标签
		model.addAttribute("labels", map.get("labels"));
		model.addAttribute("static",super.getQiqiuUrl());
		model.addAttribute("staticUrl",super.getQiqiuUrl());
		return "/web/qa/qa";
	}
	
	@ResponseBody
	@RequestMapping("/post/page")
	public Message pageQA(Integer pageNum,Integer count,Integer labelId){
		Message result = new Message();
		if(pageNum <1 ){
			result.setMsg("参数错误：pageNum ：" +pageNum);
			result.setStatus(false);
			return result;
		}
		if(count == null || count <0){
			count = 10;
		}
		return topicService.getTopicsByLabelId(labelId, (pageNum-1)*count, count, null);
	}
	
	
	@RequestMapping("/post/{id}")
	public String post(@PathVariable Integer id,ModelMap map,HttpServletRequest request) throws Exception{
		Message<Topic> message = topicService.detail(id);
		Topic result = message.getData();
		//如果帖子不存在 或者已经删除 直接调转错误页面
		if(result == null||result.getStatus() ==StatusEnum.delete ){
			return "redirect:http://"+request.getServerName()+"/404.html";
		}
		if(StringUtils.isNotBlank(result.getExtLabels())){
			String[] allLabel = result.getExtLabels().split(",");
			map.put("extLabels", Arrays.asList(allLabel));
		}
		
		//判断帖子答案并且已经有答案
		if(result.getCategroyName() == CategroyEnum.question && result.getAnswerState() == AnswerState.HaveAnser){
			Reply reply = replyService.queryTopicAnswer(result.getId());
			map.put("answer", reply);
			map.put("answerid", reply.getId());
		}
			
		message.getTopicContent().setMarkdown(((HtmlGetImg.convertcontent(message.getTopicContent().getMarkdown(), super.getQiqiuUrl()))));
		map.put("topicContent", message.getTopicContent());
		map.put("id", id);
		map.put("msg", message);
		User user = (User) request.getSession().getAttribute(CORER.SESSION_USER);//登陆用户
		if(user !=null){
			map.put("userId", user.getId());
		}
		map.put("static", super.getQiqiuUrl());
		topicService.incReadHit(id);
		return "/web/topic/detail/detail";
	}
	
	
	@RequestMapping("/post/{id}/wap")
	public String topicDetailWap(@PathVariable Integer id,ModelMap map,HttpServletRequest request) throws Exception{
		Message<Topic> message = topicService.detail(id);
		Topic result = message.getData();
		//如果帖子不存在 或者已经删除 直接调转错误页面
		if(result == null||result.getStatus() ==StatusEnum.delete ){
			return "redirect:http://"+request.getServerName()+"/404.html";
		}
		if(StringUtils.isNotBlank(result.getExtLabels())){
			String[] allLabel = result.getExtLabels().split(",");
			map.put("extLabels", Arrays.asList(allLabel));
		}
		message.getTopicContent().setMarkdown((HtmlGetImg.convertcontent(message.getTopicContent().getMarkdown(), super.getQiqiuUrl())));
		map.put("topicContent", message.getTopicContent());
		map.put("id", id);
		map.put("msg", message);
		User user = (User) request.getSession().getAttribute(CORER.SESSION_USER);//登陆用户
		if(user !=null){
			map.put("userId", user.getId());
		}
		topicService.incReadHit(id);
		
		map.put("static", super.getQiqiuUrl());
		return "/wap/topicDetal";
	}
	
	/**
	 * 查询用户所有提交帖子
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/post/question")
	public String question(HttpSession session,Model model){
		//登陆用户
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			throw new RuntimeException("用户未登陆");
		}
		
		
		Message<Topic> message = topicService.getTopicsWhoCreate(CategroyEnum.question, user.getId(), -1, 0);
		if(message.isStatus()){
			model.addAttribute("questions", message.getList());
		}
		
		return "/web/user/my/myquestion";
	}
	
	/**
	 * 查询用户所有回复
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/post/answer")
	public String answer(HttpSession session,Model model){
		//登陆用户
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			throw new RuntimeException("用户未登陆");
		}
				
		Map map = replyService.queryUserAllReply(user.getId(), -1, 0);
		model.addAttribute("replys", map.get("replys"));
		model.addAttribute("topics", map.get("topics"));
		return "/web/user/my/myanswerquestion";
	}
}
