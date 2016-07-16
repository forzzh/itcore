package www.core.com.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




import org.aspectj.weaver.ast.HasAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.core.com.Enum.ModelEnum;
import www.core.com.Enum.StatusEnum;
import www.core.com.pojo.Book;
import www.core.com.pojo.FoucusTopicorBook;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.User;
import www.core.com.service.IBooKService;
import www.core.com.service.IFocusTopicOrBookService;
import www.core.com.service.ITopicService;
import www.core.com.service.IUserService;

@Service("focusTopicOrBookService")
public class FocusTopicOrBookServiceImpl extends AbstractService<FoucusTopicorBook> implements IFocusTopicOrBookService {

	@Autowired
	IUserService userService;
	@Autowired
	IBooKService bookService;
	@Autowired
	ITopicService topicService;
	
	@Override
	public Message focusTopicOrBook(User user, Integer commonId, String type) {
		Message result = new Message<>();
		boolean isAllreadyFocus = checkBookOrTopicIsAllreadyFocus(user.getId(),commonId,type);
		if(isAllreadyFocus){
			result.setMsg("已被关注过");
			result.setStatus(false);
			return result;
		}
		
		FoucusTopicorBook entity = new FoucusTopicorBook();
		entity.setCommonId(commonId);
		entity.setUser(user);
		//1:书籍 0：帖子
		if("1".equals(type)){
			Book book = bookService.bookExistencebyId(commonId);
			if(book == null){
				result.setStatus(false);
				result.setMsg("书本不存在或已被删除，无法关注！");
				return result;
			}
			entity.setModel(ModelEnum.book);
		}else if("0".equals(type)){
			Topic topic = topicService.getTopicById(commonId);
			//帖子不存在或帖子已被删除
			if (topic == null || topic.getStatus() == StatusEnum.delete) {
				result.setStatus(false);
				result.setMsg("帖子不存在或已被删除，无法关注！");
				return result;
			}
			entity.setModel(ModelEnum.topic);
			entity.setCategroyEnum(topic.getCategroyName());
		}
		hibernateDAO.save(entity);
		
		result.setMsg("关注成功！");
		result.setStatus(true);
		return result;
	}

	/**
	 * 检查书本是否被关注过
	 * @param id     用户id
	 * @param commonId 关注id
	 * @return 
	 */
	private boolean checkBookOrTopicIsAllreadyFocus(Integer id, Integer commonId ,String type) {
		ModelEnum model;
		if("1".equals(type)){
			model = ModelEnum.book;
		}else{
			model = ModelEnum.topic;
		}
		String hql = " from "+ FoucusTopicorBook.class.getSimpleName() +
				" focus where focus.user.id = :id and focus.commonId = :commonId and focus.model = :model";
		Map<String,Object> params = new HashMap<>();
		params.put("id", id);
		params.put("commonId", commonId);
		params.put("model", model);
		FoucusTopicorBook data = hibernateDAO.findByColumn(hql,params);
		return data != null;
	}

	@Override
	public Message<List<FoucusTopicorBook>> queryMyFocusList(Integer start,
			Integer count, User user, ModelEnum modelEnum) {
		Message<List<FoucusTopicorBook>> result = new Message<>();
		
		String hql = "from FoucusTopicorBook where user = :user and"
				+ " model = :model order by createDate desc";
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("user", user);
		params.put("model", modelEnum);
		
//		int start = pageNum == 1 ? 0 :(pageNum-1)*(count-1);
		List<FoucusTopicorBook> ftbList = hibernateDAO.getListByPageByObject(hql, params, start, count);
		result.setData(ftbList);
		result.setStatus(true);
		return result;
	}

}
