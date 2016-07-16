
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
		
		User user = (User) request.getSession().getAttribute(CORER.SESSION_USER);//��½�û�
	
		if (user == null) {
			message = new Message<Topic>();
			message.setMsg("������Ϊ��");
			return message;
		}
		
		//���Ӵ�����
		topic.setUser(user);
		
		if (StringUtils.isBlank(topic.getTitle())) {
			message = new Message<Topic>();
			message.setMsg("���ⲻ��Ϊ��");
			return message;
		}
	

		if (topic.getLabel() == null) {
			message = new Message<Topic>();
			message.setMsg("��ǩ����Ϊ��");
			return message;
		}

		if (StringUtils.isBlank(detail.getContent())) {
			message = new Message<Topic>();
			message.setMsg("�������ݲ���Ϊ��");
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
			message.setMsg("����idΪ��");
			return message;
		}
		if (StringUtils.isBlank(content)) {
			message = new Message<Topic>();
			message.setMsg("����idΪ��");
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
			message.setMsg("��������Ϊ��");
			return message;
		}
		return  topicService.search(search,page);
	}

	
	/**
	 * �ظ�����
	 * @param topicid    ����id
	 * @param CategroyEnum ���ӷ�������
	 * @param content	  �ظ�����
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reply")
	public Message<Topic> replyTopic(HttpServletRequest request,Integer topicid,Integer groupid,CategroyEnum categroyEnum, String content,HttpSession session){
		Message message = new Message();
		User user = (User) session.getAttribute(CORER.SESSION_USER);//��½�û�
		if(user == null){
			message.setStatus(false);
			message.setMsg("�û�δ��¼");
			return message;
		}
		//�ظ�����Ϊ���ӻظ�
		ReplyEnum replayEnum = ReplyEnum.TopicReply;
		
		if(topicid == null){
			message.setStatus(false);
			message.setMsg("����id����Ϊ��");
			return message;
		}
		
		if(StringUtils.isBlank(content)){
			message.setStatus(false);
			message.setMsg("�ظ����ݲ���Ϊ��");
			return message;
		}
		
		if(categroyEnum == null){
			message.setStatus(false);
			message.setMsg("�������Ͳ���Ϊ��");
			return message;
		}

		message = topicService.addReply(topicid,groupid,user,replayEnum,content,categroyEnum);
		return message;
	}
	
	/**
	 * ��ѯ��ӦuserId�������ļ�������
	 * @param userId Ҫ��ѯ���û�ID
	 * @param start 
	 * @param row
	 * @return
	 */
	@ResponseBody
	@RequestMapping("getTecTopicsWhoCreate")
	public Message<Topic> getTopicsWhoCreate(Integer userId, Integer start, Integer row) {
		Message<Topic> message = new Message<>();
		if (userId == null) {
			message.setMsg("userId����Ϊ��");
			message.setStatus(false);
			return message;
		}
		
		return topicService.getTopicsWhoCreate(CategroyEnum.topic,userId, (start - 1) * row, row);
	}
	
	/**
	 * Ϊ�������Ӵ�
	 * @param topicId ����id
	 * @param replyId �ظ�id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addAnser")
	public Message<Topic> addTopicAnser(Integer topicId,Integer replyId){
		Message<Topic> message = new Message<>();
		if(topicId == null){
			message.setStatus(false);
			message.setMsg("����id����Ϊ��");
			return message;
		}
		
		if(replyId == null){
			message.setStatus(false);
			message.setMsg("�ظ�id����Ϊ��");
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
			//�����������
			List topics = (List) map.get("topics");
			model.addAttribute("topics", topics);
			
			//������������
			List questions = (List) map.get("questions");
			model.addAttribute("questions", questions);
			
			//��ǩ
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
		//�û�����
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if(user==null){
			map.addAttribute("show",false);
			return "redirect:http://"+request.getServerName()+"/user/login?redirect=/topic/addTopic"; 
		}else{
			List newIndexLabels =  labelService.queryLabelData(0,10);
			map.addAttribute("newIndexLabels", newIndexLabels);
			map.addAttribute("show",true);
			//�û�Ȧ����Ϣ
			List<Group> groups = groupPersonService.getGroupByUser(user.getId());
			map.addAttribute("userGroup", groups);
		}
		CategroyEnum[] categroyEnums = CategroyEnum.values();
		map.addAttribute("categroyEnums", categroyEnums);		
		return "/web/topic/addTopic";
	}
	
	
	/**
	 * ɾ��ָ���� topicId
	 * @param session
	 * @param topicId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteTopic")
	public Message<Topic> deleteTopic(HttpSession session,Integer topicId){
		
		Message<Topic> message = new Message<>();
		User user = (User) session.getAttribute(CORER.SESSION_USER);//��½�û�
		if(user == null){
			message.setStatus(false);
			message.setMsg("�û�δ��¼");
			return message;
		}
		if(user.getId() == null){
			message.setStatus(false);
			message.setMsg("�û���Ϣ����");
			return message;
		}
		if(topicId == null){
			message.setStatus(false);
			message.setMsg("����ID����Ϊ��");
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
		
		//�û�����
		
		
		return "/web/topic/modifyTopic";
	}
	
}

