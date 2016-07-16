package www.core.com.controller.manager.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manager/login/")
@Controller
public class LoginManagerController {

	@RequestMapping("/")
	public String login(){
		return "/manage/login";
	}
	

	@RequestMapping("/test")
	public String test(){
		return "/manage/main";
	}
}
