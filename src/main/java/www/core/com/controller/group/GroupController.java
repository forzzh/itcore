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
	 * 验证labelid 在syslabel是否存在 只有存在才可以添加 添加圈子 group.name 去判断数据库是否有这个圈子名称 如果有 就
	 * meesage 放msg 已存在 false 如果没有 直接入库
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
			message.setMsg("创建者为空");
			return message;
		}

		if (StringUtils.isBlank(group.getName())) {
			message = new Message<Group>();
			message.setMsg("圈子名称为空");
			return message;
		}

		if (group.getLabel() == null
				|| StringUtils.isBlank(group.getLabel().getName())) {
			message = new Message<Group>();
			message.setMsg("标签为空");
			return message;
		}
		if (StringUtils.isNotBlank(extLabel)) {
			message = groupService.addNewGroup(group,super.getManagerAdmin(),super.getStaticUrl(), super.array_unique(super
					.extLabelUtils(group.getLabel().getName(), extLabel)));
		} else {
			message = groupService.addNewGroup(group,super.getManagerAdmin(),super.getStaticUrl());
		}

		if (message.isStatus()) {
			message.setMsg("创建成功");
			
			//给管理发邮件
			String emailAdrs[] = super.getManagerAdmin().split(",");
			
			for (String eAdr : emailAdrs) {
				super.getMail().sendTo(eAdr).subject("有新创建的圈子，请审核").setHtmlContent("有新的圈子被创建，请访问http://localhost/manager/group/list").send();
			}
			
			// TODO 创始者自动加入圈子
			// groupService.joinGroup(group, group.getUser());

		} else {
			message.setMsg("创建失败");
		}
		return message;
	}

	/**
	 * 圈子审核
	 * 
	 * @param session
	 * @param groupId
	 *            圈子id
	 * @param statusEnum
	 *            操作
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reviewGroup")
	public Message reviewGroup(HttpSession session, Integer groupId,
			StatusEnum statusEnum) {
		Message message = new Message();
		// 检验
		if (groupId == null) {
			message.setStatus(false);
			message.setMsg("圈子id不能为空");
			return message;
		}

		if (statusEnum == null) {
			message.setStatus(false);
			message.setMsg("操作不能为空");
			return message;
		}

		// 判断状态是否合法
		if (statusEnum != StatusEnum.agree && statusEnum != StatusEnum.noagree) {
			message.setStatus(false);
			message.setMsg("操作出错");
			return message;
		}

		User user = (User) session.getAttribute(CORER.SESSION_USER);
		if (user == null) {
			message.setStatus(false);
			message.setMsg("用户未登陆");
			return message;
		}

		message = groupService.reviewGroup(user, groupId, statusEnum);
		return message;
	}

	/**
	 * score=0 focus-person=0 topics=0; 1 判断这个圈子是否存在 2 删除圈子用户 group-person
	 * 
	 * 状态更新为1
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
			message.setMsg("id 不存在");
			return message;
		}

		if (groupService.deleteGroup(group).isStatus()) {
			message = new Message<Group>();
			message.setMsg("删除成功");
		} else {
			message = new Message<Group>();
			message.setMsg("删除失败");
		}
		return message;
	}

	/**
	 * 得到一个group当前加入的人数 如果group 不存在，则返回不存在
	 * 
	 * @param group
	 *            要查询的圈子
	 * @return Message 查询结果
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
	 * 根据积分去排序 前hot多少条
	 * 
	 * hots要取多少条热门的圈子 如果numofTop数值非法，则返回非法信息
	 * 
	 * 结果存放在Message.list
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
			message.setMsg("请输入大于0的数");
			return message;
		}
		message = groupService.getHotGroup(numofTop);
		return message;

	}

	/**
	 * 修改group消息 并且可以设定 参加圈子是否需要审核
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
			message.setMsg("创建者为空");
			return message;
		}
		// 三个全假才能通过，即不为空，不为空，相等
		if ((user.getId() != null) || (group.getUser().getId() != null)
				|| (group.getUser().getId() != user.getId())) {
			message = new Message<Group>();
			message.setMsg("修改人不是圈子创建者");
			return message;
		}

		if (group.getName() == null) {
			message = new Message<Group>();
			message.setMsg("圈子名称为空");
			return message;
		}

		if (group.getLabel() == null
				|| StringUtils.isBlank(group.getLabel().getName())) {
			message = new Message<Group>();
			message.setMsg("标签名称不能为空");
			return message;
		}
		message = groupService.updateGroupInfo(group);

		if (message.isStatus()) {
			message.setMsg("修改成功");
		} else {
			message.setMsg("修改失败");
		}
		return message;
	}

	/**
	 * GroupJoinEnrollList parm1 是group parm2 退出圈子的userid
	 * 
	 * userid group的创建人的userid 退出圈子
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
			message.setMsg("用户未登录");
			return message;
		}
		
		
		// 圈子信息
		if (groupid == null) {
			message.setStatus(false);
			message.setMsg("圈子id为空");
			return message;
		}
	
		message = groupService.addGroup(groupid, user.getId());
		return message;
	}

	@ResponseBody
	@RequestMapping("/exitGroup")
	public Message<Group> exitGroup(Integer groupid, Integer userid) {
		Message message = new Message();
		// 圈子信息
		if (groupid == null) {
			message.setStatus(false);
			message.setMsg("圈子id为空");
			return message;
		}

		if (userid == null) {
			message.setStatus(false);
			message.setMsg("参与者为空");
			return message;
		}

		message = groupService.exitGroup(groupid, userid);
		return message;
	}

	/**
	 * 提人
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
				.getAttribute(CORER.SESSION_USER);// 登陆用户
		if (user == null) {
			message = new Message<>();
			message.setMsg("用户未登录");
			return message;
		}
		
		if (groupId == null ||outUserId == null) {
			message = new Message<Group>();
			message.setStatus(false);
			message.setMsg("参数不完整");
			return message;
		}
		
		message = groupService.outGroup(groupId, user.getId(), outUserId);
		return message;
	}

	/**
	 * 修改用户公告
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
				.getAttribute(CORER.SESSION_USER);// 登陆用户
		if (user != null) {
			message = new Message<>();
			message.setMsg("用户未登录");
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
	 * 传入圈子id
	 */
	@RequestMapping("detail/{id}")
	public String detail(ModelMap model,@PathVariable Integer id, HttpSession session){
		
		Group group = groupService.getGroupById(id);
		
		User user = (User) session.getAttribute(CORER.SESSION_USER);
		model.put("group", group);
		
		//标签
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
		
		//技术问题帖子
		Message<List<Topic>> toipcMessage = topicService.getTopicsByGroupId(group.getId(), 0, 8, CategroyEnum.question);
		model.put("questions", toipcMessage.getData());
		
//		是否加入该圈子
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
			result.setMsg("参数错误：start ：" + start);
			result.setStatus(false);
			return result;
		}
		result = topicService.getTopicsByGroupId(groupId, start, count, null);
		return result;
	}

	@RequestMapping("/list")
	public String list(ModelMap map) {
		// 获得右侧热门标签
		List<Label> labels = labelService.getHotLabels();
		// 热门软件列表
		List<Topic> adTopics = topicService.getHotTopicByCategroyName(
				CategroyEnum.project, 0, 5);
		// 热门回答列表
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
			message.setMsg("查询条件不能为空");
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
			message.setMsg("参数错误：description null");
			return message;
		}
		if(StringUtils.isBlank(group.getName())){
			message.setStatus(false);
            message.setMsg("参数错误：null name");	
            return message;
		}
		if(group.getLabel()==null){
			message.setStatus(false);
			message.setMsg("参数错误：null label");
			return message;
		}
		message = groupService.addNewGroup(group,super.getStaticUrl(),super.getStaticUrl());
		if(message .isStatus() ){
			String emailAdrs[] = super.getManagerAdmin().split(",");
			
			for (String eAdr : emailAdrs) {
				super.getMail().sendTo(eAdr).subject("有新创建的圈子，请审核").setHtmlContent("有新的圈子被创建，请访问http://localhost/manager/group/list").send();
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
			result.setMsg("参数错误！");
		}else{
			
			Group group = groupService.getGroupById(groupId);
			Integer createUserId = group.getUser().getId();
			if (userId.compareTo(createUserId) == 0) {
				result.setStatus(false);
				result.setMsg("创建人不能退出！");
				return result;
			}
			
			Groupperson entity = groupPersonService.getUserInGroup(groupId, userId);
			if(entity!=null){
				groupPersonService.delete(entity);
				result.setStatus(true);
				result.setMsg("成功退出！");
			}else{
				result.setStatus(false);
				result.setMsg("用户没有加入该组！");
			}
		}
		return result;
	}
	@ResponseBody
	@RequestMapping("/queryGroupUser")
	public Message queryGroupUser(Integer pageNum,Integer count,Integer groupId){
		Message<User> result = new Message<>();
		if(groupId == null || pageNum <1 ){
			result.setMsg("参数错误：replyID:" + groupId +" pageNum ：" +pageNum);
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
