package www.core.com.controller.manager.indextopic;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import www.core.com.controller.base.BaseController;
import www.core.com.pojo.AutoParmAndHQL;
import www.core.com.pojo.IndexTopicConfig;
import www.core.com.pojo.Message;
import www.core.com.service.IIndexTopicConfigService;

@Controller
@RequestMapping("/manager/indexTopic")
public class IndexTopicConfigController extends BaseController<IndexTopicConfig> {
	@Autowired
	IIndexTopicConfigService indexTopicConfigService;

	@ResponseBody
	@RequestMapping("/setTopicRange")
	public Message<IndexTopicConfig> setTopicRange(Integer topicId, Integer range, String description) {
		Message<IndexTopicConfig> indexTopicMsg = new Message<IndexTopicConfig>();
		if (topicId == null) {
			indexTopicMsg.setMsg("TopicId����Ϊ��");
			indexTopicMsg.setStatus(false);
			return indexTopicMsg;
		}
		if (range == null) {
			indexTopicMsg.setMsg("���ô�����Ϊ��");
			indexTopicMsg.setStatus(false);
			return indexTopicMsg;
		}
		if (description == null) {
			indexTopicMsg.setMsg("˵������Ϊ��");
			indexTopicMsg.setStatus(false);
			return indexTopicMsg;
		}
		indexTopicMsg = indexTopicConfigService.setTopicRange(topicId, range, description);
		return indexTopicMsg;
	}

	@RequestMapping("/list")
	public String list(ModelMap map, HttpServletRequest request, Integer start) {

		// TODO Auto-generated method stub
		// ������������ �Զ���������
		AutoParmAndHQL auto = super.createAuto(request);
		// ��ҳ
		map.put("auto", auto);
		System.out.println(JSON.toJSONString(auto));

		// �����ʼҳ���ǿ������
		if (start == null)
			start = 1;
		Long recordCount = super.count("select count(*) from IndexTopicConfig   " + auto.getHql(), auto.getMap());

		// ��ҳ�㷨
		autoPage(map, recordCount, start, "/manager/indexTopicConfig/list");

		// ��������
		List list = super.getAutoListByPage("from IndexTopicConfig   " + auto.getHql(), auto.getMap(), (start - 1) * 10,
				10);
		map.put("entity", list);
		System.out.println("select count(*) from IndexTopicConfig   " + auto.getHql());

		// ���Զ���ҳ��
		return "manager/indextopic/list";
	}
	
	@RequestMapping("add")
	public String add() {
		return "/manager/indextopic/add";
	}
}
