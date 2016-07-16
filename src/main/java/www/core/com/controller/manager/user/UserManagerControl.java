package www.core.com.controller.manager.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.Enum.SexEnum;
import www.core.com.Enum.UserEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.AutoParmAndHQL;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IUserService;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("/manager/user")
//������Դ�ļ�
@PropertySource({ "classpath:/config/properties/sys.properties" })
public class UserManagerControl extends BaseController<User> {
	@Autowired
	IUserService userService;
	@Value("${minUserLength}")
	Integer minUserLength;
	@Value("${maxUserLength}")
	Integer maxUserLength;
	
	
	@RequestMapping("list")
	public String list(ModelMap map, HttpServletRequest request, User user, Integer start) {

		// TODO Auto-generated method stub
		//������������ �Զ���������
		AutoParmAndHQL auto = super.createAuto(request);
		//��ҳ
		map.put("auto", auto);
		System.out.println(JSON.toJSONString(auto));
		
		//�����ʼҳ���ǿ������
		if (start == null)
		start = 1;
		Long recordCount = super.count("select count(*) from User   " + auto.getHql(), auto.getMap());
		
		//��ҳ�㷨
		autoPage(map,recordCount,start,"/manager/user/list");
		
		//��������
		List list = super.getAutoListByPage("from User   " + auto.getHql(), auto.getMap(), (start - 1)*10, 10);
		map.put("entity", list);
		System.out.println("select count(*) from User   " + auto.getHql());

		//���Զ���ҳ��
		return "/manager/" + super.getAllName(User.class) + "/list";
	}
	
	@RequestMapping("/add")
	public String addInit(User user,ModelMap map) {
		map.put("userStatus", UserEnum.values());
		map.put("userSex", SexEnum.values());
		return "/manager/user/adduser";
	}
	
	
	@RequestMapping("/modify")
	public String updateInit(Integer id,ModelMap map,HttpServletRequest request) {
		Message<User> message = new Message<User>();
	    User user= userService.userQueryById(id);
	    map.put("user", user);
		return "/manager/user/modify";
	}
	
	
	
	@ResponseBody
	@RequestMapping(value="/updateuser",method=RequestMethod.POST)
	public Message updateUser(String isModify, User user,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Message<User> message = new Message<>();
		message = super.baseCheckUser(user, message);
		if(!message.isStatus() ){
			return message;
		}
		message = userService.updateUserInfos(user,message);
		return message;
	}
	
	@ResponseBody
	@RequestMapping("/adduser")
	public Message addUser(User user,HttpServletRequest request,HttpServletResponse response) throws Exception {
		Message<User> message = new Message<>();
		message = super.baseCheckUser(user, message);
		if(!message.isStatus() ){
			return message;
		} 
		if(user.getStatus() == null){
			message.setMsg("�û���״̬����Ϊ��");
		}
		message = userService.addUser(user);
		return message;
	}
	
	/**
	 * �����û���ѯ����
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updateindex")
	public Message updateUserIndex(){
		Message message = new Message();
		try{
			return userService.updateUserIndex();
		}catch(Exception e){
			e.printStackTrace();
			message.setStatus(false);
			message.setMsg(e.getMessage());
		}
		return message;
	}
}
