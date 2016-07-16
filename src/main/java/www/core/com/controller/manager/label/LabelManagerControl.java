package www.core.com.controller.manager.label;

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
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.service.ILabelService;

@Controller
@RequestMapping("/manager/label")
public class LabelManagerControl extends BaseController<Label>{
	
	@Autowired
	ILabelService labelService;
	
	@RequestMapping("list")
	public String list(ModelMap map, HttpServletRequest request, Label label, Integer start) {

		// TODO Auto-generated method stub
		//������������ �Զ���������
		AutoParmAndHQL auto = super.createAuto(request);
		//��ҳ
		map.put("auto", auto);
		System.out.println(JSON.toJSONString(auto));
		
		//�����ʼҳ���ǿ������
		if (start == null)
		start = 1;
		Long recordCount = super.count("select count(*) from Label   " + auto.getHql(), auto.getMap());
		
		//��ҳ�㷨
		autoPage(map,recordCount,start,"/manager/label/list");
		
		//��������
		List list = super.getAutoListByPage("from Label   " + auto.getHql(), auto.getMap(), (start - 1)*10, 10);
		map.put("entity", list);
		System.out.println("select count(*) from Label   " + auto.getHql());

		//���Զ���ҳ��
		return "manager/" + super.getAllName(Label.class) + "/list";
	}
	


	@ResponseBody
	@RequestMapping("/addNewLabel")
	public Message<Label> addNewLabel(Label label) {
		Message<Label> labelMsg = new Message<>();
		if (label.getName() == null) {
			labelMsg.setMsg("��ǩ��Ϊ��");
			labelMsg.setStatus(false);
			return labelMsg;
		}

		if (label.getDescription() == null) {
			labelMsg.setMsg("��ǩ����Ϊ��");
			labelMsg.setStatus(false);
			return labelMsg;
		}
		if (label.getUser()==null||label.getUser().getId() ==null) {
			labelMsg.setMsg("�û�idΪ��");
			labelMsg.setStatus(false);
			return labelMsg;
		}
		return labelService.addNewLableManager(label,label.getUser().getId());

	}

	@ResponseBody
	@RequestMapping("/setLabelName")
	public Message<Label> setLabelName(String newName,Label label) {
		Message<Label> labelMsg = new Message<>();
		
		if (newName == null) {
			labelMsg.setMsg("�����ֲ���Ϊ��");
			labelMsg.setStatus(false);
			return labelMsg;
		}
		if (label.getName() == null) {
			labelMsg.setMsg("Ҫ�޸ĵı�ǩ��Ϊ��");
			labelMsg.setStatus(false);
			return labelMsg;
		}

		return labelService.setLabelName(newName,label);

	}
	
	
	@RequestMapping("add")
	public String add(Label label) {
		return "/manager/label/add";
	}
	@RequestMapping("modify")
	public String modify(Label label) {
		return "/manager/label/modify";
	}
	
	
}
