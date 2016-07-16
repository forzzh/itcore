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
			indexLabelMsg.setMsg("标签id不能为空");
			indexLabelMsg.setStatus(false);
			return indexLabelMsg;
		}

		if (range == null) {
			indexLabelMsg.setMsg("设置次序不能为空");
			indexLabelMsg.setStatus(false);
			return indexLabelMsg;
		}
		indexLabelMsg = indexLabelConfigService.setLabelRange(labelId, range);	
		
		return indexLabelMsg;
	}
	
	@RequestMapping("/list")
	public String list(ModelMap map, HttpServletRequest request, Integer start) {

		// TODO Auto-generated method stub
		//生成搜索条件 自动搜索代码
		AutoParmAndHQL auto = super.createAuto(request);
		//分页
		map.put("auto", auto);
		System.out.println(JSON.toJSONString(auto));
		
		//如果开始页面是空情况下
		if (start == null)
		start = 1;
		Long recordCount = super.count("select count(*) from IndexLabelConfig   " + auto.getHql(), auto.getMap());
		
		//分页算法
		autoPage(map,recordCount,start,"/manager/indexLabelConfig/list");
		
		//加载数据
		List list = super.getAutoListByPage("from IndexLabelConfig   " + auto.getHql(), auto.getMap(), (start - 1)*10, 10);
		map.put("entity", list);
		System.out.println("select count(*) from IndexLabelConfig   " + auto.getHql());

		//找自动化页面
		return "manager/indexlabel/list";
	}
	
	@RequestMapping("add")
	public String add() {
		return "/manager/indexlabel/add";
	}
}
