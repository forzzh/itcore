package www.core.com.controller.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.ITopicService;
import www.core.com.service.IUserPassUUIDService;
import www.core.com.service.IUserService;
import www.core.com.utils.CORER;
import www.core.com.utils.VerifyCodeUtils;

import com.mysema.commons.lang.URLEncoder;

@Controller
@RequestMapping("user/")
// 加载资源文件
public class UserController extends BaseController {

	@Autowired
	IUserService userService;
	@Autowired
	IUserPassUUIDService userPassUUIDService;
	@Autowired
	ITopicService topicService;
	
	/**
	 * 忘记密码页面
	 * @return
	 */
	@RequestMapping("/password/forget")
	public String forgetInit() {

		return "/web/user/forget/password";
	}
	/**
	 * 注册页面
	 * @return
	 */
	@RequestMapping("/reg")
	public String reg() {

		return "/web/user/reg/regUser";
	}
	@ResponseBody
	@RequestMapping("isReg")
	public Message<User> isReg(User user) {

		Message<User> message = new Message<>();
		message.setMsg("所查询的用户未注册");
		message.setStatus(false);

		if (user.getId() != null) {
			if (userService.userQueryById(user.getId()) != null) {
				message.setMsg("存在拥有该ID的用户");
				message.setStatus(true);
				return message;
			}
		}
		if (user.getEmail() != null) {
			if (userService.userQueryByEmail(user) != null) {
				message.setMsg("存在拥有该email的用户");
				message.setStatus(true);
				return message;
			}
		}
		if (user.getUser() != null) {
			if (userService.userQueryByName(user) != null) {
				message.setMsg("存在拥有该昵称的用户");
				message.setStatus(true);
				return message;
			}
		}
		return message;
	}
	
	public void doActiveUser(HttpServletRequest request,User user){
		Message<IUserPassUUIDService> UUIDMsg;
		UUIDMsg = userPassUUIDService.getActiveUserEmail(user);
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ "/";

		String activeLink = basePath + "/user/activeUser?UUID=" + UUIDMsg.getMsg();

		String emailHtmlContent = super.readActiveUser();

		emailHtmlContent = StringUtils.replace(emailHtmlContent, "{name}", user.getUser());
		emailHtmlContent = StringUtils.replace(emailHtmlContent, "{ACTUUID}", activeLink);

		super.getMail().sendTo(user.getEmail()).subject("用户激活").setHtmlContent(emailHtmlContent).send();
	}
	@ResponseBody
	@RequestMapping("loginAction")
	public Message loginAction(User user, String code, HttpServletRequest request,HttpSession session, HttpServletResponse response) {
		Message message = new Message<User>();
		if ("true".equals(super.getIsCode())) {
			if (StringUtils.isBlank(code)) {
				message.setMsg("验证码不能为空");
				return message;
			}
			Object userCode = request.getSession().getAttribute("userCode");
			if (!code.toLowerCase().equals(userCode)) {
				message.setMsg("验证码不正确");
				return message;
			}
			request.getSession().setAttribute("userCode", null);
		}
		if (!this.checkEmail(user.getEmail())) {
			message.setMsg("邮箱格式不正确");
			message.setStatus(false);
			return message;
		}
		if (StringUtils.isBlank(user.getPassword())) {
			message.setMsg("密码不能空");
			message.setStatus(false);
			return message;
		}
		// 执行查询，得到查询结果
		message = userService.login(user);
		if (message.isStatus()) {
			session.setAttribute(CORER.SESSION_USER, message.getData());
			Cookie cookie = new Cookie("email", user.getEmail());
			cookie.setPath("/");
			cookie.setMaxAge(1000*60*60*24*7);
			response.addCookie(cookie);
		}else if(message.getMsg().equals("用户未激活")){
			user.setUser(message.getBasic().toString());
			this.doActiveUser(request,user);
		}

		message.setUser(null);
		// 查询结果返回前台
		return message;
	}

	@ResponseBody
	@RequestMapping("verifycode")
	public void code(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// 生成随机字串
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// 存入会话session
		HttpSession session = request.getSession(true);
		session.setAttribute("userCode", verifyCode.toLowerCase());
		// 生成图片
		int w = 200, h = 80;
		VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);

	}

	@ResponseBody
	@RequestMapping("regUser")
	public Message reg(User user, HttpServletRequest request, String code, HttpServletResponse response)
			throws Exception {
		Message<User> message = new Message<User>();
		if ("true".equals(super.getIsRegCode())) {

			Object userCode = request.getSession().getAttribute("userCode");
			if (!code.toLowerCase().equals(userCode)) {
				message.setMsg("验证码不正确");
				return message;
			}
			request.getSession().setAttribute("userCode", null);
		}
		message = super.baseCheckUser(user, message);
		if (!message.isStatus()) {
			return message;
		}
		message = userService.regUser(user);
		// 如果注册成功，则发送激活邮件
		if (message.isStatus()) {
			// 得到要发送的邮件内容
			this.doActiveUser(request,user);
			message.setStatus(true);
			message.setMsg("注册成功");
		}
		return message;
	}
	@ResponseBody
	@RequestMapping("activeUser")
	public String activeUser(String UUID,ModelMap map) {
		Message<IUserPassUUIDService> userPassUUIDMessage;

		userPassUUIDMessage = userPassUUIDService.hadleActiveUser(UUID);
		map.put("msg", userPassUUIDMessage.getMsg());
		map.put("e", userPassUUIDMessage.getMsg());
		String redirect = URLEncoder.encodeURL("/user/login?msg="+userPassUUIDMessage.getMsg()+"&email="+userPassUUIDMessage.getBasic());
		return "<script>window.location.href='"+redirect+"'</script>";
				
	}

	/**
	 * 此方法接收一个email地址， 当email地址是某用户的email，则返回一个UUID用于重置密码。
	 * 把得到的UUID，和新的密码，发往hadleForget，即可重置密码
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/password/forgetAction", method = RequestMethod.POST)
	public String forgetPassword(User user, HttpServletRequest request, ModelMap map) {
		// TODO 得到user 信息，检测，
		Message resetPasswordMsg = null;
		try {
			if (null == user) {
				resetPasswordMsg = new Message();
				resetPasswordMsg.setMsg("null user");
				resetPasswordMsg.setStatus(false);
				return "forward:/password/forget";
			}
			if (user.getEmail() == null) {
				resetPasswordMsg = new Message();
				resetPasswordMsg.setMsg("null email adress");
				resetPasswordMsg.setStatus(false);
				return "forward:/password/forget";
			}
			resetPasswordMsg = userPassUUIDService.getResetPasswordUUID(user);
			if (resetPasswordMsg.isStatus()) {
				String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
						+ "/";
				String activeLink = basePath + "/user/forget/init?UUID=" + resetPasswordMsg.getMsg();
				String emailHtmlContent = super.readForgetPassword();

				emailHtmlContent = StringUtils.replace(emailHtmlContent, "{name}",
						resetPasswordMsg.getUser().getUser());
				emailHtmlContent = StringUtils.replace(emailHtmlContent, "{ACTUUID}", activeLink);
				super.getMail().sendTo(user.getEmail()).subject("用户找回密码").setHtmlContent(emailHtmlContent).send();
				resetPasswordMsg.setMsg("验证码已经发送邮箱，<a href='http://mail."
						+ user.getEmail().substring(user.getEmail().indexOf("@") + 1) + "'>登陆邮箱</a>。");
				resetPasswordMsg.setStatus(true);

			}
		} catch (Exception e) {

			throw e;
		} finally {
			map.put("msg", resetPasswordMsg);

		}
		return "forward:/user/password/forget";

	}

	@RequestMapping("/forget/init")
	public String forgetpasswordInit(String UUID, ModelMap map) {
		if (null == UUID) {
			return "forward:/user/password/forget";
		}

		// 检查这个验证码是否存在数据库 如果使用状态或者不存在 直接return "redirect:/user/password/forget";
		Message<IUserPassUUIDService> isEnableMsg = userPassUUIDService.isEnable(UUID);
		if (!isEnableMsg.isStatus()) {
			return "forward:/user/password/forget";
		}

		map.put("UUID", UUID);
		Message message = new Message<>();

		return "web/user/forget/forget";
	}

	// 接受一个用来重置密码的UUID和新密码
	@ResponseBody
	@RequestMapping("hadleForget")
	public Message hadleForgetPassword(String UUID, String newPassword) {
		Message message = null;
		if (null == UUID) {
			message = new Message<>();
			message.setMsg("修改密码失败");
			message.setStatus(false);
			return message;
		}
		try {

			if (StringUtils.isBlank(newPassword)) {
				message = new Message<>();
				message.setMsg("密码不能为空!");
				message.setStatus(false);
				return message;
			}
			message = new Message<>();
			// 检查UUID，查数据库
			if (userPassUUIDService.hadleResetPassword(UUID, newPassword)) {

				message.setMsg("修改密码成功");
				message.setStatus(true);

			} else {
				message.setMsg("修改密码失败");
				message.setStatus(false);
			}

			// 如果正确则更改密码
		} catch (Exception ex) {
			throw ex;
		}
		return message;
	}

	@RequestMapping("/login")
	public String login(String msg ,String email,ModelMap map,String redirect,HttpSession session,HttpServletRequest request) {
		if(session.getAttribute(SESSION_USER)!=null){
			return "redirect:http://"+request.getServerName()+"/index.html"; 
		}
		map.put("msg", msg);
		map.put("email", email);
		map.put("redirect", redirect);
		return "/web/user/login/Login";
	}
	
	/**
	 * 查询用户帖子
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("my/post")
	public String myPost(HttpSession session,Model model,Integer userid,HttpServletRequest request){
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		model.addAttribute("userdata",	(userService.userQuery(user.getId())));
		List topics = topicService.getUserTopics(user.getId(), 0, 11);
		model.addAttribute("userId", user.getId());
		model.addAttribute("show", true);
		model.addAttribute("topics", topics);
		model.addAttribute("static",super.getQiqiuUrl());
		return  "/web/user/my/topic";
	}
	
	/**
	 * 查询用户帖子(分页)
	 * @param session
	 * @param model
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping("my/post/page")
	public List getTopicByPage(HttpSession session,Integer page,Integer userid){
		
		
		return topicService.getUserTopics(userid, (page-1)*10, 11);
	}

	/**
	 * 查看其他人用户的所有帖子
	 * @param session
	 * @param model
	 * @param userid
	 * @param request
	 * @return
	 */
	@RequestMapping("/other/post/{userid}")
	public String otherPost(HttpSession session,Model model,@PathVariable Integer userid,HttpServletRequest request){
		if(userid==null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		
		model.addAttribute("userdata",	(userService.userQuery(userid)));
		
		List topics = topicService.getUserTopics(userid, 0, 11);
		model.addAttribute("topics", topics);
		model.addAttribute("userId",userid);
		model.addAttribute("show", false);

		model.addAttribute("static",super.getQiqiuUrl());
		return  "/web/user/my/topic";
	}
	@ResponseBody
	@RequestMapping("modifyImage")
	public Message modifyImage(String image,Integer x,Integer y ,Integer width ,Integer heigth,HttpServletRequest request ) throws IOException{
		Message message = new Message<>();
		User user = (User) request.getSession().getAttribute(CORER.SESSION_USER);//登陆用户
		if(user ==null){
			message.setStatus(false);
			message.setMsg("未登录");
			return  message;
		}

		return userService.modifyImage(image, x, y, width, heigth, user.getId(), super.getStaticUrl());
	}
	
	@ResponseBody
	@RequestMapping("/detail")
	public Message getUserById(HttpSession session,Integer userid){
		
		Message message = new Message<>();
		User user = (User)session.getAttribute(CORER.SESSION_USER);//登陆用户
		if(user ==null){
			message.setStatus(false);
			message.setMsg("未登录");
			message.setData(userService.userQuery(userid));
			return  message;
		}
		
		message.setStatus(true);
		message.setData(userService.userQuery(user.getId()));;
		return message;
		
	}

	@RequestMapping("/friend/")
	public String friend(HttpServletRequest request,ModelMap map,HttpSession session,Model model){
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		model.addAttribute("userdata",	(userService.userQuery(user.getId())));
		map.put("userid", user.getId());
		model.addAttribute("show", true);
		model.addAttribute("static",super.getQiqiuUrl());
		
		return "/web/user/my/friend";
	}
	@RequestMapping("/other/friend/{userid}")
	public String otherfriend(@PathVariable Integer userid,ModelMap map,HttpSession session,Model model){
		if(userid == null){
			throw new RuntimeException("用户未登陆");
		}
		map.put("userid", userid);
		model.addAttribute("show", false);
		model.addAttribute("userdata",	(userService.userQuery(userid)));
		model.addAttribute("static",super.getQiqiuUrl());
		return "/web/user/my/friend";
	}
	
	@RequestMapping("/my/MesssageList")
	public String messageList(Integer userid,ModelMap map,HttpServletRequest request,HttpSession session){
		
		/*User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}*/
		map.addAttribute("userdata",	(userService.userQuery(userid)));
		map.addAttribute("userId", userid);
		map.addAttribute("show", true);
		return "/web/user/my/myMessage";		
	}
	
	
	@RequestMapping("/outLogin")
	public String outLogin(HttpSession session,HttpServletRequest request,HttpServletResponse response)
	{
		Cookie cookie = new Cookie("email", null);
		Cookie password = new Cookie("password", null);
		response.addCookie(cookie);
		response.addCookie(password);
		session.setAttribute(SESSION_USER, null);
		return "redirect:http://"+request.getServerName()+"/";
	}
	@ResponseBody
	@RequestMapping("/statusUser")
	public String userStatus(HttpSession session,HttpServletRequest request){
		if(session.getAttribute(SESSION_USER)!=null){
			return "true";
		}else{
			return "false";
		}
		
	}
	/**
	 * 查询用户信息
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/message")
	public String myMessge(HttpSession session,Model model,Integer userid,HttpServletRequest request){
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		model.addAttribute("userdata",	(userService.userQuery(user.getId())));
		model.addAttribute("userId", user.getId());
		model.addAttribute("show", true);	
		return  "/web/user/my/myMessage";
	}
	
}
