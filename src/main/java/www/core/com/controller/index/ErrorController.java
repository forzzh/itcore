package www.core.com.controller.index;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping
@Controller
public class ErrorController {
//	 @RequestMapping("*") 
//	public String noFound(ModelMap map){
//		map.put("msg", "抱歉，找不到此页面~");
//		return "/web/error/404";
//	}
	@RequestMapping("500.html")
	public String Error(ModelMap map){
		map.put("msg", "抱歉，系统出错啦，请稍后再试~");
		return "/web/error/404";
	}
	 @RequestMapping("/404.html") 
	public String noFoundPage(ModelMap map){
		map.put("msg", "抱歉，找不到此页面~");
		return "/web/error/404";
	}
}
