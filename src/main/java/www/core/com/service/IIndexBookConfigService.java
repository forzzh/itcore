package www.core.com.service;

import java.util.List;

import www.core.com.pojo.Book;
import www.core.com.pojo.IndexBookConfig;
import www.core.com.pojo.IndexTopicConfig;
import www.core.com.pojo.Message;

public interface IIndexBookConfigService {
	public Message<IndexBookConfig> setBookRange(Integer bookId ,Integer range);
	public List<IndexBookConfig> getBookRangeList(Integer start , Integer row);
}
