package www.core.com.service;

import java.util.List;

import www.core.com.pojo.Label;
import www.core.com.pojo.Message;

public interface ILabelService {
	
	public Message<Label> getLabelsWhoCreate(Integer user,Integer start,Integer row);
	
	
	public Message<Label> addNewLableManager(Label label,Integer userId);
	/**
	 * �������ƻ�ȡ��ǩ �̰߳�ȫ
	 * 
	 * @param Label
	 * @return Message
	 */
	public Label labelExistence(Label label);
	public Label labelExistencebyId(Integer labelId);
	

	public Label addNewLable(Label label,Integer userId);

	public Message<Label> setLabelName(String newName ,Label label);

	/**
	 * �������ƻ�ȡ��ǩ �̲߳���ȫ
	 * 
	 * @param Label
	 * @return Message
	 */
	public Label labelExistenceLock(Label label);

	/**
	 * ��ǩid ��ע��ǩ�� Ȧ��group=group-1
	 * 
	 * @param id
	 */
	public void labelDeleteGroupDeleteLock(Integer id);

	/**
	 * ������Ȧ�Ӹ��������͸��¹�ע��
	 * 
	 * @param label
	 */
	public Label labelUpdateOrAddPersonAndGroup(Label label, Integer userid);

	/**
	 * ���������� ���¹�ע�˺͹�ע������
	 * 
	 * @param label
	 */
	public Label labelUpdateOrAddPersonAndGroupByTopic(Label label, Integer userid);
	/**
	 * ����ҳ��͸�������Labels
	 * @param pageNum ҳ��
	 * @param count   ����
	 * @return
	 */
	public Message<List<Label>> queryLabelPageData(Integer pageNum,Integer count);
	
	/**
	 * ����ҳ��͸�������Labels
	 * @param start ��ʼ����
	 * @param count  ��ʾ����
	 * @return
	 */
	public List<Label> queryLabelData(Integer start,int rows);


	/**
	 * ���ӹ�ע�鼮������
	 * @param labelId
	 * @return
	 */
	public Message<Label> labelIncfoucsBookByLabelId(Integer labelId);


	public Message<List<Label>> queryLabelNamePage(Integer pageNum,
			Integer count,boolean flag,Integer userid);


	public Message<List<Label>> queryLabelByName(String name,boolean flag ,Integer userid);  

	/**
	 * ���ݱ�ǩname��ѯ��ǩ	
	 * @param labelname
	 * @return
	 */
	public Label queryLabelByName(String labelname);

	public List<Label> getRandomLabelList(Integer Num);

	/**
	 * ������ű�ǩ
	 * @return
	 */
	public List<Label> getHotLabels();  
}
      