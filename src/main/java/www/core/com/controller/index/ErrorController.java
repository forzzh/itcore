package www.core.com.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class ErrorController {
//	 @RequestMapping("*") 
//	public String noFound(ModelMap map){
//		map.put("msg", "��Ǹ���Ҳ�����ҳ��~");
//		return "/web/error/404";
//	}
	@RequestMapping("500.html")
	public String Error(ModelMap map){
		map.put("msg", "��Ǹ��ϵͳ�����������Ժ�����~");
		return "/web/error/404";
	}
	 @RequestMapping("/404.html") 
	public String noFoundPage(ModelMap map){
		map.put("msg", "��Ǹ���Ҳ�����ҳ��~");
		return "/web/error/404";
	}
}
