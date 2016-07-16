package www.core.com.controller.manager.indexlabel;

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
import www.core.com.pojo.IndexLabelConfig;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.service.IIndexLabelConfigService;

@Controller
@RequestMapping("/manager/indexLabel")
public class IndexLabelConfigController extends BaseController<IndexLabelConfig> {

	@Autowired
	IIndexLabelConfigService indexLabelConfigService;

	@ResponseBody
	@RequestMapping("/setLabelRange")
	public Message<IndexLabelConfig> setLabelRange(Integer labelId, Integer range) {
		Message<IndexLabelConfig> indexLabelMsg = new Message<IndexLabelConfig>();
		if (labelId == null) {
			indexLabelMsg.setMsg("��ǩid����Ϊ��");
			indexLabelMsg.setStatus(false);
			return indexLabelMsg;
		}

		if (range == null) {
			indexLabelMsg.setMsg("���ô�����Ϊ��");
			indexLabelMsg.setStatus(false);
			return indexLabelMsg;
		}
		indexLabelMsg = indexLabelConfigService.setLabelRange(labelId, range);	
		
		return indexLabelMsg;
	}
	
	@RequestMapping("/list")
	public String list(ModelMap map, HttpServletRequest request, Integer start) {

		// TODO Auto-generated method stub
		//������������ �Զ���������
		AutoParmAndHQL auto = super.createAuto(request);
		//��ҳ
		map.put("auto", auto);
		System.out.println(JSON.toJSONString(auto));
		
		//�����ʼҳ���ǿ������
		if (start == null)
		start = 1;
		Long recordCount = super.count("select count(*) from IndexLabelConfig   " + auto.getHql(), auto.getMap());
		
		//��ҳ�㷨
		autoPage(map,recordCount,start,"/manager/indexLabelConfig/list");
		
		//��������
		List list = super.getAutoListByPage("from IndexLabelConfig   " + auto.getHql(), auto.getMap(), (start - 1)*10, 10);
		map.put("entity", list);
		System.out.println("select count(*) from IndexLabelConfig   " + auto.getHql());

		//���Զ���ҳ��
		return "manager/indexlabel/list";
	}
	
	@RequestMapping("add")
	public String add() {
		return "/manager/indexlabel/add";
	}
}
