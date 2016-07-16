package www.core.com.service;

import www.core.com.pojo.Message;
import www.core.com.pojo.PublishSign;

public interface IPublicSignService {

	/**
	 * ����PublishSign �ɹ� message.status = true; ʧ�� message.status = false;
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
