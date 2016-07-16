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
					message.setMsg("圈子不存在");
					return message;
				}
				if (group.getStatus() == StatusEnum.delete) {
					message.setStatus(false);
					message.setMsg("添加失败，圈子已删除");
					return message;
				}
				
				if (!groupPersonService.isUserInGroup(topic.getGroup().getId(), topic.getUser().getId())) {
					message.setStatus(false);
					message.setMsg("添加失败，您不是该圈子成员");
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
		message.setMsg("添加成功");

		try {
			for (String img : imgs.getImages()) {
				uploader.upload(staticPath + img, img.replace("/temp", "/topic"));
			}
		} catch (IOException e) {
			for (String img : imgs.getImages()) {
				uploader.delete(img.replace("/temp", "/topic"));
			}
			throw new Exception("帖子添加失败");
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

		// 软件发布帖子
		List topics = topicService.getTopics(CategroyEnum.project, 0, 6);
		map.put("topics", topics);

		// 技术问题帖子
		List questions = topicService.getTopics(CategroyEnum.question, 0, 7);
		map.put("questions", questions);

		// 标签
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
					throw new Exception("帖子添加失败");
				}
			}

		}
		message.setStatus(true);
		message.setMsg("更新成功");
		return message;
	}

	/**
	 * 回复帖子
	 * 
	 * @param id
	 *            传入id,如果第二个参数为0，表示为帖子id,code为1表示为图书回复
	 * @param replyUser
	 *            表示回复用户 ，一般为当前登陆用户
	 * @param replyEnum
	 *            回复类型
	 * @param content
	 *            回复内容
	 * @param categroyEnum
	 *            帖子类型
	 */
	@Override
	public Message<Topic> addReply(Integer id, Integer groupid, User replyUser, ReplyEnum replyEnum, String content,
			CategroyEnum categroyEnum) {
		Message message = null;

		// 帖子回复
		if (replyEnum == ReplyEnum.TopicReply) {
			message = topicReply(id, groupid, replyUser, content, categroyEnum);
		} else {// 图书回复
			message = bookReply(id, groupid, replyUser, content);
		}

		// 给帖子或book关注人发送通知
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
			// 发送消息
			MessageSys messageSys = new MessageSys();

			messageSys.setStatus(StatusEnum.noread);// 未读
			messageSys.setUser(user);// 消息接收者
			messageSys.setParm1(id.toString());// 关注的帖子或book的id
			messageSys.setParm2(user.getId().toString());//
			messageSys.setType(MessageType.newReplyBook);// book回复
			hibernateDAO.save(messageSys);// 保存消息

			String cos = null;
			if (replyEnum == ReplyEnum.TopicReply) {
				cos = "您关注的帖子:<a href=\"/msg/controller?redirect=/post/" + id + "&msgid=" + messageSys.getId() + "\">"
						+ tp.getTitle() + "</a> 有新的回复";// 回复者的回复内容
			} else if (replyEnum == ReplyEnum.BookReply) {
				cos = "您关注的图书:<a href=\"/msg/controller?redirect=/book/detail/" + id + "&msgid=" + messageSys.getId()
						+ "\">" + bk.getName() + "</a> 有新的回复";
			}
			messageSys.setContent(cos);// 消息内容

			hibernateDAO.saveOrUpdate(messageSys);// 更新
		}
		return message;
	}

	/**
	 * 图书回复
	 * 
	 * @return
	 */
	private Message bookReply(Integer bookId, Integer groupid, User replyUser, String content) {
		Message message = new Message();
		// 图书回复
		ReplyEnum replyEnum = ReplyEnum.BookReply;
		Book book = (Book) hibernateDAO.findByColumn(Book.class, "id", bookId);
		// 保存回复
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
		message.setMsg("回复成功");
		return message;
	}

	/**
	 * 帖子回复
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private Message topicReply(Integer tocpicId, Integer groupid, User replyUser, String content,
			CategroyEnum categroyEnum) {
		ReplyEnum replyEnum = ReplyEnum.TopicReply;// 帖子回复

		Message message = new Message();
		// 帖子
		Topic topic = (Topic) hibernateDAO
				.jdbcSelectLockRetunBean("select * from systopic where id=" + tocpicId + " for update", Topic.class);

		// 帖子不存在或者已删除
		if (topic == null || topic.getStatus() == StatusEnum.delete) {
			message.setStatus(false);
			message.setMsg("帖子不存在");
			return message;
		}

		// 更新帖子信息
		topic.setLastReplyUserId(replyUser);// 更新最后回复者
		int replycount = 1;
		if (topic.getReplys() != null) {
			replycount = topic.getReplys() + 1;
		}
		topic.setReplys(replycount);// 回复数+1
		hibernateDAO.saveOrUpdate(topic);

		// 保存回复
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
		// 检查回复中是否有@人
		checkReply(reply, topic);
		hibernateDAO.save(reply);

		// 给帖子创建者发送消息
		MessageSys messageSys = new MessageSys();

		messageSys.setStatus(StatusEnum.noread);// 未读
		messageSys.setType(MessageType.newReply);
		messageSys.setParm1(tocpicId.toString());
		messageSys.setParm2(replyUser.getId().toString());
		messageSys.setUser(topic.getUser());// 创建者
		hibernateDAO.save(messageSys);

		String cos = "您的帖子:<a href=\"/msg/controller?redirect=/post/" + tocpicId + "&msgid=" + messageSys.getId()
				+ "\">" + topic.getTitle() + "</a> 有新的回复";
		messageSys.setContent(cos);// 回复者的回复内容
		hibernateDAO.saveOrUpdate(messageSys);

		message.setStatus(true);
		message.setMsg("帖子回复成功");
		return message;
	}

	@Override
	public Topic getTopicById(Integer topicId) {
		return (Topic) hibernateDAO.findByColumn(Topic.class, "id", topicId);
	}

	/**
	 * 过滤回复内容，检查是否有@人
	 * 
	 * @param reply
	 */
	private void checkReply(Reply reply, Topic topic) {
		// 回复内容
		String content = reply.getContent();
		content = StringUtils.replace(content, " ", " ");// 中文空格替换为英文空格

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

		// 存放用户信息
		Set set = new HashSet();

		for (int i = 1; i < array.length; i++) {
			String name = array[i];

			// 直接空串开头的跳过
			if (name.startsWith(" ")) {
				continue;
			}

			// 整体为空串跳过
			if (StringUtils.isEmpty(name.trim()) || "".equals(name.trim()))
				continue;

			// 判断@的结束位置
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

		// 查询用户发送消息
		Iterator it = set.iterator();
		while (it.hasNext()) {
			String name = (String) it.next();

			User u = (User) hibernateDAO.findByColumn(User.class, "user", name);
			if (u != null) {

				content = content.replaceAll("@" + name,
						"<a href='/user/other/post/" + u.getId() + "'>@" + name + "</a>");
				MessageSys messageSys = new MessageSys();

				messageSys.setStatus(StatusEnum.noread);// 未读
				messageSys.setType(MessageType.newReply);// 新回复
				messageSys.setParm1(reply.getUser().getId().toString());// 发起@的用户的id
				messageSys.setParm2(reply.getReplyID().toString());// 所在帖子的id
				messageSys.setUser(u);// 被@的人
				hibernateDAO.save(messageSys);

				messageSys.setContent("<a href=\"/msg/controller?redirect=/post/" + topic.getId() + "&msgid="
						+ messageSys.getId() + "\">" + topic.getTitle() + "</a>有人@你: " + reply.getContent());// 回复者的回复内容
				hibernateDAO.saveOrUpdate(messageSys);
			}
		}

		// 还原回复
		for (String tempdata : list) {
			content = content.replace(tempdata, tempdata.replace("&&&", "@"));
		}
		// 替换原回复内容
		reply.setContent(content);
	}

	/*
	 * 查询对应用户ID和对应帖子类型的帖子
	 */
	public Message<Topic> getTopicsWhoCreate(CategroyEnum categroyEnum, Integer userId, Integer start, Integer row) {
		Message<Topic> message = new Message<>();

		// 在label中查找所有满足的情况
		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("userid", userId);
		hqlMap.put("categroyName", categroyEnum);

		List<Topic> topics = hibernateDAO.getListByPageByObject(
				"from Topic where user.id = :userid and categroyName = :categroyName and status='nodelete'", hqlMap,
				start, row);

		// 删除被删除的
		// for (Iterator<Topic> it = topics.iterator(); it.hasNext();) {
		// if (StatusEnum.delete.equals(it.next().getStatus())) {
		// it.remove();
		// }
		// }

		if (topics.isEmpty()) {
			message.setMsg("无创建的帖子");
			message.setStatus(false);
			return message;
		}
		message.setMsg("查询数据库成功");
		message.setStatus(true);
		message.setList(topics);

		return message;
	}

	/**
	 * 查询相关帖子
	 * 
	 * @param categroyEnum
	 *            帖子类型
	 * @param start
	 *            起始页
	 * @param row
	 *            每页显示数据
	 * @return
	 */
	public List<Topic> getTopics(CategroyEnum categroyEnum, Integer start, Integer row) {

		// 在label中查找所有满足的情况
		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("categroyName", categroyEnum);

		List<Topic> topics = hibernateDAO.getListByPageByObject(
				"from Topic where categroyName = :categroyName and status='nodelete'", hqlMap, start, row);

		// 删除被删除的
		// for (Iterator<Topic> it = topics.iterator(); it.hasNext();) {
		// // 如果topic状态为delete 或者 pending(审核中)时不进行显示
		// if (StatusEnum.delete.equals(it.next().getStatus())) {
		// it.remove();
		// }
		// }

		return topics;
	}

	/**
	 * 帖子增加答案
	 */
	@Override
	public Message<Topic> addTopicAnser(Integer topicId, Integer replyId) {
		Message message = new Message();

		// 帖子
		Topic topic = (Topic) hibernateDAO.findByColumn(Topic.class, "id", topicId);
		if (topic == null) {
			message.setStatus(false);
			message.setMsg("帖子不存在");
			return message;
		}

		// 只有技术帖子才需要解答
		if (topic.getCategroyName() != CategroyEnum.question) {
			message.setStatus(false);
			message.setMsg("该帖子不需要解答");
			// 改变帖子解答状态为不需要解答
			topic.setAnswerState(AnswerState.NoAnser);
			hibernateDAO.saveOrUpdate(topic);
			return message;
		}

		// 是否已经存在答案
		if (topic.getAnswerState() == AnswerState.HaveAnser) {
			message.setStatus(false);
			message.setMsg("一个帖子只能有一个答案");
			return message;
		}

		// 回复
		Reply reply = (Reply) hibernateDAO.findByColumn(Reply.class, "id", replyId);
		if (reply == null) {
			message.setStatus(false);
			message.setMsg("该回复不存在");
			return message;
		}

		// 标记帖子为答案已存在
		topic.setAnswerState(AnswerState.HaveAnser);
		hibernateDAO.saveOrUpdate(topic);
		// 保存答案
		TopicAnser topicAnser = new TopicAnser();
		topicAnser.setCategroyEnum(topic.getCategroyName());
		topicAnser.setTopic(topic);
		topicAnser.setReply(reply);
		topicAnser.setUser(reply.getUser());
		hibernateDAO.save(topicAnser);

		message.setStatus(true);
		message.setMsg("操作成功");

		return message;
	}

	@Override
	public void incReadHit(Integer topicId) {

		hibernateDAO.jdbcUpdate("update systopic set hits=hits+1 where id = " + topicId + " ");

	}

	/**
	 * 获取非图书帖子中hits最多的帖子
	 * 
	 * @param num
	 *            获取数量
	 * @return
	 */
	public List getHotTopics(Integer num) {
		Map map = new HashMap();
		map.put("categroyName", CategroyEnum.project);
		return hibernateDAO.getListByPageByObject(" from Topic where categroyName <> :categroyName order by hits", map,
				0, num);
	}

	/**
	 * 获取用户所有帖子
	 * 
	 * @param userId
	 *            用户id
	 * @param start
	 *            起始条数
	 * @param row
	 *            显示条数
	 * @return
	 */
	public List getUserTopics(Integer userId, Integer start, Integer row) {
		Map<String, Object> hqlMap = new HashMap<>();
		hqlMap.put("userid", userId);

		List<Topic> topics = hibernateDAO.getListByPageByObject(
				"from Topic where user.id = :userid and status='nodelete' ORDER BY createDate desc", hqlMap, start,
				row);
		//
		// // 删除被删除的
		// for (Iterator<Topic> it = topics.iterator(); it.hasNext();) {
		// if (StatusEnum.delete.equals(it.next().getStatus())) {
		// it.remove();
		// }
		// }

		return topics;
	}

	public List<Topic> getHotTopicByCategroyName(CategroyEnum categroyEnum, Integer start, Integer row) {
		// 在label中查找所有满足的情况
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
		// group不为空的话
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
		} else {// 为空查询所有
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
		map.put("status", StatusEnum.nodelete);// 未删除状态
		if (categroyEnum != null) {
			map.put("categroyName", categroyEnum);
		}

		// label不为空的话
		if (labelId != null) {
			map.put("labelid", labelId);

			String hql = null;
			if (categroyEnum != null) {
				hql = "from Topic where label.id = :labelid and categroyName =:categroyName and status=:status order by createDate desc";
			} else {
				hql = "from Topic where label.id = :labelid and status=:status order by createDate desc";
			}

			topics = hibernateDAO.getListByPageByObject(hql, map, start, count);
		} else {// 为空查询所有
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
			message.setMsg("没有这个帖子");
			return message;
		}

		Integer topicUserId = topic.getUser().getId();
		if (topicUserId.equals(user.getId())) {
			topic.setStatus(StatusEnum.delete);
			hibernateDAO.saveOrUpdate(topic);
			
			//使topicsNum减一
			Group group = topic.getGroup();
			if (group != null) {
				Group groupLock = groupService.groupExistenceLock(group.getId());
				Integer topicsNum = groupLock.getTopicsNum();
				groupLock.setTopicsNum(topicsNum - 1);
				hibernateDAO.saveOrUpdate(groupLock);
			}
			
			message.setStatus(true);
			message.setMsg("删除成功");
			return message;
		}
		message.setStatus(false);
		message.setMsg("删除失败");
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
