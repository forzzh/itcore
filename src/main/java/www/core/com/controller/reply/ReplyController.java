package www.core.com.controller.reply;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.Enum.ReplyEnum;
import www.core.com.pojo.Message;
import www.core.com.pojo.Reply;
import www.core.com.service.IReplyService;

@Controller
@RequestMapping("reply")
public class ReplyController {

	@Autowired
	IReplyService replyService;
	
	/***
	 * ajax获得回复列表
	 * @param replyID 回复ID
	 * @param pageNum 页码数
	 * @param count   查询个数 默认11个
	 * @return
	 */
	@RequestMapping("/queryReplyByReplyID")
	@ResponseBody
	public Message<Reply> queryReplyByReplyID(Integer replyID,Integer pageNum,Integer count,ReplyEnum replyEnum,Integer answerid){
		Message result = new Message<>();
		if(replyID == null || pageNum <1 || replyEnum == null){
			result.setMsg("参数错误：replyID:" + replyID +" pageNum ：" +pageNum + " replayEnum:" +replyEnum);
			result.setStatus(false);
			return result;
		}
		if(count == null || count <0){
			count = 11;
		}
		return replyService.queryReplyByReplyID(replyID, pageNum, count,replyEnum,answerid);
	}
}
