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
// ������Դ�ļ�
public class UserController extends BaseController {

	@Autowired
	IUserService userService;
	@Autowired
	IUserPassUUIDService userPassUUIDService;
	@Autowired
	ITopicService topicService;
	
	/**
	 * ��������ҳ��
	 * @return
	 */
	@RequestMapping("/password/forget")
	public String forgetInit() {

		return "/web/user/forget/password";
	}
	/**
	 * ע��ҳ��
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
		message.setMsg("����ѯ���û�δע��");
		message.setStatus(false);

		if (user.getId() != null) {
			if (userService.userQueryById(user.getId()) != null) {
				message.setMsg("����ӵ�и�ID���û�");
				message.setStatus(true);
				return message;
			}
		}
		if (user.getEmail() != null) {
			if (userService.userQueryByEmail(user) != null) {
				message.setMsg("����ӵ�и�email���û�");
				message.setStatus(true);
				return message;
			}
		}
		if (user.getUser() != null) {
			if (userService.userQueryByName(user) != null) {
				message.setMsg("����ӵ�и��ǳƵ��û�");
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

		super.getMail().sendTo(user.getEmail()).subject("�û�����").setHtmlContent(emailHtmlContent).send();
	}
	@ResponseBody
	@RequestMapping("loginAction")
	public Message loginAction(User user, String code, HttpServletRequest request,HttpSession session, HttpServletResponse response) {
		Message message = new Message<User>();
		if ("true".equals(super.getIsCode())) {
			if (StringUtils.isBlank(code)) {
				message.setMsg("��֤�벻��Ϊ��");
				return message;
			}
			Object userCode = request.getSession().getAttribute("userCode");
			if (!code.toLowerCase().equals(userCode)) {
				message.setMsg("��֤�벻��ȷ");
				return message;
			}
			request.getSession().setAttribute("userCode", null);
		}
		if (!this.checkEmail(user.getEmail())) {
			message.setMsg("�����ʽ����ȷ");
			message.setStatus(false);
			return message;
		}
		if (StringUtils.isBlank(user.getPassword())) {
			message.setMsg("���벻�ܿ�");
			message.setStatus(false);
			return message;
		}
		// ִ�в�ѯ���õ���ѯ���
		message = userService.login(user);
		if (message.isStatus()) {
			session.setAttribute(CORER.SESSION_USER, message.getData());
			Cookie cookie = new Cookie("email", user.getEmail());
			cookie.setPath("/");
			cookie.setMaxAge(1000*60*60*24*7);
			response.addCookie(cookie);
		}else if(message.getMsg().equals("�û�δ����")){
			user.setUser(message.getBasic().toString());
			this.doActiveUser(request,user);
		}

		message.setUser(null);
		// ��ѯ�������ǰ̨
		return message;
	}

	@ResponseBody
	@RequestMapping("verifycode")
	public void code(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		// ��������ִ�
		String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
		// ����Ựsession
		HttpSession session = request.getSession(true);
		session.setAttribute("userCode", verifyCode.toLowerCase());
		// ����ͼƬ
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
				message.setMsg("��֤�벻��ȷ");
				return message;
			}
			request.getSession().setAttribute("userCode", null);
		}
		message = super.baseCheckUser(user, message);
		if (!message.isStatus()) {
			return message;
		}
		message = userService.regUser(user);
		// ���ע��ɹ������ͼ����ʼ�
		if (message.isStatus()) {
			// �õ�Ҫ���͵��ʼ�����
			this.doActiveUser(request,user);
			message.setStatus(true);
			message.setMsg("ע��ɹ�");
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
	 * �˷�������һ��email��ַ�� ��email��ַ��ĳ�û���email���򷵻�һ��UUID�����������롣
	 * �ѵõ���UUID�����µ����룬����hadleForget��������������
	 * 
	 * @param user
	 * @return
	 */
	@RequestMapping(value = "/password/forgetAction", method = RequestMethod.POST)
	public String forgetPassword(User user, HttpServletRequest request, ModelMap map) {
		// TODO �õ�user ��Ϣ����⣬
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
				super.getMail().sendTo(user.getEmail()).subject("�û��һ�����").setHtmlContent(emailHtmlContent).send();
				resetPasswordMsg.setMsg("��֤���Ѿ��������䣬<a href='http://mail."
						+ user.getEmail().substring(user.getEmail().indexOf("@") + 1) + "'>��½����</a>��");
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

		// ��������֤���Ƿ�������ݿ� ���ʹ��״̬���߲����� ֱ��return "redirect:/user/password/forget";
		Message<IUserPassUUIDService> isEnableMsg = userPassUUIDService.isEnable(UUID);
		if (!isEnableMsg.isStatus()) {
			return "forward:/user/password/forget";
		}

		map.put("UUID", UUID);
		Message message = new Message<>();

		return "web/user/forget/forget";
	}

	// ����һ���������������UUID��������
	@ResponseBody
	@RequestMapping("hadleForget")
	public Message hadleForgetPassword(String UUID, String newPassword) {
		Message message = null;
		if (null == UUID) {
			message = new Message<>();
			message.setMsg("�޸�����ʧ��");
			message.setStatus(false);
			return message;
		}
		try {

			if (StringUtils.isBlank(newPassword)) {
				message = new Message<>();
				message.setMsg("���벻��Ϊ��!");
				message.setStatus(false);
				return message;
			}
			message = new Message<>();
			// ���UUID�������ݿ�
			if (userPassUUIDService.hadleResetPassword(UUID, newPassword)) {

				message.setMsg("�޸�����ɹ�");
				message.setStatus(true);

			} else {
				message.setMsg("�޸�����ʧ��");
				message.setStatus(false);
			}

			// �����ȷ���������
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
	 * ��ѯ�û�����
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
	 * ��ѯ�û�����(��ҳ)
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
	 * �鿴�������û�����������
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
		User user = (User) request.getSession().getAttribute(CORER.SESSION_USER);//��½�û�
		if(user ==null){
			message.setStatus(false);
			message.setMsg("δ��¼");
			return  message;
		}

		return userService.modifyImage(image, x, y, width, heigth, user.getId(), super.getStaticUrl());
	}
	
	@ResponseBody
	@RequestMapping("/detail")
	public Message getUserById(HttpSession session,Integer userid){
		
		Message message = new Message<>();
		User user = (User)session.getAttribute(CORER.SESSION_USER);//��½�û�
		if(user ==null){
			message.setStatus(false);
			message.setMsg("δ��¼");
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
			throw new RuntimeException("�û�δ��½");
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
	 * ��ѯ�û���Ϣ
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
