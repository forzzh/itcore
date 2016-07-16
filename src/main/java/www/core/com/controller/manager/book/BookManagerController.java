
package www.core.com.controller.manager.book;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import www.core.com.controller.base.BaseController;
import www.core.com.pojo.Book;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IBooKService;

@Controller
@RequestMapping("/manager/book")
public class BookManagerController extends BaseController<Book> {
	
	@Resource
	IBooKService bookService;
	

	public  Object detail(Integer id){
		
		return super.getById(Book.class, id);
	}
	
	/**
	 * ‘ˆº”Õº È
	 * @param book
	 * @return
	 * @throws Exception 
	 * @throws IOException 
	 * @throws RuntimeException 
	 */
	@ResponseBody
	@RequestMapping("/add")
	public Message addBook(Book book,HttpServletRequest request) throws RuntimeException, IOException, Exception{
		System.out.println(book);
		Message message = new Message();
		if(!super.checkBook(book).isStatus()){
			return super.checkBook(book);
		}
		message = bookService.addBook(request,book,super.getStaticUrl());
		return message;
		//throw new Exception ();
	}
	
	@RequestMapping("/addInit")
	public String addBookInit(Book book){
		return "manager/book/addbook";
	}
	
	@RequestMapping("/modify")
	public String modify(Integer id,ModelMap map,HttpServletRequest request){
		Message<User> message = new Message<User>();
	    Book book= bookService.bookExistencebyId(id);
	    map.put("book", book);
		return "manager/book/modify";
	}
	
	@ResponseBody
	@RequestMapping(value="/modifyBook",method=RequestMethod.POST)
	public Message modifyBook(Book book){
		Message<User> message = new Message<User>();
		System.out.println(book.getId());
		if(!super.checkBook(book).isStatus()){
			return super.checkBook(book);
		}
		message = bookService.modifyBook(book);
		return message;
	}
}

