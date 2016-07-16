package www.core.com.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import www.core.com.Enum.ReplyEnum;
import www.core.com.pojo.Book;
import www.core.com.pojo.Message;
import www.core.com.pojo.User;

public interface IBooKService {
	public Message addBook(HttpServletRequest request,Book book,String staticPath) throws RuntimeException, IOException, Exception ;
	public Book bookExistencebyId(Integer bookId);
	public Message<User> modifyBook(Book book);
	public Message<Book> queryBookList(Integer pageNum, Integer count,Integer labelId);
	
	/**
	 * 查询我关注的书籍
	 * @param start
	 * @param count
	 * @param user
	 * @return
	 */
	public Message<List<Book>> queryMyBookList(Integer start, Integer count,
			User user);
	
	
}
