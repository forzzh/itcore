package www.core.com.controller.publishSign;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.pojo.Message;
import www.core.com.pojo.PubSignLikeList;
import www.core.com.pojo.PublishSign;
import www.core.com.service.IPubSignLikeListService;
import www.core.com.service.IPublicSignService;

@Controller
@RequestMapping("publicSign")
public class PublishSignController {

	@Autowired
	IPublicSignService publicSignService;
	
	@Autowired
	IPubSignLikeListService pubSignLikeListService;
	
	
	
	@RequestMapping("/getHotPublicSign")
	public Message<PublishSign> getHotPublicSign() {
		return null;
	}

	@RequestMapping("/getCreateDatePublicSign")
	public Message<PublishSign> getCreateDatePublicSign() {
		return null;
	}
	
	@ResponseBody
	@RequestMapping("/getNewPublicSign")
	public Message<PublishSign> getNewPublicSign() {
		Message<PublishSign> message =  publicSignService.getNewPublicSign(0, 6);
		
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/likeIt")
	public Message<PubSignLikeList> likeIt(Integer userID, Integer publishSignID) {
		
		Message<PubSignLikeList> likeItPublishSignMessage;
		if (userID == null || publishSignID == null) {
			likeItPublishSignMessage = new Message<>();
			likeItPublishSignMessage.setMsg("idΪ��");
			return likeItPublishSignMessage;
		}
		
		likeItPublishSignMessage = pubSignLikeListService.likeIt(userID, publishSignID);
		
		return likeItPublishSignMessage;
	}
	
	@ResponseBody
	@RequestMapping("/add")
	public Message<PublishSign> add(PublishSign publishSign) {

		Message<PublishSign> addPublishSignMessage;

		if (null == publishSign) {
			addPublishSignMessage = new Message<>();
			addPublishSignMessage.setMsg("����Ϊ��");
			return addPublishSignMessage;
		}

		if (publishSign.getCreateUser() == null) {
			addPublishSignMessage = new Message<>();
			addPublishSignMessage.setMsg("������Ϊ��");
			return addPublishSignMessage;
		}

		if (publishSign.getContent() == null || StringUtils.isBlank(publishSign.getContent())) {
			addPublishSignMessage = new Message<>();
			addPublishSignMessage.setMsg("����Ϊ��");
			return addPublishSignMessage;
		}
		
		if (publishSign.getContent().length() > 200) {
			addPublishSignMessage = new Message<>();
			addPublishSignMessage.setMsg("������ҪС��200��");
			return addPublishSignMessage;
		}

		Message<PublishSign> publishSignMsg;
		publishSignMsg = publicSignService.publishNewSign(publishSign);
		if (!publishSignMsg.isStatus()) {
			addPublishSignMessage = new Message<>();
			addPublishSignMessage.setMsg("�����쳣");
			return addPublishSignMessage;
		}
		
		return publishSignMsg;
	}
}
