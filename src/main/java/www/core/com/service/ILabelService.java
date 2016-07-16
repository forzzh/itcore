package www.core.com.service;

import java.util.List;

import www.core.com.pojo.Label;
import www.core.com.pojo.Message;

public interface ILabelService {
	
	public Message<Label> getLabelsWhoCreate(Integer user,Integer start,Integer row);
	
	
	public Message<Label> addNewLableManager(Label label,Integer userId);
	/**
	 * 根据名称获取标签 线程安全
	 * 
	 * @param Label
	 * @return Message
	 */
	public Label labelExistence(Label label);
	public Label labelExistencebyId(Integer labelId);
	

	public Label addNewLable(Label label,Integer userId);

	public Message<Label> setLabelName(String newName ,Label label);

	/**
	 * 根据名称获取标签 线程不安全
	 * 
	 * @param Label
	 * @return Message
	 */
	public Label labelExistenceLock(Label label);

	/**
	 * 标签id 关注标签的 圈子group=group-1
	 * 
	 * @param id
	 */
	public void labelDeleteGroupDeleteLock(Integer id);

	/**
	 * 如果添加圈子更新数量和更新关注人
	 * 
	 * @param label
	 */
	public Label labelUpdateOrAddPersonAndGroup(Label label, Integer userid);

	/**
	 * 如果添加帖子 更新关注人和关注帖子数
	 * 
	 * @param label
	 */
	public Label labelUpdateOrAddPersonAndGroupByTopic(Label label, Integer userid);
	/**
	 * 根据页码和个数返回Labels
	 * @param pageNum 页码
	 * @param count   个数
	 * @return
	 */
	public Message<List<Label>> queryLabelPageData(Integer pageNum,Integer count);
	
	/**
	 * 根据页码和个数返回Labels
	 * @param start 起始条数
	 * @param count  显示个数
	 * @return
	 */
	public List<Label> queryLabelData(Integer start,int rows);


	/**
	 * 增加关注书籍的数量
	 * @param labelId
	 * @return
	 */
	public Message<Label> labelIncfoucsBookByLabelId(Integer labelId);


	public Message<List<Label>> queryLabelNamePage(Integer pageNum,
			Integer count,boolean flag,Integer userid);


	public Message<List<Label>> queryLabelByName(String name,boolean flag ,Integer userid);  

	/**
	 * 根据标签name查询标签	
	 * @param labelname
	 * @return
	 */
	public Label queryLabelByName(String labelname);

	public List<Label> getRandomLabelList(Integer Num);

	/**
	 * 获得热门标签
	 * @return
	 */
	public List<Label> getHotLabels();  
}
      