package www.core.com.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import www.core.com.pojo.Book;
import www.core.com.pojo.IndexBookConfig;
import www.core.com.pojo.IndexLabelConfig;
import www.core.com.pojo.IndexTopicConfig;
import www.core.com.pojo.Message;
import www.core.com.service.IBooKService;
import www.core.com.service.IIndexBookConfigService;

@Service("indexBookConfigService")
public class IndexBookConfigServiceImpl extends AbstractService<IndexBookConfig> implements IIndexBookConfigService {

	@Autowired
	IBooKService bookService;

	IndexBookConfig getIndexBookConfigByBookId(Integer bookId) {
		IndexBookConfig result = getById(IndexBookConfig.class, bookId);
		if (result == null) {
			result = new IndexBookConfig();
		}
		return result;
	}

	@Override
	public Message<IndexBookConfig> setBookRange(Integer bookId, Integer range) {
		Message<IndexBookConfig> indexBookMsg = new Message<IndexBookConfig>();

		Book oldBook = bookService.bookExistencebyId(bookId);
		if (oldBook == null) {
			indexBookMsg.setMsg("书不存在");
			indexBookMsg.setStatus(false);
			return indexBookMsg;
		}

		IndexBookConfig indexBookConfig = getIndexBookConfigByBookId(bookId);

		indexBookConfig.setBook(oldBook);
		indexBookConfig.setSort(range);

		saveOrUpdate(indexBookConfig);

		indexBookMsg.setMsg("设置成功");
		indexBookMsg.setStatus(true);

		return indexBookMsg;
	}

	@Override
	public List<IndexBookConfig> getBookRangeList(Integer start, Integer row) {
		Message<IndexBookConfig> indexMsg = new Message<>();
		List books = hibernateDAO.getListByPageByObject(
				"select ibc.book from IndexBookConfig ibc order by ibc.sort", null, start, row);
		
		return books;
	}

}
