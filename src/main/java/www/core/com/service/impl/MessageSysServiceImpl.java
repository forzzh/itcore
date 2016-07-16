package www.core.com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.Enum.MessageType;
import www.core.com.Enum.StatusEnum;
import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Book;
import www.core.com.pojo.Group;
import www.core.com.pojo.Groupperson;
import www.core.com.pojo.Message;
import www.core.com.pojo.MessageSys;
import www.core.com.pojo.User;
import www.core.com.service.IGroupPersonService;
import www.core.com.service.IGroupService;
import www.core.com.service.IMessageSysService;
import www.core.com.service.IUserService;

@Transactional
@Service("messageSysService")
public class MessageSysServiceImpl extends AbstractService implements IMessageSysService {

	@Resource(name = "hibernateDAO")
	BaseDAO<MessageSys> hibernateDAO;
	
	@Resource
	IUserService userService;
	
	@Resource
	IGroupService groupService;
	
	@Resource
	IGroupPersonService groupPersonService;

	/**
	 * ������Ϣ
	 * 
	 * @param messageSys
	 *            ��Ϣ����
	 */
	@Override
	public Message<MessageSys> addMessageSys(MessageSys messageSys) {
		Message message = new Message();
		// ����
		hibernateDAO.save(messageSys);

		message.setStatus(true);
		message.setMsg("����ɹ�");
		return message;
	}

	/**
	 * ɾ����Ϣ
	 * @param msgID ��Ϣid
	 */
	@Override
	public Message<MessageSys> deleteMessageSys(Integer msgID) {
		Message message = new Message();

		MessageSys messageSys = hibernateDAO.findByColumn(MessageSys.class,
				"id", msgID);
		if (messageSys == null) {
			message.setStatus(false);
			message.setMsg("ɾ����Ϣ������");
		} else {
			//״̬��Ϊɾ��
			messageSys.setStatus(StatusEnum.delete);
			hibernateDAO.saveOrUpdate(messageSys);
			
			message.setStatus(true);
			message.setMsg("ɾ���ɹ�");
		}
		return message;
	}

	/**
	 * ������Ϣ״̬Ϊ�Ѷ�
	 * @param msgID ��Ϣid
	 */
	@Override
	public Message updateMsgRead(Integer msgID) {
		Message message = new Message();

		MessageSys messageSys = hibernateDAO.findByColumn(MessageSys.class,
				"id", msgID);
		if(messageSys == null){
			message.setStatus(false);
			message.setMsg("������Ϣ������");
		}else if(messageSys.getStatus() == StatusEnum.noread ){
			messageSys.setStatus(StatusEnum.read);
			hibernateDAO.saveOrUpdate(messageSys);
			message.setStatus(true);
			message.setMsg("������Ϣ�Ѷ��ɹ�");
		}else{
			message.setStatus(false);
			message.setMsg("��Ϣ�Ѵ���");
		}
		return message;
	}

	/**
	 * ��ȡ��Ϣ�б�
	 */
	@Override
	public Message<MessageSys> getMsgList(Integer page, Integer limit, StatusEnum status) {
		Message message = new Message();
		//�������ҳΪ�գ�Ĭ��Ϊ1
		if(page == null){
			page = 1;
		}
		
		if(limit == null){
			limit = 20;
		}
		
		//����
		Map map = new HashMap();
		//��ҳ��ʼҳ
		int start = (page-1)*limit;
		
		StringBuffer sb = new StringBuffer();
		sb.append("from MessageSys msg ");
		
		if(status != null){
			map.put("status", status);
			sb.append(" where msg.status = :status");
		}
		
		List list = hibernateDAO.getListByPage(sb.toString(), map, start, limit);
		
		//��������
		String hql = "select count(*) from "+MessageSys.class.getSimpleName()+" msg ";
		if(status != null){
			hql += " where msg.status = :status";
		}
		Long count = hibernateDAO.count(hql, map);

		//�������
		map.clear();
		map.put("page", page);
		map.put("row", limit);
		map.put("total", count);
		
		message.setStatus(true);
		message.setList(list);
		message.setData(map);
		return message;
	}

	/**
	 * ����������Ϣ
	 */
	public Message<MessageSys> agreeMsg(User user,Integer msgID,StatusEnum status) {
		Message message = new Message();

		MessageSys messageSys = hibernateDAO.findByColumn(MessageSys.class,
				"id", msgID);
		if(messageSys == null){
			message.setStatus(false);
			message.setMsg("��Ϣ������");
		}else{
			MessageType type = messageSys.getType();
			
			//Ϊ����Ȧ�ӻ��˳�Ȧ����Ϣ
			if(type.equals(MessageType.addGroup)){
				//ԭ��Ϣ��Ϊ�Ѷ�������
				messageSys.setStatus(StatusEnum.read);
				hibernateDAO.saveOrUpdate(messageSys);
				
				
				
				//����uesr��Ϣ
				String parm = messageSys.getParm2();
				Integer userid = Integer.parseInt(parm);
				User applyUser = userService.userQueryById(userid);
				
				//group��Ϣ
				String parm2 = messageSys.getParm1();
				Integer groupid = Integer.parseInt(parm2);
				Group group = groupService.getGroupById(groupid);
				
				//�жϵ�½�û��Ƿ�ΪȦ�Ӵ����ߣ�ֻ�д����߿���ͬ�����Ȧ��
				if(user.getId().intValue() != group.getUser().getId()){
					message.setStatus(false);
					message.setMsg("�û���Ȩ���иò���");
					return message;
				}
				//���ͬ����߾ܾ��� ��Ϣ���ͱ�ʾ��������Ϣ�ĳ��Ѷ�
				String hql = "update MessageSys set type='READadd' , status='read'  where id="+msgID;
				hibernateDAO.executeUpdate(hql);
				//��Ϣ����
				String content = null;
				if(status ==  StatusEnum.agree){
					Groupperson grouppersion = new Groupperson();
					grouppersion.setGroup(group);
					grouppersion.setUser(applyUser);
					groupPersonService.add(grouppersion);
					
					
					content = "<a href=\"/msg/controller?redirect=/user/other/post/"+user.getId() + "\">" + user.getUser() + "</a> ͬ�������� '<a href=\"/group/detail/"+ group.getId() +"\">" + group.getName() + "</a>' Ȧ��!";
				}else{
					content = "<a href=\"/msg/controller?redirect=/user/other/post/"+user.getId() + "\">" + user.getUser() + "�ܾ������� '<a href=\"/group/detail/"+ group.getId() +"\">" + group.getName() + "</a>' Ȧ��!";
				}
				
				//֪ͨ�û����
				MessageSys usermessage = new MessageSys();
				//��Ϣ������ԭ��Ϣ��ͬ
				usermessage.setType(MessageType.READadd);
				usermessage.setUser(applyUser);//�����û�
				usermessage.setContent(content);
				usermessage.setStatus(StatusEnum.noread);//��Ϣδ��
				hibernateDAO.save(usermessage);
				
				message.setStatus(true);
				message.setMsg("��Ϣ�����ɹ�");
			}else{
				message.setStatus(false);
				message.setMsg("����Ϣ�޷�����ͬ�����");
			}
		}
		return message;
	}
	
	/**
	 * �û��˳�Ȧ�ӵ���Ϣ����
	 * ע:�˷���ֻ��������Ϣ�����������û��˳�Ȧ�Ӳ���
	 * @param user �˳�Ȧ���û�
	 * @param group Ȧ��
	 */
	public void exitGroupMessage(User user ,Group group){
		MessageSys usermessage = new MessageSys();
		
		usermessage.setType(MessageType.exitGroup);
		usermessage.setUser(group.getUser());//Ȧ�Ӵ�����
		usermessage.setContent("�û�"+user.getUser() + "�˳� '" + group.getName() + "' Ȧ��!");
		usermessage.setStatus(StatusEnum.noread);//��Ϣδ��
		hibernateDAO.save(usermessage);
	}
	
	
	public Message<MessageSys> queryUserMsgList(Integer pageNum, Integer count, Integer userId,StatusEnum status){
		Message<MessageSys> result = new Message<>();
		String hql = null;
		Map param = new HashMap<>();
		
		if(userId == null){
			result.setStatus(false);
			result.setMsg("����useridΪ��");
			return result;
		}
		
		param.put("userId", userId);
		//δ��
		if(status == StatusEnum.noread){
			hql = " from " + MessageSys.class.getSimpleName() + " message where userId = :userId and status = 'noread' order by createDate desc";
		}
		
		//�Ѷ�
		if(status == StatusEnum.read){
			hql = " from " + MessageSys.class.getSimpleName() + " message where userId = :userId and status = 'read' order by createDate desc";
		}
		
		//����
		if(status == null){
			hql = " from " + MessageSys.class.getSimpleName() + " message where userId = :userId order by createDate desc";
		}
			
		try{
			int start = (pageNum-1)*(count-1);
			List<MessageSys> msgList = hibernateDAO.getListByPageByObject(hql, param, start, count);
			result.setList(msgList);
			result.setStatus(true);
		}catch(Exception e){
			result.setMsg("��ѯ����:" + e.getMessage());
			result.setBasic(false);
		}
		return result;
	}

	@Override
	public Message<MessageSys> list(Integer userid, Integer start,
			Integer pageNum) {
		Message<MessageSys> message = new  Message<>();
		String hql="from "+MessageSys.class.getSimpleName()+" as ms where ms.user.id="+userid +" order by createDate desc";
		List<MessageSys> list = hibernateDAO.getListByPage(hql, null, pageNum*(start-1), pageNum+1);
		message.setList(list);
		message.setMsg("��ѯ���ݳɹ�");
		message.setStatus(true);
		return message;
	}

	@Override
	public Message unreadMessageCount(Integer id) {
		Message result = new Message<>();
		String sql  = " select count(*) from "+MessageSys.class.getSimpleName()+" as ms  where ms.user.id= "+id +" and ms.status = 'noread' ";
		result.setStatus(true);
		result.setData(hibernateDAO.count(sql, null));
		return result;
	}

	@Override
	public Message setAllRead(User user) {
		Message result = new Message();

		String hql = "update MessageSys set status ='read' where user.id = " + user.getId();
		hibernateDAO.executeUpdate(hql);
		result.setStatus(true);
		result.setMsg("���³ɹ�");;
		return result;
	}

	@Override
	public Message delteAllMsg(User user) {
		Message result = new Message();

		String hql = "delete from MessageSys where user.id = " + user.getId();
		hibernateDAO.executeUpdate(hql);
		result.setStatus(true);
		result.setMsg("ɾ���ɹ�");;
		return result;
	}

}
