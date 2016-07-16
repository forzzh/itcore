package www.core.com.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import www.core.com.dao.BaseDAO;
import www.core.com.pojo.FocusTag;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IFocusTagService;

@Service("focusTagService")
public class FocusTagServiceImpl  implements IFocusTagService{
	@Resource(name = "hibernateDAO")
	BaseDAO hibernateDAO;

	@Override
	public FocusTag foucusLabel(Integer user, Label  label) {
		String hql = "from "+ FocusTag.class.getSimpleName() 
				+" where fousUserId=:fousUserId and labelId=:labelId";
		Map<String , Object> parm = new HashMap();
		parm.put("fousUserId", user);
		parm.put("labelId", label.getId());
		return (FocusTag) hibernateDAO.findByColumn(hql, parm);
		
	}
	@Override
	public void saveFoucusLabel(Integer userid, Label label) {
		User user = new User();
		user.setId(userid);;
		FocusTag entity = new FocusTag(label,user);
		hibernateDAO.save(entity);;
	}
	@Override
	public Message dofocus(Integer userid, Integer labelid) {
		Message<FocusTag> focus = new Message<>();
		String hql = "from "+ FocusTag.class.getSimpleName() 
				+" where fousUserId=:fousUserId and labelId=:labelId";
		Map<String , Object> parm = new HashMap();
		parm.put("fousUserId", userid);
		parm.put("labelId",labelid);
		FocusTag result = (FocusTag) hibernateDAO.findByColumn(hql, parm);
		
		Map<String , Object> parm1 = new HashMap();
		String sql="from "+Label.class.getSimpleName()+
				"  where id=:labelId";
		parm1.put("labelId", labelid);
		Label label = (Label) hibernateDAO.findByColumn(sql, parm1);
		if(result == null){
			result = new FocusTag();
			result.setUser(new User(userid));
			result.setLabel(label);
			hibernateDAO.save(result);
			label.setFoucsPerson(label.getFoucsPerson()+1);
			hibernateDAO.saveOrUpdate(label);
			focus.setMsg("标签关注成功");
		}else{
			hibernateDAO.delete(result);
			label.setFoucsPerson(label.getFoucsPerson()-1);
			hibernateDAO.saveOrUpdate(label);
			focus.setMsg("标签取消关注");
		}
		focus.setStatus(true);
		return focus;
	}
}