package www.core.com.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import www.core.com.Enum.ModelEnum;
import www.core.com.Enum.ReplyEnum;
import www.core.com.pojo.Book;
import www.core.com.pojo.FoucusTopicorBook;
import www.core.com.pojo.Message;
import www.core.com.pojo.Reply;
import www.core.com.pojo.TopicGetImage;
import www.core.com.pojo.User;
import www.core.com.service.IBooKService;
import www.core.com.service.IFocusTopicOrBookService;
import www.core.com.service.ILabelService;
import www.core.com.utils.HtmlGetImg;
import www.core.com.utils.Uploader;

@Service("bookService")
@Transactional(rollbackFor={IOException.class,Exception.class,RuntimeException.class})
public class BookServiceImpl extends AbstractService<Book> implements IBooKService {

	
	@Autowired
	ILabelService labelService;
	
	@Autowired
	IFocusTopicOrBookService focusTopicOrBookService;
	
	/**
	 * 添加图书
	 * @throws Exception 
	 */
	@Override
	public Message addBook(HttpServletRequest request,Book book,String staticPath) throws Exception {
		book.setShare(0);
		Message message = new Message();
		//更新标签的关注情况
		Message labelMessage = labelService.labelIncfoucsBookByLabelId(book.getLabel().getId());
		
		if (!labelMessage.isStatus()) {
			return labelMessage;
		}
		TopicGetImage imgs = HtmlGetImg.content(book.getContent());
		String image = book.getImage();
		Uploader uploader = new Uploader();
		uploader.upload(staticPath + image, image.replace("/temp", "/topic"));
		image = image.replace("/temp", "/topic");
		book.setImage(image);
		hibernateDAO.saveOrUpdate(book);
		
		try {
			for (String img : imgs.getImages()) {
				uploader.upload(staticPath + img, img.replace("/temp", "/topic"));
			}
		} catch (IOException e) {
			for (String img : imgs.getImages()) {
				uploader.delete(img.replace("/temp", "/topic"));
			}
			throw new Exception("帖子添加失败");
		}
		message.setStatus(true);
		message.setMsg("保存成功");
		return message;
	}

	@Override
	public Book bookExistencebyId(Integer bookId) {
		//Book result = getById(Book.class, bookId);
		
		Book result  = hibernateDAO.findByColumn(Book.class,"id",bookId);
		return result;
	}

	@Override
	public Message<User> modifyBook(Book book) {
		Message message = new Message();
		Book oldBook = hibernateDAO.findByColumn(Book.class, "id", book.getId());
		System.out.println();
			oldBook.setAuthor(book.getAuthor());
			oldBook.setContent(book.getContent());
			oldBook.setDescription(book.getDescription());
			oldBook.setLabel(book.getLabel());
			oldBook.setName(book.getName());
			oldBook.setPrice(book.getPrice());
			System.out.println(book.getImage());
			if(book.getImage()!=null &&!"".endsWith(book.getImage())){
				oldBook.setImage(book.getImage());
			}
		hibernateDAO.saveOrUpdate(oldBook);
		message.setStatus(true);
		return message;
	}


	@Override
	public Message<Book> queryBookList(Integer pageNum, Integer count ,Integer labelId) {
		Message<Book> result = new Message<>();
		String hql ;
		Map param = null;
		//根据标签查询
		if(labelId!= null){
			hql = " from " + Book.class.getSimpleName() + " book where labelId = :labelId order by createDate desc";
			param = new HashMap<>();
			param.put("labelId", labelId);
		}else{
			hql = " from " + Book.class.getSimpleName() + " book order by createDate desc";
		}
		try{
			int start = pageNum == 1 ? 0 :(pageNum-1)*(count-1);
			List<Book> bookList = hibernateDAO.getListByPageByObject(hql, param, start, count);
			result.setList(bookList);
			result.setStatus(true);
		}catch(Exception e){
			result.setMsg("查询错误:" + e.getMessage());
			result.setBasic(false);
		}
		return result;
	}

	@Override
	public Message<List<Book>> queryMyBookList(Integer start, Integer count,
			User user) {
		Message<List<Book>> result = new Message<>();
		Message<List<FoucusTopicorBook>> mybookMessage = focusTopicOrBookService.queryMyFocusList(start, count, user, ModelEnum.book);
		List<FoucusTopicorBook> ftbList = mybookMessage.getData();
		if (ftbList == null || ftbList.size() == 0) {
			result.setData(Collections.<Book> emptyList());
			result.setStatus(true);
			return result;
		}
		
		Integer[] ids = new Integer[ftbList.size()];
		
		for (int i = 0, len = ftbList.size(); i < len; i++) {
			Integer bookId = ftbList.get(i).getCommonId();
			ids[i] = bookId;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		String hql = "from Book where id in :ids";
		params.put("ids", ids);
		List<Book> books = hibernateDAO.getListByPageByObject(hql, params, -1, 0);
		
		result.setData(books);
		result.setStatus(true);
		return result;
	}

}
