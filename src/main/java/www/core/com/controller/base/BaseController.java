package www.core.com.controller.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.ModelMap;

import www.core.com.controller.manager.MangerInterface;
import www.core.com.pojo.AutoParmAndHQL;
import www.core.com.pojo.Book;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.TopicContent;
import www.core.com.pojo.User;
import www.core.com.service.impl.AbstractService;
import www.core.com.utils.CORER;
import www.core.com.utils.Email;

@Configuration
// 加载资源文件
@PropertySource({ "classpath:/config/properties/sys.properties" })
public class BaseController<T> extends AbstractService implements MangerInterface, CORER {
	@Value("${email.host}")
	private String host;
	@Value("${email.from}")
	private String from;
	@Value("${email.password}")
	private String password;
	@Value("${email.user}")
	private String user;
	@Value("${staticUrl}")
	private String staticUrl;
	@Value("${qiqiuUrl}")
	private String qiqiuUrl;
	@Value("${isCode}")
	String isCode;
	@Value("${isRegCode}")
	String isRegCode;
	@Value("${minUserLength}")
	Integer minUserLength;
	@Value("${maxUserLength}")
	Integer maxUserLength;
	@Value("${managerAdmin}")
	String managerAdmin;
	public void setManagerAdmin(String managerAdmin) {
		this.managerAdmin = managerAdmin;
	}
	public String getManagerAdmin() {
		return managerAdmin;
	}
	public Message baseCheckUser(User user,Message message){
		
		
			if (!checkUser(user.getUser(), minUserLength, maxUserLength)) {
				message.setMsg("昵称不能小于" + minUserLength + "大于" + maxUserLength + "字符");
				message.setStatus(false);
				return message;
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
		if (user.getSex() == null) {
			message.setMsg("你的性别不能为空");
			message.setStatus(false);
			return message;
		}
		message.setStatus(true);
		return message;
	}
	public Message baseTopicAdd(Topic topic, TopicContent detail, User user,String extLabel,Integer userId) throws InterruptedException, IOException{
		Message message = null;
		if (StringUtils.isBlank(topic.getImage())) {
			message = new Message<Topic>();
			message.setMsg("图片不能为空");
			return message;
		}
		if (StringUtils.isBlank(topic.getTitle())) {
			message = new Message<Topic>();
			message.setMsg("标题不能为空");
			return message;
		}
		if (StringUtils.isBlank(topic.getImage())) {
			message = new Message<Topic>();
			message.setMsg("图片不能为空");
			return message;
		}

		if (topic.getLabel() == null) {
			message = new Message<Topic>();
			message.setMsg("标签不能为空");
			return message;
		}
//		if (topic.getGroup() == null || topic.getGroup().getId() == null) {
//			message = new Message<Topic>();
//			message.setMsg("圈子id不能为空");
//			return message;
//		}
		if (StringUtils.isBlank(detail.getContent())) {
			message = new Message<Topic>();
			message.setMsg("文章内容不能为空");
			return message;
		}
		user.setId(userId);
		topic.setUser(user);
		message = new Message<Topic>();
		message.setStatus(true);
		return message;
	}
	
	public String getIsCode() {
		return isCode;
	}
	public void setIsCode(String isCode) {
		this.isCode = isCode;
	}
	public String getIsRegCode() {
		return isRegCode;
	}
	public void setIsRegCode(String isRegCode) {
		this.isRegCode = isRegCode;
	}
	public Integer getMinUserLength() {
		return minUserLength;
	}
	public void setMinUserLength(Integer minUserLength) {
		this.minUserLength = minUserLength;
	}
	public Integer getMaxUserLength() {
		return maxUserLength;
	}
	public void setMaxUserLength(Integer maxUserLength) {
		this.maxUserLength = maxUserLength;
	}
	public String getQiqiuUrl() {
		return qiqiuUrl;
	}

	public void setQiqiuUrl(String qiqiuUrl) {
		this.qiqiuUrl = qiqiuUrl;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getStaticUrl() {
		return staticUrl;
	}

	public void setStaticUrl(String staticUrl) {
		this.staticUrl = staticUrl;
	}

	
	public String[] extLabelUtils(String main, String ext) {
		String[] exts = null;
		if (StringUtils.isNotBlank(ext)) {
			// 吧前端传入的扩展标签字符串转换为数组
			exts = ext.split(",");
			// 如果分割后还是有内容那么进行转换链表形式数组
			List<String> list = this.convertArray(exts);
			if(list.size() == 1 ){
				return this.array_unique(list.toArray(new String[list.size()]));
			}
			// 如果主标签包含了
			for (String name : exts) {
				if (name.equals(main)) {
					list.remove(name);
				}
			}
				
			
			exts = this.array_unique(list.toArray(new String[list.size()]));
		}
		return exts;
	}

	public String[] array_unique(String[] a) {
		// array_unique
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < a.length; i++) {
			if (StringUtils.isBlank(a[i])) {
				continue;
			}
			if (!list.contains(a[i])) {
				list.add(a[i]);
			}
		}
		return list.toArray(new String[list.size()]);
	}

	public List<String> convertArray(String[] a) {
		// array_unique
		List<String> list = new LinkedList<String>();
		for (int i = 0; i < a.length; i++) {
			if (StringUtils.isBlank(a[i])) {
				continue;
			}
			list.add(a[i]);
		}
		return list;
	}

	public boolean checkEmail(String email) {
		if (email == null) {
			return false;
		}
		Pattern pattern = Pattern.compile(
				"^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	public Email getMail() {

		Email mail = new Email();
		mail.setHost(host); // 邮件服务器地址
		mail.setFrom(from); // 发件人邮箱
		mail.setUser(user); // 用户名
		mail.setPassword(password); // 密码

		return mail;
	}

	/**
	 * 检查昵称
	 * 
	 * @return
	 */
	public boolean checkUser(String user, int start, int maxlength) {

		if (user == null || user.length() < start || user.length() > maxlength) {
			return false;
		}
		return true;
	}

	public boolean checkPassword(String password) {
		if (password == null || password.length() < 6 || password.length() > 15) {
			return false;
		}

		Pattern pattern = Pattern.compile("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	// 生成随机码
	public String createCode() {
		return UUID.randomUUID().toString();
	}

	public String readToString(File file) {
        StringBuffer sbread = new StringBuffer();  
		try {
			 InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "gbk");  
     
             while (isr.ready()) {  
                 sbread.append((char) isr.read());  
             }  
             
             isr.close();  
              
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(sbread);// 返回文件内容,默认编码
	}

	public String readFile(String path) {
		try {
			path = BaseController.class.getClassLoader().getResource("").toURI().getPath() + path;
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File file = new File(path);
		return readToString(file);
	}

	public String readForgetPassword() {
		return readFile(forgetPassword);
	}

	public String readActiveUser() {
		return readFile(activeUser);
	}

	
	public void initList(ModelMap map) {
		// TODO Auto-generated method stub

	}

	public AutoParmAndHQL createAuto(HttpServletRequest request) {
		AutoParmAndHQL auto = new AutoParmAndHQL();
		String hql = "where 1=1";
		String query = "";
		Map<String, Object> parm = new HashMap<String, Object>();
		Map map = request.getParameterMap();
		Set keSet = map.entrySet();
		
		
		for (Iterator itr = keSet.iterator(); itr.hasNext();) {
			Map.Entry me = (Map.Entry) itr.next();
			Object ok = me.getKey(); // 获取参数名
			Object ov = me.getValue(); // 获取参数值
			System.out.println(ov);
			System.out.println("-------");
			if(ok.equals("start")){
				continue;
			}
			if(StringUtils.isBlank(((Object[]) ov)[0].toString()) ){
				continue;
			}
			try{
				Long.valueOf(((String[]) ov)[0]);
				hql = hql + " and " + ok + "=" + ((Object[]) ov)[0];
				
			}catch(Exception ex){
				hql = hql + " and " + ok + "='" + ((Object[]) ov)[0]+"'";
				
			}
			query =query+ ok + "=" +(((Object[]) ov)[0])+"&";
			
			
			
			
		}
		auto.setQuery(query);
		auto.setHql(hql);
		auto.setMap(parm);
		return auto;
	}
	public String getError(String message) {
		JSONObject obj = new JSONObject();
		obj.put("error", 1);
		obj.put("message", message);
		return obj.toJSONString();
	}
	
	public Integer autoPage(ModelMap map ,Long recordCount,int start,String action){
		Long sizePage = null;
		if(recordCount%10 !=0){
			sizePage =  recordCount/10+1;
		}else{
			sizePage =  recordCount/10;
		}
		if(start <=1){
			start =1;
		}
		if(start>=sizePage){
			start = Integer.parseInt(String.valueOf(sizePage));
		}
		long next = 0;
		if(start+1>=sizePage){
			map.put("next", sizePage);
		}else{
			map.put("next", start+1);
		}
		
		if(start-1 <=1){
			map.put("back", 1);
		}else{
			map.put("back", start-1);
		}
		map.put("current", start);
		map.put("count", recordCount);
		
		map.put("action", action);
		return start;
	}
	
	public Message checkLabelPage(Integer pageNum,Integer count){
		Message result = new Message();
		if( pageNum==null || pageNum <= 0 || count ==null || count <0){
			result.setMsg("参数错误：pageNum:"+pageNum+" count:"+count);
			result.setStatus(false);
			return result;
		}
		result.setStatus(true);
		return result;
	}
	
	public Message checkBook(Book book){
		Message message = new Message();
		//名称校验 
				if(StringUtils.isBlank(book.getName())){
					message.setStatus(false);
					message.setMsg("book名称不能为空");
					return message;
				}
				
				//描述校验
				if(StringUtils.isBlank(book.getDescription())){
					message.setStatus(false);
					message.setMsg("book描述不能为空");
					return message;
				}
				
				//价格
				if(book.getPrice() < 0){
					message.setStatus(false);
					message.setMsg("book价格不正确");
					return message;
				}
				
				//图书标签
				if(book.getLabel() == null || book.getLabel().getId() == null){
					message.setStatus(false);
					message.setMsg("book标签分类不能为空");
					return message;
				}
				
				message.setStatus(true);
				return message;
	}
	
}
