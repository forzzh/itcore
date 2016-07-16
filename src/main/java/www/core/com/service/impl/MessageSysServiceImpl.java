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
	 * 增加消息
	 * 
	 * @param messageSys
	 *            消息对象
	 */
	@Override
	public Message<MessageSys> addMessageSys(MessageSys messageSys) {
		Message message = new Message();
		// 保存
		hibernateDAO.save(messageSys);

		message.setStatus(true);
		message.setMsg("保存成功");
		return message;
	}

	/**
	 * 删除消息
	 * @param msgID 消息id
	 */
	@Override
	public Message<MessageSys> deleteMessageSys(Integer msgID) {
		Message message = new Message();

		MessageSys messageSys = hibernateDAO.findByColumn(MessageSys.class,
				"id", msgID);
		if (messageSys == null) {
			message.setStatus(false);
			message.setMsg("删除消息不存在");
		} else {
			//状态改为删除
			messageSys.setStatus(StatusEnum.delete);
			hibernateDAO.saveOrUpdate(messageSys);
			
			message.setStatus(true);
			message.setMsg("删除成功");
		}
		return message;
	}

	/**
	 * 更新消息状态为已读
	 * @param msgID 消息id
	 */
	@Override
	public Message updateMsgRead(Integer msgID) {
		Message message = new Message();

		MessageSys messageSys = hibernateDAO.findByColumn(MessageSys.class,
				"id", msgID);
		if(messageSys == null){
			message.setStatus(false);
			message.setMsg("更新消息不存在");
		}else if(messageSys.getStatus() == StatusEnum.noread ){
			messageSys.setStatus(StatusEnum.read);
			hibernateDAO.saveOrUpdate(messageSys);
			message.setStatus(true);
			message.setMsg("更新消息已读成功");
		}else{
			message.setStatus(false);
			message.setMsg("消息已处理");
		}
		return message;
	}

	/**
	 * 获取消息列表
	 */
	@Override
	public Message<MessageSys> getMsgList(Integer page, Integer limit, StatusEnum status) {
		Message message = new Message();
		//如果起码页为空，默认为1
		if(page == null){
			page = 1;
		}
		
		if(limit == null){
			limit = 20;
		}
		
		//参数
		Map map = new HashMap();
		//分页起始页
		int start = (page-1)*limit;
		
		StringBuffer sb = new StringBuffer();
		sb.append("from MessageSys msg ");
		
		if(status != null){
			map.put("status", status);
			sb.append(" where msg.status = :status");
		}
		
		List list = hibernateDAO.getListByPage(sb.toString(), map, start, limit);
		
		//数据总数
		String hql = "select count(*) from "+MessageSys.class.getSimpleName()+" msg ";
		if(status != null){
			hql += " where msg.status = :status";
		}
		Long count = hibernateDAO.count(hql, map);

		//数据填充
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
	 * 处理申请消息
	 */
	public Message<MessageSys> agreeMsg(User user,Integer msgID,StatusEnum status) {
		Message message = new Message();

		MessageSys messageSys = hibernateDAO.findByColumn(MessageSys.class,
				"id", msgID);
		if(messageSys == null){
			message.setStatus(false);
			message.setMsg("消息不存在");
		}else{
			MessageType type = messageSys.getType();
			
			//为增加圈子或退出圈子消息
			if(type.equals(MessageType.addGroup)){
				//原消息设为已读并更新
				messageSys.setStatus(StatusEnum.read);
				hibernateDAO.saveOrUpdate(messageSys);
				
				
				
				//申请uesr信息
				String parm = messageSys.getParm2();
				Integer userid = Integer.parseInt(parm);
				User applyUser = userService.userQueryById(userid);
				
				//group信息
				String parm2 = messageSys.getParm1();
				Integer groupid = Integer.parseInt(parm2);
				Group group = groupService.getGroupById(groupid);
				
				//判断登陆用户是否为圈子创建者，只有创建者可以同意加入圈子
				if(user.getId().intValue() != group.getUser().getId()){
					message.setStatus(false);
					message.setMsg("用户无权进行该操作");
					return message;
				}
				//如果同意或者拒绝了 消息类型表示处理并且消息改成已读
				String hql = "update MessageSys set type='READadd' , status='read'  where id="+msgID;
				hibernateDAO.executeUpdate(hql);
				//消息内容
				String content = null;
				if(status ==  StatusEnum.agree){
					Groupperson grouppersion = new Groupperson();
					grouppersion.setGroup(group);
					grouppersion.setUser(applyUser);
					groupPersonService.add(grouppersion);
					
					
					content = "<a href=\"/msg/controller?redirect=/user/other/post/"+user.getId() + "\">" + user.getUser() + "</a> 同意您加入 '<a href=\"/group/detail/"+ group.getId() +"\">" + group.getName() + "</a>' 圈子!";
				}else{
					content = "<a href=\"/msg/controller?redirect=/user/other/post/"+user.getId() + "\">" + user.getUser() + "拒绝您加入 '<a href=\"/group/detail/"+ group.getId() +"\">" + group.getName() + "</a>' 圈子!";
				}
				
				//通知用户结果
				MessageSys usermessage = new MessageSys();
				//消息类型与原消息相同
				usermessage.setType(MessageType.READadd);
				usermessage.setUser(applyUser);//申请用户
				usermessage.setContent(content);
				usermessage.setStatus(StatusEnum.noread);//消息未读
				hibernateDAO.save(usermessage);
				
				message.setStatus(true);
				message.setMsg("消息操作成功");
			}else{
				message.setStatus(false);
				message.setMsg("该消息无法进行同意操作");
			}
		}
		return message;
	}
	
	/**
	 * 用户退出圈子的消息发送
	 * 注:此方法只负责发送消息，并不处理用户退出圈子操作
	 * @param user 退出圈子用户
	 * @param group 圈子
	 */
	public void exitGroupMessage(User user ,Group group){
		MessageSys usermessage = new MessageSys();
		
		usermessage.setType(MessageType.exitGroup);
		usermessage.setUser(group.getUser());//圈子创建者
		usermessage.setContent("用户"+user.getUser() + "退出 '" + group.getName() + "' 圈子!");
		usermessage.setStatus(StatusEnum.noread);//消息未读
		hibernateDAO.save(usermessage);
	}
	
	
	public Message<MessageSys> queryUserMsgList(Integer pageNum, Integer count, Integer userId,StatusEnum status){
		Message<MessageSys> result = new Message<>();
		String hql = null;
		Map param = new HashMap<>();
		
		if(userId == null){
			result.setStatus(false);
			result.setMsg("参数userid为空");
			return result;
		}
		
		param.put("userId", userId);
		//未读
		if(status == StatusEnum.noread){
			hql = " from " + MessageSys.class.getSimpleName() + " message where userId = :userId and status = 'noread' order by createDate desc";
		}
		
		//已读
		if(status == StatusEnum.read){
			hql = " from " + MessageSys.class.getSimpleName() + " message where userId = :userId and status = 'read' order by createDate desc";
		}
		
		//所有
		if(status == null){
			hql = " from " + MessageSys.class.getSimpleName() + " message where userId = :userId order by createDate desc";
		}
			
		try{
			int start = (pageNum-1)*(count-1);
			List<MessageSys> msgList = hibernateDAO.getListByPageByObject(hql, param, start, count);
			result.setList(msgList);
			result.setStatus(true);
		}catch(Exception e){
			result.setMsg("查询错误:" + e.getMessage());
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
		message.setMsg("查询数据成功");
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
		result.setMsg("更新成功");;
		return result;
	}

	@Override
	public Message delteAllMsg(User user) {
		Message result = new Message();

		String hql = "delete from MessageSys where user.id = " + user.getId();
		hibernateDAO.executeUpdate(hql);
		result.setStatus(true);
		result.setMsg("删除成功");;
		return result;
	}

}
