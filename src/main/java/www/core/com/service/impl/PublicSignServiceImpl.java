package www.core.com.service.impl;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.dao.BaseDAO;
import www.core.com.pojo.Message;
import www.core.com.pojo.PublishSign;
import www.core.com.pojo.User;
import www.core.com.service.IPublicSignService;
import www.core.com.service.IUserService;

@Transactional
@Service("publicSignService")
public class PublicSignServiceImpl implements IPublicSignService {
	@Resource(name = "hibernateDAO")
	BaseDAO<PublishSign> hibernateDAO;

	@Autowired
	IUserService userService;

	@Override
	public Message<PublishSign> publishNewSign(PublishSign publishSign) {
		Message<PublishSign> publishSignMsg;
		publishSign.setLike(0);

		hibernateDAO.saveOrUpdate(publishSign);

		publishSignMsg = new Message<>();
		publishSignMsg.setMsg("发布成功");
		publishSignMsg.setStatus(true);
		return publishSignMsg;
	}

	@Override
	public PublishSign pubSignExistenceById(Integer publishSignID) {
		PublishSign publishSign = hibernateDAO.findByColumn(PublishSign.class, null, publishSignID);
		return publishSign;
	}

	@Override
	public Message<PublishSign> getNewPublicSign(Integer start, Integer count) {
		Message<PublishSign> psMsg = new Message<PublishSign>();
		String hql = "from PublishSign order by createDate desc";
		List<PublishSign> psList = hibernateDAO.getListByPage(hql, null, start, count);
		for (PublishSign publishSign : psList) {
			publishSign.setShowData(publishSign.getShowData());
		}
		psMsg.setList(psList);
		psMsg.setStatus(true);
		return psMsg;
	}

}
