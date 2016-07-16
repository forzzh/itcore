package www.core.com.controller.group;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.Enum.CategroyEnum;
import www.core.com.Enum.StatusEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Group;
import www.core.com.pojo.Groupperson;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.User;
import www.core.com.service.IGroupPersonService;
import www.core.com.service.IGroupService;
import www.core.com.service.ILabelService;
import www.core.com.service.ITopicService;
import www.core.com.service.IUserService;
import www.core.com.utils.CORER;

@Controller
@RequestMapping("/group")
public class GroupController extends BaseController<Group> {

	@Autowired
	IGroupService groupService;

	@Autowired
	ITopicService topicService;
	
	@Autowired
	IGroupPersonService groupPersonService;

	@Autowired
	ILabelService labelService;

	/**
	 * ��֤labelid ��syslabel�Ƿ���� ֻ�д��ڲſ������ ���Ȧ�� group.name ȥ�ж����ݿ��Ƿ������Ȧ������ ����� ��
	 * meesage ��msg �Ѵ��� false ���û�� ֱ�����
	 * 
	 * @param group
	 * @return
	 * @throws IOException 
	 */
	@ResponseBody
	@RequestMapping("/addNewGroup")
	public Message<Group> addNewGroup(Group group, String extLabel) throws IOException {
		Message<Group> message = null;

		if (group.getUser() == null) {
			message = new Message<Group>();
			message.setMsg("������Ϊ��");
			return message;
		}

		if (StringUtils.isBlank(group.getName())) {
			message = new Message<Group>();
			message.setMsg("Ȧ������Ϊ��");
			return message;
		}

		if (group.getLabel() == null
				|| StringUtils.isBlank(group.getLabel().getName())) {
			message = new Message<Group>();
			message.setMsg("��ǩΪ��");
			return message;
		}
		if (StringUtils.isNotBlank(extLabel)) {
			message = groupService.addNewGroup(group,super.getManagerAdmin(),super.getStaticUrl(), super.array_unique(super
					.extLabelUtils(group.getLabel().getName(), extLabel)));
		} else {
			message = groupService.addNewGroup(group,super.getManagerAdmin(),super.getStaticUrl());
		}

		if (message.isStatus()) {
			message.setMsg("�����ɹ�");
			
			//�������ʼ�
			String emailAdrs[] = super.getManagerAdmin().split(",");
			
			for (String eAdr : emailAdrs) {
				super.getMail().sendTo(eAdr).subject("���´�����Ȧ�ӣ������").setHtmlContent("���µ�Ȧ�ӱ������������http://localhost/manager/group/list").send();
			}
			
			// TODO ��ʼ���Զ�����Ȧ��
			// groupService.joinGroup(group, group.getUser());

		} else {
			message.setMsg("����ʧ��");
		}
		return message;
	}

	/**
	 * Ȧ�����
	 * 
	 * @param session
	 * @param groupId
	 *            Ȧ��id
	 * @param statusEnum
	 *            ����
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reviewGroup")
	public Message reviewGroup(HttpSession session, Integer groupId,
			StatusEnum statusEnum) {
		Message message = new Message();
		// ����
		if (groupId == null) {
			message.setStatus(false);
			message.setMsg("Ȧ��id����Ϊ��");
			return message;
		}

		if (statusEnum == null) {
			message.setStatus(false);
			message.setMsg("��������Ϊ��");
			return message;
		}

		// �ж�״̬�Ƿ�Ϸ�
		if (statusEnum != StatusEnum.agree && statusEnum != StatusEnum.noagree) {
			message.setStatus(false);
			message.setMsg("��������");
			return message;
		}

		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if (user == null) {
			message.setStatus(false);
			message.setMsg("�û�δ��½");
			return message;
		}

		message = groupService.reviewGroup(user, groupId, statusEnum);
		return message;
	}

	/**
	 * score=0 focus-person=0 topics=0; 1 �ж����Ȧ���Ƿ���� 2 ɾ��Ȧ���û� group-person
	 * 
	 * ״̬����Ϊ1
	 * 
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteGroup")
	public Message<Group> deleteGroup(Group group) {
		Message<Group> message = null;
		if (group.getId() == null) {
			message = new Message<Group>();
			message.setMsg("id ������");
			return message;
		}

		if (groupService.deleteGroup(group).isStatus()) {
			message = new Message<Group>();
			message.setMsg("ɾ���ɹ�");
		} else {
			message = new Message<Group>();
			message.setMsg("ɾ��ʧ��");
		}
		return message;
	}

	/**
	 * �õ�һ��group��ǰ��������� ���group �����ڣ��򷵻ز�����
	 * 
	 * @param group
	 *            Ҫ��ѯ��Ȧ��
	 * @return Message ��ѯ���
	 */

	@ResponseBody
	@RequestMapping("/getDetail")
	public Message<Group> getDetail(Group group) {
		Message<Group> message;

		message = groupService.detailGroup(group);
		return message;
	}

	/**
	 * 
	 * ���ݻ���ȥ���� ǰhot������
	 * 
	 * hotsҪȡ���������ŵ�Ȧ�� ���numofTop��ֵ�Ƿ����򷵻طǷ���Ϣ
	 * 
	 * ��������Message.list
	 * 
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getHotGroup")
	public Message<Group> getHotGroup(int numofTop) {

		Message<Group> message;

		if (numofTop < 0) {
			message = new Message<Group>();
			message.setMsg("���������0����");
			return message;
		}
		message = groupService.getHotGroup(numofTop);
		return message;

	}

	/**
	 * �޸�group��Ϣ ���ҿ����趨 �μ�Ȧ���Ƿ���Ҫ���
	 * 
	 * @param group
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateGroupInfo")
	public Message<Group> updateGroupInfo(Group group, User user) {
		Message<Group> message = null;

		if (null == group) {
			message = new Message<Group>();
			message.setMsg("null group");
			return message;
		}
		if (null == user) {
			message = new Message<Group>();
			message.setMsg("null user");
			return message;
		}

		if (group.getUser() == null) {
			message = new Message<Group>();
			message.setMsg("������Ϊ��");
			return message;
		}
		// ����ȫ�ٲ���ͨ��������Ϊ�գ���Ϊ�գ����
		if ((user.getId() != null) || (group.getUser().getId() != null)
				|| (group.getUser().getId() != user.getId())) {
			message = new Message<Group>();
			message.setMsg("�޸��˲���Ȧ�Ӵ�����");
			return message;
		}

		if (group.getName() == null) {
			message = new Message<Group>();
			message.setMsg("Ȧ������Ϊ��");
			return message;
		}

		if (group.getLabel() == null
				|| StringUtils.isBlank(group.getLabel().getName())) {
			message = new Message<Group>();
			message.setMsg("��ǩ���Ʋ���Ϊ��");
			return message;
		}
		message = groupService.updateGroupInfo(group);

		if (message.isStatus()) {
			message.setMsg("�޸ĳɹ�");
		} else {
			message.setMsg("�޸�ʧ��");
		}
		return message;
	}

	/**
	 * GroupJoinEnrollList parm1 ��group parm2 �˳�Ȧ�ӵ�userid
	 * 
	 * userid group�Ĵ����˵�userid �˳�Ȧ��
	 * 
	 * @param group
	 * @return
	 */

	@ResponseBody
	@RequestMapping("/addGroup")
	public Message<Group> addGroup(Integer groupid, HttpSession session,HttpServletRequest request) {
		Message<Group> message = new Message<Group>();

		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			
			message.setStatus(false);
			message.setMsg("�û�δ��¼");
			return message;
		}
		
		
		// Ȧ����Ϣ
		if (groupid == null) {
			message.setStatus(false);
			message.setMsg("Ȧ��idΪ��");
			return message;
		}
	
		message = groupService.addGroup(groupid, user.getId());
		return message;
	}

	@ResponseBody
	@RequestMapping("/exitGroup")
	public Message<Group> exitGroup(Integer groupid, Integer userid) {
		Message message = new Message();
		// Ȧ����Ϣ
		if (groupid == null) {
			message.setStatus(false);
			message.setMsg("Ȧ��idΪ��");
			return message;
		}

		if (userid == null) {
			message.setStatus(false);
			message.setMsg("������Ϊ��");
			return message;
		}

		message = groupService.exitGroup(groupid, userid);
		return message;
	}

	/**
	 * ����
	 * 
	 * @param groupid
	 * @param userid
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/outGroup")
	public Message<Group> outGroup(Integer groupId, HttpServletRequest request,
			Integer outUserId) {
		Message<Group> message = null;
		User user = (User) request.getSession()
				.getAttribute(CORER.SESSION_USER);// ��½�û�
		if (user == null) {
			message = new Message<>();
			message.setMsg("�û�δ��¼");
			return message;
		}
		
		if (groupId == null ||outUserId == null) {
			message = new Message<Group>();
			message.setStatus(false);
			message.setMsg("����������");
			return message;
		}
		
		message = groupService.outGroup(groupId, user.getId(), outUserId);
		return message;
	}

	/**
	 * �޸��û�����
	 * 
	 * @param document
	 * @param id
	 * @param userId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateDocument")
	public Message updateDocument(String document, Integer id,
			HttpServletRequest request) {
		Message<Group> message = null;
		User user = (User) request.getSession()
				.getAttribute(CORER.SESSION_USER);// ��½�û�
		if (user != null) {
			message = new Message<>();
			message.setMsg("�û�δ��¼");
			return message;
		}
		return groupService.updateDocument(document, id, user.getId());
	}

	@Autowired
	IUserService userService;
	@RequestMapping("/my")
	public String init(Integer id, ModelMap map,HttpSession session,HttpServletRequest request) {
		
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		map.put("groupid",  user.getId());
		map.addAttribute("userdata",	(userService.userQuery(user.getId())));
		map.addAttribute("userId", user.getId());
		map.addAttribute("show", true);
		return "/web/user/my/mygroup";
	}
	@RequestMapping("/other/{userid}")
	public String othergroup(@PathVariable Integer userid, ModelMap map,HttpSession session,HttpServletRequest request) {
		
		if(userid == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		map.put("groupid",  userid);
		map.addAttribute("userdata",	(userService.userQuery(userid)));
		map.addAttribute("userid", userid);
		map.addAttribute("show", false);
		return "/web/user/my/mygroup";
	}
	@ResponseBody
	@RequestMapping("/focuse")
	public Message focuse(Integer id, Integer pagenum, Integer count) {
		Message<Label> result = new Message<>();
		if (!super.checkLabelPage(pagenum, count).isStatus()) {
			return super.checkLabelPage(pagenum, count);
		}
		result = groupService.getfocus(id, pagenum, count);
		return result;
	}

	@ResponseBody
	@RequestMapping("/createLabel")
	public Message createLabel(Integer id, Integer pagenum, Integer count) {
		Message<Label> result = new Message<>();
		super.checkLabelPage(pagenum, count);
		result = groupService.getCreate(id, pagenum, count);
		return result;
	}

	/**
	 * ����Ȧ��id
	 */
	@RequestMapping("detail/{id}")
	public String detail(ModelMap model,@PathVariable Integer id, HttpSession session){
		
		Group group = groupService.getGroupById(id);
		
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		model.put("group", group);
		
		//��ǩ
		List<String> labels = new ArrayList<String>();
		if (group.getLabel() != null) {
			String label = group.getLabel().getName();
			labels.add(label);
			String extLabels = group.getExtLabels();
			if (StringUtils.isNotBlank(extLabels)) {
				String[] lables = extLabels.split(",");
				List<String> asList = Arrays.asList(lables);
				labels.addAll(asList);
			}
		}
		model.put("labels", labels);
		
		//������������
		Message<List<Topic>> toipcMessage = topicService.getTopicsByGroupId(group.getId(), 0, 8, CategroyEnum.question);
		model.put("questions", toipcMessage.getData());
		
//		�Ƿ�����Ȧ��
		if (user != null) {
			model.put("userId", user.getId());
			if(user.getId().equals(group.getUser().getId())){
				model.put("isGroupOwn","owner");
			}
			Groupperson entity = groupPersonService.getUserInGroup(id, user.getId());
			if(entity!=null){
				model.put("isAdded","add");
			}
		}
		/*Message<User> message = groupPersonService.getUserByGroupId(id, -1, 0);
		model.put("users", message.getList());*/
		model.put("static", super.getQiqiuUrl());
		return "/web/group/detail";
	}

	@ResponseBody
	@RequestMapping("getTopicsByGroupId")
	public Message<List<Topic>> getTopicsByGroupId(Integer groupId,
			Integer start, Integer count) {
		Message<List<Topic>> result = null;

		if (start < 0) {
			result = new Message<>();
			result.setMsg("��������start ��" + start);
			result.setStatus(false);
			return result;
		}
		result = topicService.getTopicsByGroupId(groupId, start, count, null);
		return result;
	}

	@RequestMapping("/list")
	public String list(ModelMap map) {
		// ����Ҳ����ű�ǩ
		List<Label> labels = labelService.getHotLabels();
		// ��������б�
		List<Topic> adTopics = topicService.getHotTopicByCategroyName(
				CategroyEnum.project, 0, 5);
		// ���Żش��б�
		List<Topic> requestTopics = topicService.getHotTopicByCategroyName(
				CategroyEnum.question, 0, 5);
		map.put("static", super.getQiqiuUrl());
		map.put("labels", labels);
		map.put("adTopics", adTopics);
		map.put("requestTopics", requestTopics);
		return "/web/group/list";
	}

	@ResponseBody
	@RequestMapping("/getRecommendGroup")
	public Message<Group> getRecommendGroup(HttpServletRequest request) {
		Message<Group> message = new Message<>();

		Boolean isLogin;
		User user = (User) request.getSession()
				.getAttribute(CORER.SESSION_USER);

		if (user != null) {
			isLogin = true;
		} else {
			isLogin = false;
		}

		return groupService.getRecommendGroup(user, isLogin);
	}

	@ResponseBody
	@RequestMapping("/getListGroup")
	public Message<Group> getListGroup(Integer pageNow, Integer count) {
		Message<Group> message = new Message<>();
		if (!super.checkLabelPage(pageNow, count).isStatus()) {
			return super.checkLabelPage(pageNow, count);
		}
		return groupService.getlistGroup(pageNow, count);
	}

	@ResponseBody
	@RequestMapping("/searchByName")
	public Message<Group> searchByName(String name) {
		Message<Group> message = new Message<>();
		if (null == name || "".equals(name)) {
			message.setMsg("��ѯ��������Ϊ��");
			message.setStatus(false);
			return message;
		}
		message = groupService.searchByname(name);
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/addGroupByUser")
	public Message<Group> addGroupByUser(Group group) throws IOException{
		Message<Group> message = new Message<>();
		if(StringUtils.isBlank(group.getDescription())){
			message.setStatus(false);
			message.setMsg("��������description null");
			return message;
		}
		if(StringUtils.isBlank(group.getName())){
			message.setStatus(false);
            message.setMsg("��������null name");	
            return message;
		}
		if(group.getLabel()==null){
			message.setStatus(false);
			message.setMsg("��������null label");
			return message;
		}
		message = groupService.addNewGroup(group,super.getStaticUrl(),super.getStaticUrl());
		if(message .isStatus() ){
			String emailAdrs[] = super.getManagerAdmin().split(",");
			
			for (String eAdr : emailAdrs) {
				super.getMail().sendTo(eAdr).subject("���´�����Ȧ�ӣ������").setHtmlContent("���µ�Ȧ�ӱ������������http://localhost/manager/group/list").send();
			}
		}
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/quitGroup")
	public Message quitGroup(Integer groupId,Integer userId){
		Message result = new Message<>();
		if(groupId == null || userId == null){
			result.setStatus(false);
			result.setMsg("��������");
		}else{
			
			Group group = groupService.getGroupById(groupId);
			Integer createUserId = group.getUser().getId();
			if (userId.compareTo(createUserId) == 0) {
				result.setStatus(false);
				result.setMsg("�����˲����˳���");
				return result;
			}
			
			Groupperson entity = groupPersonService.getUserInGroup(groupId, userId);
			if(entity!=null){
				groupPersonService.delete(entity);
				result.setStatus(true);
				result.setMsg("�ɹ��˳���");
			}else{
				result.setStatus(false);
				result.setMsg("�û�û�м�����飡");
			}
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/queryGroupUser")
	public Message queryGroupUser(Integer pageNum,Integer count,Integer groupId){
		Message<User> result = new Message<>();
		if(groupId == null || pageNum <1 ){
			result.setMsg("��������replyID:" + groupId +" pageNum ��" +pageNum);
			result.setStatus(false);
			return result;
		}
		if(count == null || count <0){
			count = 11;
		}
		int start = (pageNum == 1 )?0:(pageNum-1)*(count-1);
		return groupPersonService.getUserByGroupId(groupId, start, count);
	}
	
	@RequestMapping("/addGroupPage")
	public String addGroupPage(HttpSession session, HttpServletRequest request, ModelMap map) {
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		map.put("userId",  user.getId());
		return "/web/group/addGroup";
	}
}
