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
		//生成搜索条件 自动搜索代码
		AutoParmAndHQL auto = super.createAuto(request);
		//分页
		map.put("auto", auto);
		System.out.println(JSON.toJSONString(auto));
		
		//如果开始页面是空情况下
		if (start == null)
		start = 1;
		Long recordCount = super.count("select count(*) from Label   " + auto.getHql(), auto.getMap());
		
		//分页算法
		autoPage(map,recordCount,start,"/manager/label/list");
		
		//加载数据
		List list = super.getAutoListByPage("from Label   " + auto.getHql(), auto.getMap(), (start - 1)*10, 10);
		map.put("entity", list);
		System.out.println("select count(*) from Label   " + auto.getHql());

		//找自动化页面
		return "manager/" + super.getAllName(Label.class) + "/list";
	}
	


	@ResponseBody
	@RequestMapping("/addNewLabel")
	public Message<Label> addNewLabel(Label label) {
		Message<Label> labelMsg = new Message<>();
		if (label.getName() == null) {
			labelMsg.setMsg("标签名为空");
			labelMsg.setStatus(false);
			return labelMsg;
		}

		if (label.getDescription() == null) {
			labelMsg.setMsg("标签详情为空");
			labelMsg.setStatus(false);
			return labelMsg;
		}
		if (label.getUser()==null||label.getUser().getId() ==null) {
			labelMsg.setMsg("用户id为空");
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
			labelMsg.setMsg("新名字不能为空");
			labelMsg.setStatus(false);
			return labelMsg;
		}
		if (label.getName() == null) {
			labelMsg.setMsg("要修改的标签名为空");
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
