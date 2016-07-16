package www.core.com.service.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.Enum.JoinTypeEnum;
import www.core.com.Enum.MessageType;
import www.core.com.Enum.StatusEnum;
import www.core.com.Enum.UserEnum;
import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Group;
import www.core.com.pojo.Groupperson;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.MessageSys;
import www.core.com.pojo.User;
import www.core.com.service.IGroupPersonService;
import www.core.com.service.IGroupService;
import www.core.com.service.ILabelService;
import www.core.com.service.IMessageSysService;
import www.core.com.service.IUserService;
import www.core.com.utils.MathHelper;
import www.core.com.utils.Uploader;

@Transactional
@Service("groupService")
@Configuration
// 加载资源文件
@PropertySource({ "classpath:/config/properties/sys.properties" })
public class GroupServiceImpl extends AbstractService<Group>implements IGroupService {

	@Resource(name = "hibernateDAO")
	BaseDAO<Group> hibernateDAO;

	@Autowired
	ILabelService labelService;
	@Autowired
	IUserService userService;
	@Autowired
	IGroupPersonService groupPersonService;
	@Autowired
	IMessageSysService messageSysService;

	public void addGroupPerson(User user, Group group) {
		Groupperson groupPerson = new Groupperson();
		groupPerson.setGroup(group);
		groupPerson.setUser(user);
		groupPersonService.add(groupPerson);
	}

	@Override
	public Message<Group> addGroup(Integer groupid, Integer userid) {

		Message<Group> message = new Message<Group>();
		// 圈子信息
		Group group = hibernateDAO.findByColumn(Group.class, "id", groupid);
		if (group == null || group.getStatus() == StatusEnum.delete || group.getStatus() == StatusEnum.pending) {
			message.setStatus(false);
			message.setMsg("圈子不存在");
			return message;
		}

		// 加入用户
		User user = userService.userQueryById(userid);
		if (user == null || user.getStatus() == UserEnum.delete) {
			message.setStatus(false);
			message.setMsg("用户不存在");
			return message;
		}

		// 检验用户是否已加入圈子
		Groupperson groupperson = groupPersonService.getUserInGroup(groupid, userid);
		if (groupperson != null) {
			message.setStatus(false);
			message.setMsg("用户已加入该圈子");
			return message;
		}
		JoinTypeEnum joinType = group.getJoinType();
		// 不需要审核
		if (joinType == JoinTypeEnum.noapprove) {

			// 用户保存进圈子
			addGroupPerson(user, group);
			// 创建者发送消息
			MessageSys createMsg = new MessageSys();

			createMsg.setStatus(StatusEnum.noread);// 消息设为未读
			createMsg.setUser(group.getUser());// 消息接收者为圈子创建者
			createMsg.setType(MessageType.addGroup);// 消息类型
			createMsg.setParm1(user.getId().toString());// 参与用id
			createMsg.setParm2(group.getId().toString());// 圈子id
			createMsg.setContent("用户已加入过该圈子");
			messageSysService.addMessageSys(createMsg);

			// 参与者发送消息
			MessageSys uerMsg = new MessageSys();
			uerMsg.setStatus(StatusEnum.noread);// 消息设为未读
			uerMsg.setUser(user);// 消息接收者为参与用户
			createMsg.setType(MessageType.addGroup);// 消息类型
			createMsg.setParm1(group.getId().toString());// 圈子id
			createMsg.setParm2(user.getId().toString());// 参与用户id
			createMsg.setContent("您已成功加入圈子：<a href=\"/group/detail/"+group.getId()+"\">"+group.getName()+"</a>");
			message.setMsg("参加成功");
			messageSysService.addMessageSys(createMsg);

		} else {
			// 创建者发送消息
			MessageSys createMsg = new MessageSys();
			
			String hql = "select count(*) from MessageSys where status='noread' and parm1= '"+group.getId()+"' and parm2='"+user.getId()+"' ";
			Map<String,Object> map = new HashMap();
			map.put("userid", user.getId());

			//如果连续申请1次后 
			Long count = hibernateDAO.count(hql, null);
			if(count >= 1){
				message.setStatus(false);
				message.setMsg("请不要重复申请");
				return message;
			}
			
			createMsg.setStatus(StatusEnum.noread);// 消息设为未读
			createMsg.setUser(group.getUser());// 消息接收者为圈子创建者
			createMsg.setType(MessageType.addGroup);// 消息类型
			createMsg.setParm1(group.getId().toString());// 圈子id
			createMsg.setParm2(user.getId().toString());// 参与用户id
			createMsg.setContent("用户:<a href=\"/user/other/post/"+user.getId()+"\">"+user.getUser()+"</a>,申请加入圈子:<a href=\"/group/detail/"+group.getId()+"\">"+group.getName()+"</a>");
			message.setMsg("已经提交到创建人处审核");
			messageSysService.addMessageSys(createMsg);
		}
		message.setStatus(true);
		return message;
	}

	@Override
	public Group groupExistence(Group group) {
		String hql = "from " + Group.class.getSimpleName() + " where lower(name)=:name and status='"
				+ String.valueOf(StatusEnum.nodelete) + "'";
		Map<String, String> parm = new HashMap<String, String>();
		parm.put("name", group.getName());
		// System.out.println(String.valueOf(StatusEnum.nodelete));
		// parm.put("status", String.valueOf(StatusEnum.nodelete));
		Group result = hibernateDAO.findByColumn(hql, parm);
		return result;

	}

	@Override
	public Group groupExistenceLock(Integer id) {
		Group result = hibernateDAO.jdbcSelectLockRetunBean("select * from sysgroup where id=" + id + " for update",
				Group.class);

		return result;
	}

	public Group groupExistenceById(Group group) {

		Group result = hibernateDAO.findByColumn(Group.class, "id", (group.getId()));
		return result;
	}

	public Group groupExistenceById(Integer id) {

		Group result = hibernateDAO.findByColumn(Group.class, "id", id);
		return result;
	}

	@Value("${bookScore}")
	public Integer bookScore = 10;
	@Value("${topicScore}")
	public Integer topicScore = 10;
	@Value("${groupScore}")
	public Integer groupScore = 10;
	@Value("${personScore}")
	public Integer personScore = 10;

	@Override
	public Message<Group> addNewGroup(Group group,String admin,String staticPath, String... extLabel) throws IOException {
		Message<Group> groupMessage = new Message<Group>();
		Group groupExistence = this.groupExistence(group);
		
		// 如果group存在
		if (groupExistence != null && (StatusEnum.nodelete == groupExistence.getStatus()
				|| StatusEnum.pending == groupExistence.getStatus())) {
			// 如果group存在且未删除
			groupMessage.setMsg("圈子已存在");
			return groupMessage;

		}
		Label temp = null;
		String extsLabels = "";
		for (String name : extLabel) {
			temp = new Label();
			temp.setName(name);
			extsLabels = extsLabels + name + ",";
			Label label = labelService.labelUpdateOrAddPersonAndGroup(temp, group.getUser().getId());
		}
		Uploader upload = new Uploader();
		String image = group.getImage();
		upload.upload(staticPath + image, image.replace("/temp", "/topic"));
		image = image.replace("/temp", "/topic");
		group.setImage(image);
		group.setTopicsNum(0);
		group.setExtLabels(extsLabels);
		Label label = labelService.labelUpdateOrAddPersonAndGroup(group.getLabel(), group.getUser().getId());
		group.setLabel(label);
		labelService.addNewLable(group.getLabel(), group.getUser().getId());
		User user = userService.userQueryById(group.getUser().getId());
		// group.setScore(0);
		group.setFoucsPersonNum(0);
		group.setTopicsNum(0);
		group.setUser(user);
		group.setCreateDate(new Timestamp(System.currentTimeMillis()));
		// group.setStatus(StatusEnum.nodelete);
		group.setStatus(StatusEnum.pending);// 表示圈子处于审核中状态
		group.setScore(
				MathHelper.add(MathHelper.mul(1, personScore), MathHelper.mul(group.getTopicsNum(), topicScore)));
		hibernateDAO.saveOrUpdate(group);
		this.addGroupPerson(user, group);// 创建人自动加入该圈子
		groupMessage.setStatus(true);
		groupMessage.setMsg("圈子申请提交成功正在审核！");
		// groupMessage.setMsg("创建圈子成功");
		return groupMessage;
	}

	@Override
	public Message<Group> deleteGroup(Group group) {

		Message<Group> message = new Message<>();
		message.setStatus(true);

		Group result = this.groupExistenceById(group.getId());
		if (result == null) {
			message.setStatus(false);
			return message;
		}
		result.setScore(0.0);
		result.setFoucsPersonNum(0);
		result.setTopicsNum(0);
		result.setUser(null);
		result.setDescription(null);
		result.setStatus(StatusEnum.delete);

		hibernateDAO.saveOrUpdate(result);
		// 所在标签 group数量少一个

		labelService.labelDeleteGroupDeleteLock(result.getLabel().getId());
		return message;
	}

	/**
	 * 用户退出圈子
	 * 
	 * @param groupid
	 *            圈子id
	 * @param userid
	 *            用户id
	 */
	@Override
	public Message<Group> exitGroup(Integer groupid, Integer userid) {
		Message message = new Message();
		Groupperson groupperson = groupPersonService.getUserInGroup(groupid, userid);
		if (groupperson == null) {
			message.setStatus(false);
			message.setMsg("用户退圈成功");
			return message;
		}
		
		// 退出圈子操作
		Map map = new HashMap();
		map.put("groupid", groupid);
		map.put("userid", userid);

		// 圈子信息
		Group group = this.groupExistenceLock(groupid);
		
		if(userid == group.getUser().getId()){
			message.setStatus(false);
			message.setMsg("群主不能退出圈子");
			return message;
		}
		// 用户信息
		groupPersonService.deleteIsNoLock(groupperson);
		User user = userService.userQueryById(userid);
		// 创建者发送退出圈子消息
		MessageSys createMsg = new MessageSys();

		createMsg.setStatus(StatusEnum.noread);// 消息设为未读
		createMsg.setUser(group.getUser());// 消息接收者为圈子创建者
		createMsg.setType(MessageType.exitGroup);// 消息类型
		createMsg.setParm1(group.getId().toString());// 圈子id
		createMsg.setParm2(user.getId().toString());// 退出用户id
		createMsg.setContent("用户已退出圈子");
		messageSysService.addMessageSys(createMsg);

		message.setStatus(true);
		message.setMsg("用户退出成功");
		return message;
	}

	@Override
	public Message<Group> detailGroup(Group group) {
		Message<Group> message = new Message<Group>();

		String groupKey = "group" + group.getId();
		ValueWrapper object = super.getCache(groupKey);
		if (object == null) {
			Group groupExistence = this.groupExistenceById(group);

			put(groupKey, groupExistence);
			message.setStatus(true);
			message.setData(groupExistence);
		} else {
			message.setStatus(true);
			message.setData((Group) object.get());
		}
		return message;
	}

	@Override
	public Message<Group> updateGroupInfo(Group group) {
		Message<Group> message = new Message<>();
		Group groupExistence = this.groupExistenceById(group);
		if (groupExistence == null) {
			message.setMsg("圈子不存在");
			return message;
		}

		groupExistence.setDescription(group.getDescription());
		groupExistence.setImage(group.getImage());
		groupExistence.setJoinType(group.getJoinType());

		// // 不允许设置Label为空
		// Label label = group.getLabel();
		// Label result = labelService.labelExistence(label);
		// if (label != null) {
		// groupExistence.setLabel(result);
		// } else {
		// result = labelService.addNewLable(group.getLabel(),).getData();
		// groupExistence.setLabel(result);
		// }

		hibernateDAO.saveOrUpdate(groupExistence);
		message.setStatus(true);

		return message;
	}

	@Override

	public Message<Group> getHotGroup(int numofTop) {
		Message message = new Message<Group>();
		message.setMsg("test");
		return message;
	}

	@Override

	public Message<Group> updateHotGroup(Group group) {
		Message<Group> message = new Message<Group>();
		Group groupExistenceMsg = this.groupExistence(group);
		// group.setScore(groupExistenceMsg.getFoucsPersonNum() * 5 +
		// groupExistenceMsg.getTopicsNum());
		group.setScore(MathHelper.add(MathHelper.mul(group.getFoucsPersonNum(), personScore),
				MathHelper.mul(group.getTopicsNum(), topicScore)));
		this.updateGroupInfo(group);
		message.setMsg("更新后的Score为:" + group.getScore());
		message.setStatus(true);
		return message;
	}

	@Override

	public Message<Group> joinGroup(Group group, User user) {
		Message message = new Message<Group>();
		message.setMsg("test");
		return message;
	}

	@Override
	public Message<Group> quitGroup(Group group, User user) {
		Message message = new Message<Group>();
		message.setMsg("test");
		return message;
	}

	@Override
	public Message<Group> outGroup(Integer groupId, Integer userId, Integer outUserId) {
		Group result = this.groupExistenceLock(groupId);
		Message message = new Message<Group>();
		if (result == null || result.getUser().getId() != userId) {
			message.setStatus(false);
			message.setMsg("你没有权限");
			return message;
		}
		Groupperson groupperson = groupPersonService.getUserInGroup(groupId, outUserId);
		if (groupperson == null) {
			message.setStatus(true);
			message.setMsg("操作成功");
			return message;
		}
		groupPersonService.deleteIsNoLock(groupperson);
		message.setStatus(true);
		message.setMsg("操作成功");
		return message;
	}

	/**
	 * 审核加入圈子申请
	 */
	@Override
	public Message reviewGroup(User user, Integer groupId, StatusEnum statusEnum) {
		Message message = new Message();
		Group group = hibernateDAO.findByColumn(Group.class, "id", groupId);

		if (group == null) {
			message.setStatus(false);
			message.setMsg("圈子不存在");
			return message;
		}

		if (group.getStatus() != StatusEnum.pending) {
			message.setStatus(false);
			message.setMsg("圈子申请无法完成该操作");
			return message;
		}

		// 同意申请
		if (statusEnum == StatusEnum.agree) {
			group.setStatus(StatusEnum.nodelete);
			hibernateDAO.saveOrUpdate(group);

			// 发送消息
			MessageSys messageSys = new MessageSys();
			messageSys.setContent("圈子通过申请");
			messageSys.setStatus(StatusEnum.noread);// 未读
			messageSys.setType(MessageType.applyGroup);// 圈子申请类型
			messageSys.setParm1(group.getId().toString());// 圈子id
			messageSys.setParm2(user.getId().toString());// 审核人
			messageSys.setUser(group.getUser());// 消息接收者
			messageSysService.addMessageSys(messageSys);// 发送消息
		}

		// 不同意
		if (statusEnum == StatusEnum.noagree) {
			// 清除圈子用户表
			String hql = "DELETE FROM Groupperson gp WHERE gp.group.id = " + groupId;
			hibernateDAO.executeUpdate(hql);
			// 删除圈子
			hibernateDAO.delete(group);

			// 发送消息
			MessageSys messageSys = new MessageSys();
			messageSys.setContent("圈子未通过申请");
			messageSys.setStatus(StatusEnum.noread);// 未读
			messageSys.setType(MessageType.applyGroup);// 圈子申请类型
			messageSys.setParm1(group.getId().toString());// 圈子id
			messageSys.setParm2(user.getId().toString());// 审核人
			messageSys.setUser(group.getUser());// 消息接收者
			messageSysService.addMessageSys(messageSys);// 发送消息
		}

		message.setStatus(true);
		message.setMsg("操作成功");
		return message;
	}

	@Override
	public Group getGroupById(Integer groupid) {
		return hibernateDAO.findByColumn(Group.class, "id", groupid);
	}

	@Override
	public Message updateDocument(String document, Integer id, Integer userId) {
		// TODO Auto-generated method stub
		Message message = new Message();
		Group group = this.groupExistenceById(userId);
		if (group.getUser().getId() != userId) {
			message.setStatus(false);
			message.setMsg("没有权限修改");
			return message;
		}
		group.setDocument(document);
		hibernateDAO.saveOrUpdate(document);
		message.setMsg("公告修改成功");
		message.setStatus(true);
		return message;
	}

	@Override
	public Message<Label> getfocus(Integer id, Integer pageNum, Integer count) {
		Message<Label> result = new Message<>();
		String hql = " from FocusTag ft where ft.user = " + id + " ORDER BY ft.createDate DESC";
		System.out.println(hql);
		List<Label> list = hibernateDAO.getListByPage(hql, null, count * (pageNum - 1), (count + 1));
		result.setStatus(true);
		result.setMsg("查询成功！");
		result.setList(list);
		return result;
	}

	@Override
	public Message<Label> getCreate(Integer id, Integer pageNum, Integer count) {
		Message<Label> result = new Message<>();
		String hql = "from " + Label.class.getSimpleName() + " lb where lb.user = " + id
				+ " ORDER BY lb.createDate DESC";
		List<Label> list = hibernateDAO.getListByPage(hql, null, count * (pageNum - 1), (count + 1));
		result.setStatus(true);
		result.setMsg("查询成功！");
		result.setList(list);
		return result;
	}

	@Override
	public Message<Group> getRecommendGroup(User user, Boolean isLogin) {

		Message<Group> message = new Message<>();
		

		// 从这个列表里随机选取
		Group result = null;

		if (isLogin ) {
			String sql = "select * from  sysgroup g ";
			
			
			List<Group> groups = groupPersonService.getGroupByUser(user.getId());
			if(groups !=null &&groups.size()!=0){
				String ids = null;
				for (Group group : groups) {
					ids = group.getId() + ",";
				}
				ids =ids+"-1";
				sql = sql +" where g.id not in("+ids+")  and g.status='nodelete'";
			}else{
				sql = sql +" where    g.status='nodelete'";
			}
			sql = sql+" order by rand()  limit 0,1";
			
			Map<String, Object> map = new HashMap<>();
			map.put("list", groups);
			
			// 未关注的圈子
			result = hibernateDAO.findByColumn(Group.class, sql);

		}else{
			
			String sql = "select * from  sysgroup  g  order by rand()  limit 0,1 ";


			// 所有圈子
			result = hibernateDAO.findByColumn(Group.class, sql);

		}

		// 随机取一个
		if (result == null) {
			message.setMsg("真厉害你已经关注了所有的圈子");
			message.setStatus(true);
			return message;
		}


		//
		message.setData(result);
		message.setMsg("读取推荐圈子成功");
		message.setStatus(true);

		return message;
	}
	
	@Override
	public Message<Group> getlistGroup( Integer pageNow,Integer count) {
		Message<Group> message = new Message<>();
		String hql="from "+Group.class.getSimpleName()+" gp where gp.status='nodelete' ORDER BY gp.createDate DESC";
		List<Group> list = hibernateDAO.getListByPage(hql, null, count*(pageNow-1), (count+1));
		message.setList(list);
		return message;
	}

	@Override
	public Message<Group> searchByname(String name) {
		Message<Group> message = new Message<>();
		String hql="from "+Group.class.getSimpleName()+" gp where gp.status='nodelete' and gp.name like '%"+name.trim()+"%' ORDER BY gp.createDate DESC";
		List<Group> list = hibernateDAO.getListByName(hql);
		message.setList(list);
		message.setStatus(true);
		return message;
	}
}
