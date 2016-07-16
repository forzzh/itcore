
package www.core.com.controller.book;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.Enum.CategroyEnum;
import www.core.com.Enum.ReplyEnum;
import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Book;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
import www.core.com.pojo.Topic;
import www.core.com.pojo.User;
import www.core.com.service.IBooKService;
import www.core.com.service.ILabelService;
import www.core.com.service.ITopicService;
import www.core.com.service.IUserService;
import www.core.com.utils.CORER;

@Controller
@RequestMapping("book")
public class BookController extends BaseController<Book> {

	@Autowired
	IUserService userService;
	@Resource
	IBooKService bookService;
	
	@Resource
	ILabelService labelService;
	
	@Resource
	ITopicService topicService;
	
	@RequestMapping("/{id}")
	public  Object detail( @PathVariable Integer id){
		
		return super.getById(Book.class, id);
	}
	@RequestMapping("/list")
	public String detail(ModelMap map){
		//����Ҳ����ű�ǩ
		List<Label> labels = labelService.getHotLabels();
		//��������б�
		List<Topic> adTopics = topicService.getHotTopicByCategroyName(CategroyEnum.project, 0, 5);
		//���Żش��б�
		List<Topic> requestTopics = topicService.getHotTopicByCategroyName(CategroyEnum.question, 0, 5);
		
		map.put("staticurl", super.getQiqiuUrl());
		map.put("labels", labels);
		map.put("adTopics", adTopics);
		map.put("requestTopics", requestTopics);
		return  "/web/book/list";
	}
	
	@RequestMapping("/queryBookList")
	@ResponseBody
	public Message<Book> queryBookList(Integer pageNum,Integer count,Integer labelId){
		Message result = new Message<>();
		if(pageNum <1 ){
			result.setMsg("��������pageNum ��" +pageNum);
			result.setStatus(false);
			return result;
		}
		if(count == null || count <0){
			count = 10;
		}
		return bookService.queryBookList(pageNum, count, labelId);
	}
	
	@RequestMapping("other/{userid}")
	public String othermy(@PathVariable Integer userid,HttpServletRequest request,ModelMap model){
		if(userid==null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		model.put("userdata",	(userService.userQuery(userid)));
		model.addAttribute("userid",userid);
		model.addAttribute("userId",userid);
		model.addAttribute("show", false);
		model.addAttribute("static",super.getQiqiuUrl());
		return "/web/user/my/mybook";
	}
	@RequestMapping("/my")
	public String my(Integer userid,HttpServletRequest request,ModelMap model,HttpSession session){
		User user = (User) session.getAttribute(SESSION_USER);
		if(user == null){
			return "redirect:http://"+request.getServerName()+"/user/login";
		}
		
		model.addAttribute("userId",user.getId());
		model.addAttribute("show", true);
		model.addAttribute("static",super.getQiqiuUrl());
		model.addAttribute("userdata",	(userService.userQuery(user.getId())));
		return "/web/user/my/mybook";
	}
	
	@ResponseBody
	@RequestMapping("/queryMyBookList")
	public Message<List<Book>> queryMyBookList(Integer start, Integer count, Integer labelId, HttpSession session,User user){
		Message<List<Book>>  result = new Message<>();
	
		if (start < 0 ) {
			result.setMsg("��������start ��" +start);
			result.setStatus(false);
			return result;
		}
		if (count == null || count < 0) {
			count = 10;
		}
		return bookService.queryMyBookList(start, count, user);
	}
	
	@RequestMapping("/detail/{id}")
	public String bookDetail(@PathVariable Integer id,ModelMap map,HttpServletRequest request){
		Book book = bookService.bookExistencebyId(id);
		//����鱾������ �����Ѿ�ɾ�� ֱ�ӵ�ת����ҳ��
		if(book == null){
			return "redirect:http://"+request.getServerName()+"/404.html";
		}
		map.put("book", book);
		map.put("id", id);
		User user = (User) request.getSession().getAttribute(CORER.SESSION_USER);//��½�û�
		if(user !=null){
			map.put("userId", user.getId());
		}
		map.put("static", super.getQiqiuUrl());
		return "/web/book/bookDetail";
	}
	@RequestMapping("/detail/{id}/wap")
	public String wapbookDetail(@PathVariable Integer id,ModelMap map,HttpServletRequest request){
		Book book = bookService.bookExistencebyId(id);
		//����鱾������ �����Ѿ�ɾ�� ֱ�ӵ�ת����ҳ��
		if(book == null){
			return "redirect:http://"+request.getServerName()+"/404.html";
		}
		map.put("book", book);
		map.put("id", id);
		User user = (User) request.getSession().getAttribute(CORER.SESSION_USER);//��½�û�
		if(user !=null){
			map.put("userId", user.getId());
		}
		map.put("static", super.getQiqiuUrl());
		return "/wap/book";
	}
	
	/**
	 * �ظ�����
	 * @param bookid    �鼮id
	 * @param CategroyEnum ���ӷ�������
	 * @param content	  �ظ�����
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/reply")
	public Message replyTopic(HttpServletRequest request,Integer bookid,Integer groupid,String content,HttpSession session){
		Message message = new Message();
		User user = (User) session.getAttribute(CORER.SESSION_USER);//��½�û�
		if(user == null){
			message.setStatus(false);
			message.setMsg("�û�δ��¼");
			return message;
		}
		//�ظ�����Ϊ�鼮
		ReplyEnum replayEnum = ReplyEnum.BookReply;
		if(bookid == null){
			message.setStatus(false);
			message.setMsg("�鼮id����Ϊ��");
			return message;
		}
		
		if(StringUtils.isBlank(content)){
			message.setStatus(false);
			message.setMsg("�ظ����ݲ���Ϊ��");
			return message;
		}
		message = topicService.addReply(bookid,groupid,user,replayEnum,content,null);
		return message;
	}
}

