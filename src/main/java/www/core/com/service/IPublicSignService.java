package www.core.com.service;

import www.core.com.pojo.Message;
import www.core.com.pojo.PublishSign;

public interface IPublicSignService {

	/**
	 * 发表PublishSign 成功 message.status = true; 失败 message.status = false;
	 * 
	 * @param PublishSign
	 * @return
	 */
	public Message<PublishSign> publishNewSign(PublishSign publishSign);
	

	/**
	 * 
	 * @param publishSign
	 * @return
	 */
	public PublishSign pubSignExistenceById(Integer publishSignID);


	public Message<PublishSign> getNewPublicSign(Integer start, Integer count);
	
	
}
