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
// ������Դ�ļ�
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
		// Ȧ����Ϣ
		Group group = hibernateDAO.findByColumn(Group.class, "id", groupid);
		if (group == null || group.getStatus() == StatusEnum.delete || group.getStatus() == StatusEnum.pending) {
			message.setStatus(false);
			message.setMsg("Ȧ�Ӳ�����");
			return message;
		}

		// �����û�
		User user = userService.userQueryById(userid);
		if (user == null || user.getStatus() == UserEnum.delete) {
			message.setStatus(false);
			message.setMsg("�û�������");
			return message;
		}

		// �����û��Ƿ��Ѽ���Ȧ��
		Groupperson groupperson = groupPersonService.getUserInGroup(groupid, userid);
		if (groupperson != null) {
			message.setStatus(false);
			message.setMsg("�û��Ѽ����Ȧ��");
			return message;
		}
		JoinTypeEnum joinType = group.getJoinType();
		// ����Ҫ���
		if (joinType == JoinTypeEnum.noapprove) {

			// �û������Ȧ��
			addGroupPerson(user, group);
			// �����߷�����Ϣ
			MessageSys createMsg = new MessageSys();

			createMsg.setStatus(StatusEnum.noread);// ��Ϣ��Ϊδ��
			createMsg.setUser(group.getUser());// ��Ϣ������ΪȦ�Ӵ�����
			createMsg.setType(MessageType.addGroup);// ��Ϣ����
			createMsg.setParm1(user.getId().toString());// ������id
			createMsg.setParm2(group.getId().toString());// Ȧ��id
			createMsg.setContent("�û��Ѽ������Ȧ��");
			messageSysService.addMessageSys(createMsg);

			// �����߷�����Ϣ
			MessageSys uerMsg = new MessageSys();
			uerMsg.setStatus(StatusEnum.noread);// ��Ϣ��Ϊδ��
			uerMsg.setUser(user);// ��Ϣ������Ϊ�����û�
			createMsg.setType(MessageType.addGroup);// ��Ϣ����
			createMsg.setParm1(group.getId().toString());// Ȧ��id
			createMsg.setParm2(user.getId().toString());// �����û�id
			createMsg.setContent("���ѳɹ�����Ȧ�ӣ�<a href=\"/group/detail/"+group.getId()+"\">"+group.getName()+"</a>");
			message.setMsg("�μӳɹ�");
			messageSysService.addMessageSys(createMsg);

		} else {
			// �����߷�����Ϣ
			MessageSys createMsg = new MessageSys();
			
			String hql = "select count(*) from MessageSys where status='noread' and parm1= '"+group.getId()+"' and parm2='"+user.getId()+"' ";
			Map<String,Object> map = new HashMap();
			map.put("userid", user.getId());

			//�����������1�κ� 
			Long count = hibernateDAO.count(hql, null);
			if(count >= 1){
				message.setStatus(false);
				message.setMsg("�벻Ҫ�ظ�����");
				return message;
			}
			
			createMsg.setStatus(StatusEnum.noread);// ��Ϣ��Ϊδ��
			createMsg.setUser(group.getUser());// ��Ϣ������ΪȦ�Ӵ�����
			createMsg.setType(MessageType.addGroup);// ��Ϣ����
			createMsg.setParm1(group.getId().toString());// Ȧ��id
			createMsg.setParm2(user.getId().toString());// �����û�id
			createMsg.setContent("�û�:<a href=\"/user/other/post/"+user.getId()+"\">"+user.getUser()+"</a>,�������Ȧ��:<a href=\"/group/detail/"+group.getId()+"\">"+group.getName()+"</a>");
			message.setMsg("�Ѿ��ύ�������˴����");
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
		
		// ���group����
		if (groupExistence != null && (StatusEnum.nodelete == groupExistence.getStatus()
				|| StatusEnum.pending == groupExistence.getStatus())) {
			// ���group������δɾ��
			groupMessage.setMsg("Ȧ���Ѵ���");
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
		group.setStatus(StatusEnum.pending);// ��ʾȦ�Ӵ��������״̬
		group.setScore(
				MathHelper.add(MathHelper.mul(1, personScore), MathHelper.mul(group.getTopicsNum(), topicScore)));
		hibernateDAO.saveOrUpdate(group);
		this.addGroupPerson(user, group);// �������Զ������Ȧ��
		groupMessage.setStatus(true);
		groupMessage.setMsg("Ȧ�������ύ�ɹ�������ˣ�");
		// groupMessage.setMsg("����Ȧ�ӳɹ�");
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
		// ���ڱ�ǩ group������һ��

		labelService.labelDeleteGroupDeleteLock(result.getLabel().getId());
		return message;
	}

	/**
	 * �û��˳�Ȧ��
	 * 
	 * @param groupid
	 *            Ȧ��id
	 * @param userid
	 *            �û�id
	 */
	@Override
	public Message<Group> exitGroup(Integer groupid, Integer userid) {
		Message message = new Message();
		Groupperson groupperson = groupPersonService.getUserInGroup(groupid, userid);
		if (groupperson == null) {
			message.setStatus(false);
			message.setMsg("�û���Ȧ�ɹ�");
			return message;
		}
		
		// �˳�Ȧ�Ӳ���
		Map map = new HashMap();
		map.put("groupid", groupid);
		map.put("userid", userid);

		// Ȧ����Ϣ
		Group group = this.groupExistenceLock(groupid);
		
		if(userid == group.getUser().getId()){
			message.setStatus(false);
			message.setMsg("Ⱥ�������˳�Ȧ��");
			return message;
		}
		// �û���Ϣ
		groupPersonService.deleteIsNoLock(groupperson);
		User user = userService.userQueryById(userid);
		// �����߷����˳�Ȧ����Ϣ
		MessageSys createMsg = new MessageSys();

		createMsg.setStatus(StatusEnum.noread);// ��Ϣ��Ϊδ��
		createMsg.setUser(group.getUser());// ��Ϣ������ΪȦ�Ӵ�����
		createMsg.setType(MessageType.exitGroup);// ��Ϣ����
		createMsg.setParm1(group.getId().toString());// Ȧ��id
		createMsg.setParm2(user.getId().toString());// �˳��û�id
		createMsg.setContent("�û����˳�Ȧ��");
		messageSysService.addMessageSys(createMsg);

		message.setStatus(true);
		message.setMsg("�û��˳��ɹ�");
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
			message.setMsg("Ȧ�Ӳ�����");
			return message;
		}

		groupExistence.setDescription(group.getDescription());
		groupExistence.setImage(group.getImage());
		groupExistence.setJoinType(group.getJoinType());

		// // ����������LabelΪ��
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
		message.setMsg("���º��ScoreΪ:" + group.getScore());
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
			message.setMsg("��û��Ȩ��");
			return message;
		}
		Groupperson groupperson = groupPersonService.getUserInGroup(groupId, outUserId);
		if (groupperson == null) {
			message.setStatus(true);
			message.setMsg("�����ɹ�");
			return message;
		}
		groupPersonService.deleteIsNoLock(groupperson);
		message.setStatus(true);
		message.setMsg("�����ɹ�");
		return message;
	}

	/**
	 * ��˼���Ȧ������
	 */
	@Override
	public Message reviewGroup(User user, Integer groupId, StatusEnum statusEnum) {
		Message message = new Message();
		Group group = hibernateDAO.findByColumn(Group.class, "id", groupId);

		if (group == null) {
			message.setStatus(false);
			message.setMsg("Ȧ�Ӳ�����");
			return message;
		}

		if (group.getStatus() != StatusEnum.pending) {
			message.setStatus(false);
			message.setMsg("Ȧ�������޷���ɸò���");
			return message;
		}

		// ͬ������
		if (statusEnum == StatusEnum.agree) {
			group.setStatus(StatusEnum.nodelete);
			hibernateDAO.saveOrUpdate(group);

			// ������Ϣ
			MessageSys messageSys = new MessageSys();
			messageSys.setContent("Ȧ��ͨ������");
			messageSys.setStatus(StatusEnum.noread);// δ��
			messageSys.setType(MessageType.applyGroup);// Ȧ����������
			messageSys.setParm1(group.getId().toString());// Ȧ��id
			messageSys.setParm2(user.getId().toString());// �����
			messageSys.setUser(group.getUser());// ��Ϣ������
			messageSysService.addMessageSys(messageSys);// ������Ϣ
		}

		// ��ͬ��
		if (statusEnum == StatusEnum.noagree) {
			// ���Ȧ���û���
			String hql = "DELETE FROM Groupperson gp WHERE gp.group.id = " + groupId;
			hibernateDAO.executeUpdate(hql);
			// ɾ��Ȧ��
			hibernateDAO.delete(group);

			// ������Ϣ
			MessageSys messageSys = new MessageSys();
			messageSys.setContent("Ȧ��δͨ������");
			messageSys.setStatus(StatusEnum.noread);// δ��
			messageSys.setType(MessageType.applyGroup);// Ȧ����������
			messageSys.setParm1(group.getId().toString());// Ȧ��id
			messageSys.setParm2(user.getId().toString());// �����
			messageSys.setUser(group.getUser());// ��Ϣ������
			messageSysService.addMessageSys(messageSys);// ������Ϣ
		}

		message.setStatus(true);
		message.setMsg("�����ɹ�");
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
			message.setMsg("û��Ȩ���޸�");
			return message;
		}
		group.setDocument(document);
		hibernateDAO.saveOrUpdate(document);
		message.setMsg("�����޸ĳɹ�");
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
		result.setMsg("��ѯ�ɹ���");
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
		result.setMsg("��ѯ�ɹ���");
		result.setList(list);
		return result;
	}

	@Override
	public Message<Group> getRecommendGroup(User user, Boolean isLogin) {

		Message<Group> message = new Message<>();
		

		// ������б������ѡȡ
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
			
			// δ��ע��Ȧ��
			result = hibernateDAO.findByColumn(Group.class, sql);

		}else{
			
			String sql = "select * from  sysgroup  g  order by rand()  limit 0,1 ";


			// ����Ȧ��
			result = hibernateDAO.findByColumn(Group.class, sql);

		}

		// ���ȡһ��
		if (result == null) {
			message.setMsg("���������Ѿ���ע�����е�Ȧ��");
			message.setStatus(true);
			return message;
		}


		//
		message.setData(result);
		message.setMsg("��ȡ�Ƽ�Ȧ�ӳɹ�");
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
