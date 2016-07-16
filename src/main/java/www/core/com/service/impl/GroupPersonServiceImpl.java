package www.core.com.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Group;
import www.core.com.pojo.Groupperson;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IGroupPersonService;

@Service("groupPersonService")
@org.springframework.transaction.annotation.Transactional
public class GroupPersonServiceImpl implements IGroupPersonService {

	@Resource(name = "hibernateDAO")
	BaseDAO<Groupperson> hibernateDAO;

	public GroupPersonServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isUserInGroup(Integer groupid, Integer userid) {
		if (groupid == null || userid == null) {
			return false;
		}

		String hql = "select count(*) from " + Groupperson.class.getSimpleName()
				+ " gp where gp.group.id = :groupid and  gp.user.id = :userid ";
		Map map = new HashMap();
		map.put("groupid", groupid);
		map.put("userid", userid);

		Long count = hibernateDAO.count(hql, map);
		if (count == 0) {
			return false;
		}
		return true;
	}

	public void lock(Groupperson person) {
		hibernateDAO.jdbcSelectLock("select * from sysgroup where id=" + person.getGroup().getId());
	}
	@Value("${bookScore}")
	public Integer bookScore=10;
	@Value("${topicScore}")
	public Integer		topicScore=10;
	@Value("${groupScore}")
	public Integer		groupScore=10;
	@Value("${personScore}")
	public Integer		personScore=10;
	@Override
	public Groupperson add(Groupperson person) {
		// TODO Auto-generated method stub
		lock(person);
		hibernateDAO.jdbcUpdate(
				"update sysgroup set foucsPersonNum=foucsPersonNum+1 , score=score+"+personScore+" where id = " + person.getGroup().getId() + " ");
		hibernateDAO.save(person);
		return person;
	}

	@Override
	public Groupperson delete(Groupperson person) {
		// TODO Auto-generated method stub
		lock(person);
		hibernateDAO.jdbcUpdate(
				"update sysgroup set foucsPersonNum=foucsPersonNum-1 , score=score-"+personScore+"   where id = " + person.getGroup().getId());
		hibernateDAO.delete(person);
		return person;
	}
	public Groupperson deleteIsNoLock(Groupperson person) {
		// TODO Auto-generated method stub
		hibernateDAO.jdbcUpdate(
				"update sysgroup set foucsPersonNum=foucsPersonNum-1, score=score-"+personScore+" where id = " + person.getGroup().getId());
		hibernateDAO.delete(person);
		return person;
	}

	@Override
	public Groupperson getUserInGroup(Integer groupid, Integer userid) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("groupid", groupid);
		map.put("userid", userid);
		Groupperson result = hibernateDAO.findByColumn(
				"from " + Groupperson.class.getSimpleName() + " where groupid=:groupid and userId=:userid", map);
		return result;
	}

	@Override
	public Message getGroupByUserId(Integer userId) {
		Map<String, String> map = new HashMap<String, String>();
		String hql = "from " + Groupperson.class.getSimpleName() + " as groupperson where groupperson.user.id="+userId+" and groupperson.group.status='nodelete'";
//		map.put("userId", String.valueOf(userId));
		List list = hibernateDAO.getListByPage(hql, map, -1, -1);
		Message message = new Message<Groupperson>();
		message.setData(list);
		message.setMsg("查询成功");
		message.setStatus(true);
		return message;
	}

	/* *
	 * 得到关注groupid的所有用户
	 *
	 * 	 
	 */
	@Override
	public Message<User> getUserByGroupId(Integer groupid, Integer start, Integer row) {
		Message<User> message = new Message<>();

		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("groupid", groupid);
		List<User> groupPersons = hibernateDAO.getListByPageByObject("select gp.user from Groupperson gp where gp.group.id = :groupid", hqlMap, start, row);
		
		if (groupPersons.isEmpty()) {
			message.setMsg("圈子中没有用户");
			message.setStatus(false);
			message.setList(Collections.<User> emptyList());
			return message;
		}
		
		message.setMsg("查询数据库成功");
		message.setStatus(true);
		message.setList(groupPersons);
		
		return message;
	}
	
	/**
	 * 获取用户加入的圈子
	 * @param userid
	 * @return
	 */
	@Override
	public List<Group> getGroupByUser(Integer userid){
		String hql = "select pgroup.group from " + Groupperson.class.getSimpleName() + " pgroup where pgroup.user.id="+userid+" and pgroup.group.status='nodelete'";
		return hibernateDAO.getListByPage(hql, null, -1, 0);
	}

	@Override
	public Message<Group> getGroupByUserid(Integer userid, int i, Integer row) {
		Message<Group> message = new Message<>();

		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("userid", userid);
		List<Group> groupPersons = hibernateDAO.getListByPageByObject("select gp.group from Groupperson gp where gp.user.id = :userid and gp.group.status='nodelete'", hqlMap, i, row);
		
		if (groupPersons.isEmpty()) {
			message.setMsg("您还没有关注圈子");
			message.setStatus(false);
			return message;
		}
		
		message.setMsg("查询数据库成功");
		message.setStatus(true);
		message.setList(groupPersons);
		
		return message;
	}

	@Override
	public Message getCreateGroupByUserId(Integer userid) {
		Map<String, String> map = new HashMap<String, String>();
		String hql = "from " + Group.class.getSimpleName() + " as group where group.user.id="+userid+" and group.status='nodelete'";
		List list = hibernateDAO.getListByPage(hql, map, -1, -1);
		Message message = new Message<Groupperson>();
		message.setData(list);
		message.setMsg("查询成功");
		message.setStatus(true);
		return message;
	}

}
