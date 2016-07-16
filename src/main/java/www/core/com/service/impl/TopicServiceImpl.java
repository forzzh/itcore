package www.core.com.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.queryParser.ParseException;
import org.markdown4j.Markdown4jProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.Enum.*;
import www.core.com.dao.BaseDAO;
import www.core.com.dao.imp.BaseImpl;
import www.core.com.pojo.Book;
import www.core.com.pojo.Group;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.MessageSys;
import www.core.com.pojo.Reply;
import www.core.com.pojo.Topic;
import www.core.com.pojo.TopicAnser;
import www.core.com.pojo.TopicContent;
import www.core.com.pojo.TopicGetImage;
import www.core.com.pojo.User;
import www.core.com.service.IGroupPersonService;
import www.core.com.service.IGroupService;
import www.core.com.service.ILabelService;
import www.core.com.service.ITopicService;
import www.core.com.utils.HtmlGetImg;
import www.core.com.utils.Page;
import www.core.com.utils.Uploader;

@Service("topicService")
@Transactional(rollbackFor = { Exception.class })
public class TopicServiceImpl implements ITopicService {
	@Autowired
	ILabelService labelService;
	@Resource(name = "hibernateDAO")
	BaseDAO hibernateDAO;

	@Autowired
	IGroupPersonService groupPersonService;
	@Autowired
	IGroupService groupService;
	@Resource
	ITopicService topicService;

	@Override
	public Message<Topic> detailMarkdown(Integer id) {
		Message<Topic> message = new Message<>();
		Topic topic = (Topic) hibernateDAO.findByColumn(Topic.class, "id", id);
		TopicContent content = detailBasic(id);
		message.setData(topic);
		message.setTopicContent(content);
		return message;
	}

	public TopicContent detailBasic(Integer id) {
		return (TopicContent) hibernateDAO.findByColumn(TopicContent.class, "topicId", id);
	}

	@Override
	public Message<Topic> detail(Integer id) {
		Message<Topic> message = new Message<>();
		Topic topic = (Topic) hibernateDAO.findByColumn(Topic.class, "id", id);
		TopicContent content = detailBasic(id);
	
		message.setData(topic);
		message.setTopicContent(content);
		return message;
	}

	public Message<Topic> add(String qiniu, String staticPath, Topic topic, TopicContent detail, User ps,
			String... label) throws Exception {
		Message<Topic> message = new Message<Topic>();
		if (topic.getGroup() != null) {
			if (topic.getGroup().getId() != null) {
				Group group = groupService.groupExistenceLock(topic.getGroup().getId());
				if (group == null || group.getStatus() == StatusEnum.pending) {
					message.setStatus(false);
					message.setMsg("Ȧ�Ӳ�����");
					return message;
				}
				if (group.getStatus() == StatusEnum.delete) {
					message.setStatus(false);
					message.setMsg("���ʧ�ܣ�Ȧ����ɾ��");
					return message;
				}
				
				if (!groupPersonService.isUserInGroup(topic.getGroup().getId(), topic.getUser().getId())) {
					message.setStatus(false);
					message.setMsg("���ʧ�ܣ������Ǹ�Ȧ�ӳ�Ա");
					return message;
				}
				group.setTopicsNum(group.getTopicsNum() + 1);
				hibernateDAO.saveOrUpdate(group);
			} else {
				topic.setGroup(null);
			}
		}

		Label temp = null;
		String extsLabels = "";
		if (label != null) {
			for (String name : label) {
				temp = new Label();
				temp.setName(name);
				extsLabels = extsLabels + name + ",";
				labelService.labelUpdateOrAddPersonAndGroupByTopic(temp, topic.getUser().getId());
			}
		}

		topic.setHits(0);
		topic.setReplys(0);
		topic.setShare(0);
		topic.setExtLabels(extsLabels);

		Label result = labelService.labelUpdateOrAddPersonAndGroupByTopic(topic.getLabel(), topic.getUser().getId());
		topic.setLabel(result);
		detail.setType(1);
		TopicGetImage imgs = HtmlGetImg.content(detail.getContent());
		Uploader uploader = new Uploader();
		String image = topic.getImage();
		if (image != null) {
			uploader.upload(staticPath + image, image.replace("/temp", "/topic"));
			image = image.replace("/temp", "/topic");
			topic.setImage(image);
		}

		detail.setTitle(topic.getTitle());
		int lastIndex = 0;
		Markdown4jProcessor markdown = new Markdown4jProcessor();
		try {
			String simple = HtmlGetImg.getAllText(markdown.process(detail.getContent()));
			if (simple.length() > 40) {
				lastIndex = 40;
			} else {
				lastIndex = simple.length();
			}
			topic.setSimpleContent(simple.substring(0, lastIndex));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		topic.setStatus(StatusEnum.nodelete);
		hibernateDAO.save(topic);
		detail.setTopic(topic);
		detail.setContent(detail.getContent().replaceAll("http://localhost/temp/upload", "/topic/upload")
				.replaceAll("http://www.corer.me/temp/upload", "/topic/upload")
				.replaceAll("http://139.224.28.34/temp/upload", "/topic/upload"));
		detail
		.setMarkdown(detail.getMarkdown().replaceAll("http://localhost/temp/upload", "/topic/upload")
				.replaceAll("http://www.corer.me/temp/upload", "/topic/upload")
				.replaceAll("http://139.224.28.34/temp/upload", "/topic/upload"));
		hibernateDAO.executeSearch(detail);

		message.setStatus(true);
		message.setMsg("��ӳɹ�");

		try {
			for (String img : imgs.getImages()) {
				uploader.upload(staticPath + img, img.replace("/temp", "/topic"));
			}
		} catch (IOException e) {
			for (String img : imgs.getImages()) {
				uploader.delete(img.replace("/temp", "/topic"));
			}
			throw new Exception("�������ʧ��");
		}
		return message;
	}

	@Override
	public Page search(String search, Integer page) throws ParseException {
		return hibernateDAO.getSearch(TopicContent.class, page, search, "title", "content");
	}

	@Override
	public Map searchAll(String search, Integer page) throws ParseException {
		Map map = new HashMap();
		Page pages = hibernateDAO.getSearch(TopicContent.class, page, search, "title", "content");

		// �����������
		List topics = topicService.getTopics(CategroyEnum.project, 0, 6);
		map.put("topics", topics);

		// ������������
		List questions = topicService.getTopics(CategroyEnum.question, 0, 7);
		map.put("questions", questions);

		// ��ǩ
		List labels = labelService.getRandomLabelList(20);
		map.put("labels", labels);

		map.put("pages", pages);
		return map;
	}

	@Override
	public Message<Topic> update(String staticPath, String content, Integer id, String title,String markdown) throws Exception {
		Message<Topic> message = new Message<Topic>();
		Topic result = (Topic) hibernateDAO.findByColumn(Topic.class, "id", id);
		if (result != null && result.getStatus() == StatusEnum.nodelete) {
			TopicContent topicContent = (TopicContent) hibernateDAO.findByColumn(TopicContent.class, "topicid", id);

			if (topicContent != null) {

				topicContent.setTitle(title);
				topicContent.setTopic(new Topic(id));
				TopicGetImage imgs = HtmlGetImg.content(topicContent.getContent());
				StringBuffer newContent = new StringBuffer(content);

				topicContent
						.setContent(newContent.toString().replaceAll("http://localhost/temp/upload", "/topic/upload")
								.replaceAll("http://www.corer.me/temp/upload", "/topic/upload")
								.replaceAll("http://139.224.28.34/temp/upload", "/topic/upload"));
				topicContent
				.setMarkdown(markdown.replaceAll("http://localhost/temp/upload", "/topic/upload")
						.replaceAll("http://www.corer.me/temp/upload", "/topic/upload")
						.replaceAll("http://139.224.28.34/temp/upload", "/topic/upload"));
				topicContent.setType(1);
				hibernateDAO.saveOrUpdate(topicContent);
				Uploader uploader = new Uploader();
				for (String img : imgs.getImages()) {
					uploader.delete(img);
				}
				imgs = HtmlGetImg.content(content);
				try {
					for (String img : imgs.getImages()) {
						uploader.upload(staticPath + img, img.replace("/temp", "/topic"));
					}
				} catch (IOException e) {
					for (String img : imgs.getImages()) {
						uploader.delete(img.replace("/temp", "/topic"));
					}
					throw new Exception("�������ʧ��");
				}
			}

		}
		message.setStatus(true);
		message.setMsg("���³ɹ�");
		return message;
	}

	/**
	 * �ظ�����
	 * 
	 * @param id
	 *            ����id,����ڶ�������Ϊ0����ʾΪ����id,codeΪ1��ʾΪͼ��ظ�
	 * @param replyUser
	 *            ��ʾ�ظ��û� ��һ��Ϊ��ǰ��½�û�
	 * @param replyEnum
	 *            �ظ�����
	 * @param content
	 *            �ظ�����
	 * @param categroyEnum
	 *            ��������
	 */
	@Override
	public Message<Topic> addReply(Integer id, Integer groupid, User replyUser, ReplyEnum replyEnum, String content,
			CategroyEnum categroyEnum) {
		Message message = null;

		// ���ӻظ�
		if (replyEnum == ReplyEnum.TopicReply) {
			message = topicReply(id, groupid, replyUser, content, categroyEnum);
		} else {// ͼ��ظ�
			message = bookReply(id, groupid, replyUser, content);
		}

		// �����ӻ�book��ע�˷���֪ͨ
		Map map = new HashMap();
		map.put("id", id);
		String hql = "select ftb.user from FoucusTopicorBook ftb where ftb.commonId = :id";
		List<User> list = hibernateDAO.getListByPage(hql, map, -1, 0);

		Topic tp = null;
		Book bk = null;

		if (replyEnum == ReplyEnum.TopicReply) {

			tp = (Topic) hibernateDAO.findByColumn(Topic.class, "id", id);
		} else if (replyEnum == ReplyEnum.BookReply) {

			bk = (Book) hibernateDAO.findByColumn(Book.class, "id", id);
		}

		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			// ������Ϣ
			MessageSys messageSys = new MessageSys();

			messageSys.setStatus(StatusEnum.noread);// δ��
			messageSys.setUser(user);// ��Ϣ������
			messageSys.setParm1(id.toString());// ��ע�����ӻ�book��id
			messageSys.setParm2(user.getId().toString());//
			messageSys.setType(MessageType.newReplyBook);// book�ظ�
			hibernateDAO.save(messageSys);// ������Ϣ

			String cos = null;
			if (replyEnum == ReplyEnum.TopicReply) {
				cos = "����ע������:<a href=\"/msg/controller?redirect=/post/" + id + "&msgid=" + messageSys.getId() + "\">"
						+ tp.getTitle() + "</a> ���µĻظ�";// �ظ��ߵĻظ�����
			} else if (replyEnum == ReplyEnum.BookReply) {
				cos = "����ע��ͼ��:<a href=\"/msg/controller?redirect=/book/detail/" + id + "&msgid=" + messageSys.getId()
						+ "\">" + bk.getName() + "</a> ���µĻظ�";
			}
			messageSys.setContent(cos);// ��Ϣ����

			hibernateDAO.saveOrUpdate(messageSys);// ����
		}
		return message;
	}

	/**
	 * ͼ��ظ�
	 * 
	 * @return
	 */
	private Message bookReply(Integer bookId, Integer groupid, User replyUser, String content) {
		Message message = new Message();
		// ͼ��ظ�
		ReplyEnum replyEnum = ReplyEnum.BookReply;
		Book book = (Book) hibernateDAO.findByColumn(Book.class, "id", bookId);
		// ����ظ�
		Reply reply = new Reply();
		reply.setContent(content);
		reply.setReplyID(bookId);
		reply.setUser(replyUser);
		reply.setReplyEnum(replyEnum);
		if (groupid != null) {
			Group group = (Group) hibernateDAO.findByColumn(Group.class, "id", groupid);
			if (group != null) {
				reply.setGroup(group);
			}
		}
		hibernateDAO.save(reply);

		message.setStatus(true);
		message.setMsg("�ظ��ɹ�");
		return message;
	}

	/**
	 * ���ӻظ�
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Message topicReply(Integer tocpicId, Integer groupid, User replyUser, String content,
			CategroyEnum categroyEnum) {
		ReplyEnum replyEnum = ReplyEnum.TopicReply;// ���ӻظ�

		Message message = new Message();
		// ����
		Topic topic = (Topic) hibernateDAO
				.jdbcSelectLockRetunBean("select * from systopic where id=" + tocpicId + " for update", Topic.class);

		// ���Ӳ����ڻ�����ɾ��
		if (topic == null || topic.getStatus() == StatusEnum.delete) {
			message.setStatus(false);
			message.setMsg("���Ӳ�����");
			return message;
		}

		// ����������Ϣ
		topic.setLastReplyUserId(replyUser);// �������ظ���
		int replycount = 1;
		if (topic.getReplys() != null) {
			replycount = topic.getReplys() + 1;
		}
		topic.setReplys(replycount);// �ظ���+1
		hibernateDAO.saveOrUpdate(topic);

		// ����ظ�
		Reply reply = new Reply();
		reply.setContent(content);
		reply.setReplyID(tocpicId);
		reply.setUser(replyUser);
		reply.setReplyEnum(replyEnum);
		if (groupid != null) {
			Group group = (Group) hibernateDAO.findByColumn(Group.class, "id", groupid);
			if (group != null) {
				reply.setGroup(group);
			}
		}
		// ���ظ����Ƿ���@��
		checkReply(reply, topic);
		hibernateDAO.save(reply);

		// �����Ӵ����߷�����Ϣ
		MessageSys messageSys = new MessageSys();

		messageSys.setStatus(StatusEnum.noread);// δ��
		messageSys.setType(MessageType.newReply);
		messageSys.setParm1(tocpicId.toString());
		messageSys.setParm2(replyUser.getId().toString());
		messageSys.setUser(topic.getUser());// ������
		hibernateDAO.save(messageSys);

		String cos = "��������:<a href=\"/msg/controller?redirect=/post/" + tocpicId + "&msgid=" + messageSys.getId()
				+ "\">" + topic.getTitle() + "</a> ���µĻظ�";
		messageSys.setContent(cos);// �ظ��ߵĻظ�����
		hibernateDAO.saveOrUpdate(messageSys);

		message.setStatus(true);
		message.setMsg("���ӻظ��ɹ�");
		return message;
	}

	@Override
	public Topic getTopicById(Integer topicId) {
		return (Topic) hibernateDAO.findByColumn(Topic.class, "id", topicId);
	}

	/**
	 * ���˻ظ����ݣ�����Ƿ���@��
	 * 
	 * @param reply
	 */
	private void checkReply(Reply reply, Topic topic) {
		// �ظ�����
		String content = reply.getContent();
		content = StringUtils.replace(content, " ", " ");// ���Ŀո��滻ΪӢ�Ŀո�

		Pattern p = Pattern.compile(
				"[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
		Matcher m = p.matcher(content);
		List<String> list = new ArrayList();
		while (m.find()) {
			String reg = m.group();

			content = content.replace(reg, reg.replace("@", "&&&"));
			list.add(reg.replace("@", "&&&"));
		}
		String[] array = content.split("@");

		// ����û���Ϣ
		Set set = new HashSet();

		for (int i = 1; i < array.length; i++) {
			String name = array[i];

			// ֱ�ӿմ���ͷ������
			if (name.startsWith(" ")) {
				continue;
			}

			// ����Ϊ�մ�����
			if (StringUtils.isEmpty(name.trim()) || "".equals(name.trim()))
				continue;

			// �ж�@�Ľ���λ��
			if (name.indexOf(" ") != -1) {

				String[] temp = name.split(" ");

				if (temp[0].trim().equals("")) {
					continue;
				}

				set.add(temp[0]);
			} else {
				set.add(name);
			}
		}

		// ��ѯ�û�������Ϣ
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String name = (String) it.next();

			User u = (User) hibernateDAO.findByColumn(User.class, "user", name);
			if (u != null) {

				content = content.replaceAll("@" + name,
						"<a href='/user/other/post/" + u.getId() + "'>@" + name + "</a>");
				MessageSys messageSys = new MessageSys();

				messageSys.setStatus(StatusEnum.noread);// δ��
				messageSys.setType(MessageType.newReply);// �»ظ�
				messageSys.setParm1(reply.getUser().getId().toString());// ����@���û���id
				messageSys.setParm2(reply.getReplyID().toString());// �������ӵ�id
				messageSys.setUser(u);// ��@����
				hibernateDAO.save(messageSys);

				messageSys.setContent("<a href=\"/msg/controller?redirect=/post/" + topic.getId() + "&msgid="
						+ messageSys.getId() + "\">" + topic.getTitle() + "</a>����@��: " + reply.getContent());// �ظ��ߵĻظ�����
				hibernateDAO.saveOrUpdate(messageSys);
			}
		}

		// ��ԭ�ظ�
		for (String tempdata : list) {
			content = content.replace(tempdata, tempdata.replace("&&&", "@"));
		}
		// �滻ԭ�ظ�����
		reply.setContent(content);
	}

	/*
	 * ��ѯ��Ӧ�û�ID�Ͷ�Ӧ�������͵�����
	 */
	public Message<Topic> getTopicsWhoCreate(CategroyEnum categroyEnum, Integer userId, Integer start, Integer row) {
		Message<Topic> message = new Message<>();

		// ��label�в���������������
		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("userid", userId);
		hqlMap.put("categroyName", categroyEnum);

		List<Topic> topics = hibernateDAO.getListByPageByObject(
				"from Topic where user.id = :userid and categroyName = :categroyName and status='nodelete'", hqlMap,
				start, row);

		// ɾ����ɾ����
		// for (Iterator<Topic> it = topics.iterator(); it.hasNext();) {
		// if (StatusEnum.delete.equals(it.next().getStatus())) {
		// it.remove();
		// }
		// }

		if (topics.isEmpty()) {
			message.setMsg("�޴���������");
			message.setStatus(false);
			return message;
		}
		message.setMsg("��ѯ���ݿ�ɹ�");
		message.setStatus(true);
		message.setList(topics);

		return message;
	}

	/**
	 * ��ѯ�������
	 * 
	 * @param categroyEnum
	 *            ��������
	 * @param start
	 *            ��ʼҳ
	 * @param row
	 *            ÿҳ��ʾ����
	 * @return
	 */
	public List<Topic> getTopics(CategroyEnum categroyEnum, Integer start, Integer row) {

		// ��label�в���������������
		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("categroyName", categroyEnum);

		List<Topic> topics = hibernateDAO.getListByPageByObject(
				"from Topic where categroyName = :categroyName and status='nodelete'", hqlMap, start, row);

		// ɾ����ɾ����
		// for (Iterator<Topic> it = topics.iterator(); it.hasNext();) {
		// // ���topic״̬Ϊdelete ���� pending(�����)ʱ��������ʾ
		// if (StatusEnum.delete.equals(it.next().getStatus())) {
		// it.remove();
		// }
		// }

		return topics;
	}

	/**
	 * �������Ӵ�
	 */
	@Override
	public Message<Topic> addTopicAnser(Integer topicId, Integer replyId) {
		Message message = new Message();

		// ����
		Topic topic = (Topic) hibernateDAO.findByColumn(Topic.class, "id", topicId);
		if (topic == null) {
			message.setStatus(false);
			message.setMsg("���Ӳ�����");
			return message;
		}

		// ֻ�м������Ӳ���Ҫ���
		if (topic.getCategroyName() != CategroyEnum.question) {
			message.setStatus(false);
			message.setMsg("�����Ӳ���Ҫ���");
			// �ı����ӽ��״̬Ϊ����Ҫ���
			topic.setAnswerState(AnswerState.NoAnser);
			hibernateDAO.saveOrUpdate(topic);
			return message;
		}

		// �Ƿ��Ѿ����ڴ�
		if (topic.getAnswerState() == AnswerState.HaveAnser) {
			message.setStatus(false);
			message.setMsg("һ������ֻ����һ����");
			return message;
		}

		// �ظ�
		Reply reply = (Reply) hibernateDAO.findByColumn(Reply.class, "id", replyId);
		if (reply == null) {
			message.setStatus(false);
			message.setMsg("�ûظ�������");
			return message;
		}

		// �������Ϊ���Ѵ���
		topic.setAnswerState(AnswerState.HaveAnser);
		hibernateDAO.saveOrUpdate(topic);
		// �����
		TopicAnser topicAnser = new TopicAnser();
		topicAnser.setCategroyEnum(topic.getCategroyName());
		topicAnser.setTopic(topic);
		topicAnser.setReply(reply);
		topicAnser.setUser(reply.getUser());
		hibernateDAO.save(topicAnser);

		message.setStatus(true);
		message.setMsg("�����ɹ�");

		return message;
	}

	@Override
	public void incReadHit(Integer topicId) {

		hibernateDAO.jdbcUpdate("update systopic set hits=hits+1 where id = " + topicId + " ");

	}

	/**
	 * ��ȡ��ͼ��������hits��������
	 * 
	 * @param num
	 *            ��ȡ����
	 * @return
	 */
	public List getHotTopics(Integer num) {
		Map map = new HashMap();
		map.put("categroyName", CategroyEnum.project);
		return hibernateDAO.getListByPageByObject(" from Topic where categroyName <> :categroyName order by hits", map,
				0, num);
	}

	/**
	 * ��ȡ�û���������
	 * 
	 * @param userId
	 *            �û�id
	 * @param start
	 *            ��ʼ����
	 * @param row
	 *            ��ʾ����
	 * @return
	 */
	public List getUserTopics(Integer userId, Integer start, Integer row) {
		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("userid", userId);

		List<Topic> topics = hibernateDAO.getListByPageByObject(
				"from Topic where user.id = :userid and status='nodelete' ORDER BY createDate desc", hqlMap, start,
				row);
		//
		// // ɾ����ɾ����
		// for (Iterator<Topic> it = topics.iterator(); it.hasNext();) {
		// if (StatusEnum.delete.equals(it.next().getStatus())) {
		// it.remove();
		// }
		// }

		return topics;
	}

	public List<Topic> getHotTopicByCategroyName(CategroyEnum categroyEnum, Integer start, Integer row) {
		// ��label�в���������������
		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("categroyName", categroyEnum);
		hqlMap.put("status", StatusEnum.delete);
		List<Topic> topics = hibernateDAO.getListByPageByObject(
				"from Topic where categroyName = :categroyName and status<>:status order by hits desc", hqlMap, start,
				row);
		return topics;
	}

	@Override
	public Message<List<Topic>> getTopicsByGroupId(Integer groupId, Integer start, Integer count,
			CategroyEnum categroyEnum) {
		Message<List<Topic>> message = new Message<List<Topic>>();
		Group group = groupService.getGroupById(groupId);
		List<Topic> topics = null;
		String hql =  null;
		// group��Ϊ�յĻ�
		Map<String, Object> map = new HashMap<>();
		if (group != null) {
			map.put("group", group);
			if(categroyEnum == null){
				hql = "from Topic where group = :group and status='nodelete'  order by createDate desc";
			}else{
				hql = "from Topic where group = :group and categroyName =:categroyName  and status='nodelete'  order by createDate desc";
				map.put("categroyName", categroyEnum);
			}
			topics = hibernateDAO.getListByPageByObject(hql, map, start, count);
		} else {// Ϊ�ղ�ѯ����
			 hql = "from Topic where categroyName =:categroyName  and status='nodelete'  order by createDate desc";
			map.put("categroyName", categroyEnum);
			topics = hibernateDAO.getListByPageByObject(hql, map, start, count);
		}

		message.setStatus(true);
		message.setData(topics);
		return message;
	}

	@Override
	public Message getTopicsByLabelId(Integer labelId, Integer start, Integer count, CategroyEnum categroyEnum) {
		Message<List<Topic>> message = new Message<List<Topic>>();
		List<Topic> topics = null;

		Map<String, Object> map = new HashMap<>();
		map.put("status", StatusEnum.nodelete);// δɾ��״̬
		if (categroyEnum != null) {
			map.put("categroyName", categroyEnum);
		}

		// label��Ϊ�յĻ�
		if (labelId != null) {
			map.put("labelid", labelId);

			String hql = null;
			if (categroyEnum != null) {
				hql = "from Topic where label.id = :labelid and categroyName =:categroyName and status=:status order by createDate desc";
			} else {
				hql = "from Topic where label.id = :labelid and status=:status order by createDate desc";
			}

			topics = hibernateDAO.getListByPageByObject(hql, map, start, count);
		} else {// Ϊ�ղ�ѯ����
			String hql = null;
			if (categroyEnum != null) {
				hql = "from Topic where categroyName =:categroyName and status=:status order by createDate desc";
			} else {
				hql = "from Topic where status=:status order by createDate desc";
			}

			topics = hibernateDAO.getListByPageByObject(hql, map, start, count);
		}

		message.setStatus(true);
		message.setData(topics);
		return message;
	}

	@Override
	public Message<Topic> deleteTopid(User user, Integer topicId) {
		Message<Topic> message = new Message<>();

		Topic topic = this.getTopicById(topicId);

		if (topic == null) {
			message.setStatus(false);
			message.setMsg("û���������");
			return message;
		}

		Integer topicUserId = topic.getUser().getId();
		if (topicUserId.equals(user.getId())) {
			topic.setStatus(StatusEnum.delete);
			hibernateDAO.saveOrUpdate(topic);
			
			//ʹtopicsNum��һ
			Group group = topic.getGroup();
			if (group != null) {
				Group groupLock = groupService.groupExistenceLock(group.getId());
				Integer topicsNum = groupLock.getTopicsNum();
				groupLock.setTopicsNum(topicsNum - 1);
				hibernateDAO.saveOrUpdate(groupLock);
			}
			
			message.setStatus(true);
			message.setMsg("ɾ���ɹ�");
			return message;
		}
		message.setStatus(false);
		message.setMsg("ɾ��ʧ��");
		return message;
	}

	@Override
	public List<Topic> getTopicListByUsers(Integer[] userIds, Integer first,
			Integer count) {
		Map<String, Object> map = new HashMap<>();
		map.put("userIds", userIds);
		String hql = "from Topic where createUserId in :userIds";
		List<Topic> topics = hibernateDAO.getListByPageByObject(hql, map, first, count);
		return topics;
	}

}
