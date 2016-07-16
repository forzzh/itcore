package www.core.com.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.dao.BaseDAO;
import www.core.com.pojo.FocusTag;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IFocusTagService;
import www.core.com.service.ILabelService;
import www.core.com.utils.MathHelper;

@Transactional
@Service("labelService")
@Configuration
//������Դ�ļ�
@PropertySource({ "classpath:/config/properties/sys.properties" })
public class LabelServiceImpl implements ILabelService {
	@Resource(name = "hibernateDAO")
	BaseDAO<Label> hibernateDAO;
	@Value("${bookScore}")
	public Integer bookScore=10;
	@Value("${topicScore}")
	public Integer topicScore=10;
	@Value("${groupScore}")
	public Integer groupScore=10;
	@Value("${personScore}")
	public Integer personScore=10;
	@Resource
	IFocusTagService focusTagService;

	@Override
	public Label labelExistence(Label label) {
		label.setName(label.getName().toLowerCase());
		Map<String, String> map = new HashMap<String, String>();
		map.put("name", label.getName());
		Label result = hibernateDAO.findByColumn("from " + Label.class.getSimpleName() + " where lower(name)=:name ",
				map);
		return result;
	}

	@Override
	public Label labelExistenceLock(Label label) {
		Label result = hibernateDAO.jdbcSelectLockRetunBean(
				"select * from syslabel where lower(name)='" + label.getName().toLowerCase() + "' for update",
				Label.class);

		return result;
	}

	public Label labelExistenceByNameLock(String name) {
		Label result = hibernateDAO.jdbcSelectLockRetunBean(
				"select * from syslabel where lower(name)='" + name + "' for update", Label.class);

		return result;
	}

	public Label labelExistenceByIdLock(Integer id) {
		Label result = hibernateDAO.jdbcSelectLockRetunBean
				("select * from syslabel where id='" + id + "' for update",
				Label.class);

		return result;
	}

	
	@Override
	public Message<Label> labelIncfoucsBookByLabelId(Integer labelId) {
		Message<Label> message = new Message<>();
		
		Label label = this.labelExistenceByIdLock(labelId);
		if (label == null) {
			message.setStatus(false);
			message.setMsg("û�д˱�ǩ");
			return message;
		}
		
		Integer oldCount = label.getFoucsBook();
		if (oldCount == null) {
			oldCount = 0;
		}
		label.setFoucsBook(oldCount + 1);
		label.setScore(MathHelper.add(MathHelper.add(MathHelper.mul(label.getFoucsGroup(), groupScore), MathHelper.mul(personScore	, label.getFoucsPerson())), MathHelper.mul(bookScore, label.getFoucsBook())));
		hibernateDAO.saveOrUpdate(label);
		message.setStatus(true);
		message.setMsg("���ϴ˱�ǩ���鼮�������ӳɹ�");
		
		return message;

	}

	@Override
	public Label addNewLable(Label label, Integer userId) {
		User user = new User();
		user.setId(userId);
		label.setUser(user);
		hibernateDAO.saveOrUpdate(label);
		return label;
	}

	@Override
	public Message<Label> addNewLableManager(Label label, Integer userId) {

		label.setName(label.getName().toLowerCase());

		if (labelExistence(label) == null) {
			label.setFoucsBook(0);
			label.setFoucsGroup(0);
			label.setTopics(0);
			label.setFoucsPerson(0);
			User user = new User();
			user.setId(userId);
			label.setUser(user);
			hibernateDAO.saveOrUpdate(label);
			Message<Label> labelMsg = new Message<>();
			labelMsg.setMsg("��ӳɹ�");
			labelMsg.setData(label);
			labelMsg.setStatus(true);
			return labelMsg;
		} else {
			Message<Label> labelMsg = new Message<>();
			labelMsg.setMsg("��ǩ�Ѵ���");
			labelMsg.setStatus(false);
			return labelMsg;
		}

	}

	@Override
	public void labelDeleteGroupDeleteLock(Integer id) {
		// TODO Auto-generated method stub
		Label result = hibernateDAO.jdbcSelectLockRetunBean("select * from syslabel where id=" + id + " for update",
				Label.class);
		if (result != null) {
			result.setFoucsGroup(result.getFoucsGroup() - 1);
			result.setScore(MathHelper.add(MathHelper.add(MathHelper.mul(result.getFoucsGroup(), groupScore), MathHelper.mul(personScore	, result.getFoucsPerson())), MathHelper.mul(bookScore, result.getFoucsBook())));
			hibernateDAO.saveOrUpdate(result);
		}

	}

	@Override
	public Label labelUpdateOrAddPersonAndGroup(Label label, Integer userid) {
		// TODO Auto-generated method stub
		Label result = this.labelExistenceLock(label);
		if (result != null) {
			result.setFoucsGroup(result.getFoucsGroup() + 1);
			// �����ǰ����û�û�й�ע�������ǩ ��ô�͹�ע������1
			if (focusTagService.foucusLabel(userid, result) == null) {
				result.setFoucsPerson((result.getFoucsPerson()==null?0:label.getFoucsGroup()) + 1);
			}
			label.setScore(MathHelper.add(MathHelper.mul(label.getFoucsGroup()==null?0:label.getFoucsGroup(), groupScore), MathHelper.mul(personScore	, label.getFoucsPerson()==null?0:label.getFoucsPerson())));
			//
			hibernateDAO.saveOrUpdate(result);
			return result;
		} else {
			// ��ʼ��Ϊ1
			label.setFoucsGroup(1);
			label.setFoucsPerson(1);
			label.setTopics(0);
			label.setScore(MathHelper.add(MathHelper.mul(label.getFoucsGroup(), groupScore), MathHelper.mul(personScore	, label.getFoucsPerson())));
			this.addNewLable(label, userid);

			// ����֮��Ĭ�Ϲ�ע
			focusTagService.saveFoucusLabel(userid, label);
			;
			return label;
		}
	}

	@Override
	public Label labelUpdateOrAddPersonAndGroupByTopic(Label label, Integer userid) {
		// TODO Auto-generated method stub

		Label result = this.labelExistenceLock(label);
		if (result != null) {
			// �����ǰ����û�û�й�ע�������ǩ ��ô�͹�ע������1
			if (focusTagService.foucusLabel(userid, result) == null) {
				result.setFoucsPerson(result.getFoucsPerson() + 1);
			}
			result.setTopics(result.getTopics()==null ?0:result.getTopics() + 1);
			hibernateDAO.saveOrUpdate(result);
			return result;
		} else {
			// ��ʼ��Ϊ1
			label.setFoucsGroup(0);
			label.setFoucsPerson(1);
			label.setTopics(1);
			this.addNewLable(label, userid);
			// ����֮��Ĭ�Ϲ�ע
			focusTagService.saveFoucusLabel(userid, label);
			;
			return label;
		}
	}

	@Override
	public Message<Label> setLabelName(String newName, Label label) {

		Message<Label> labelMsg;

		label.setName(label.getName().toLowerCase());

		Label oldLabel = labelExistence(label);

		if (oldLabel == null) {
			labelMsg = new Message<>();
			labelMsg.setMsg("��ǩ������");
			labelMsg.setStatus(false);
			return labelMsg;
		}
		oldLabel.setName(newName);
		Label newLabel = labelExistence(label);

		if (newLabel != null) {
			labelMsg = new Message<>();
			labelMsg.setMsg("�±�ǩ�Ѵ���");
			labelMsg.setStatus(false);
			return labelMsg;
		}
		hibernateDAO.saveOrUpdate(oldLabel);

		labelMsg = new Message<>();
		labelMsg.setMsg("�޸ı�ǩ�ɹ�");
		labelMsg.setStatus(true);
		return labelMsg;
	}

	@Override
	public Label labelExistencebyId(Integer labelId) {
		Label result = hibernateDAO.findByColumn(Label.class, "id", labelId);
		return result;
	}

	@Override
	public Message<List<Label>> queryLabelPageData(Integer pageNum, Integer count) {
		Message<List<Label>> result = new Message<>();
		List list = queryLabelData((pageNum - 1)*count,count);
		result.setStatus(true);
		result.setMsg("��ѯ�ɹ���");
		result.setData(list);
		return result;
	}
	

	@Override
	public List<Label> queryLabelData(Integer start, int rows) {
		String hql = " from " + Label.class.getSimpleName() + " ORDER BY score DESC";
		List<Label> list = hibernateDAO.getListByPage(hql, null, start, rows);
		return list;
	}


	@Override
	public Message<Label> getLabelsWhoCreate(Integer user, Integer start, Integer row) {
		Message<Label> message = new Message<>();

		// ��label�в���������������
		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("userid", user);

		List<Label> labels = hibernateDAO.getListByPageByObject("from Label where user.id = :userid", hqlMap, start,
				row);

		if (labels.isEmpty()) {
			message.setMsg("�޴����ı�ǩ");
			message.setStatus(false);
			return message;
		}

		message.setMsg("��ѯ���ݿ�ɹ�");
		message.setStatus(true);
		message.setList(labels);

		return message;
	}
	@Override
	//��˭д�İ����������ֵ�Ǹ�ë�߰���ֱ���� Message<Label> Ȼ��setList������
	public Message<List<Label>> queryLabelNamePage(Integer pageNum,
			Integer count,boolean flag,Integer userid) {
		Message<List<Label>> result = new Message<>();
		List<Label> list = this.queryList(pageNum, count);
		if(flag){
			for (Label label : list) {
				FocusTag focus = focusTagService.foucusLabel(userid, label);
				if(focus!=null){
					label.setShowData(String.valueOf((focus.getId())));
				}
			}
		}
		result.setStatus(true);
		result.setMsg("��ѯ�ɹ���");
		result.setData(list);
		return result;
	}
	
	public List<Label> queryList(Integer pageNum,
			Integer count){
		String hql = "select new Label(l.name,l.id,l.foucsPerson) from " + Label.class.getSimpleName() + " as l ORDER BY createDate DESC";
		List<Label> list = hibernateDAO.getListByPage(hql, null, count * (pageNum - 1), count);
		return list;
	}
	/**
	 * �����ȡ20����ǩ
	 * @param num Ҫ��ȡ�ı�ǩ��
	 */
	public List<Label> getRandomLabelList(Integer num){
		List<Label> randomLabel = hibernateDAO.getListByPageByObject("from Label order by rand()", null, 0, num);
		return randomLabel;		
	}

	@Override
	public Message<List<Label>> queryLabelByName(String name,boolean flag ,Integer userid) {
		
		Message<List<Label>> result = new Message<>();
		String hql ="select  new Label(l.name,l.id,l.foucsPerson) from " + Label.class.getSimpleName() + " as l where name like '%"+name.trim()+"%' ORDER BY score DESC";
		List<Label> list = hibernateDAO.getListByName(hql);
		if(flag){
			for (Label label : list) {
				FocusTag focus = focusTagService.foucusLabel(userid, label);
				if(focus!=null){
					label.setShowData(String.valueOf((focus.getId())));
				}
			}
		}
		result.setStatus(true);
		result.setMsg("��ѯ�ɹ���");
		result.setData(list);
		return result;
	}

	@Override
	public List<Label> getHotLabels() {
		String hql = " from " +Label.class.getSimpleName() + " order by foucsBook desc";
		List<Label> list = hibernateDAO.getListByPage(hql, null, 0, 15);
		return list;
	}

	/**
	 * �������Ʋ�ѯ����
	 * @param labelname ��ǩ����
	 */
	@Override
	public Label queryLabelByName(String labelname) {
		return 	hibernateDAO.findByColumn(Label.class, "name", labelname);
	}

}
