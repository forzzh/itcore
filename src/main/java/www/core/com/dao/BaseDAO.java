
package www.core.com.dao;

import java.util.List;
import java.util.Map;

import org.apache.lucene.queryParser.ParseException;

import www.core.com.pojo.Label;
import www.core.com.utils.Page;

public interface BaseDAO<T> {

	public void updateSearch(Object t) throws InterruptedException ;
	public List<T> getAll(Class T);
	
	/**
	 * ��ָ�����е�ָ���ֶ��в���
	 * @param T ָ���ı�����ͨ��.class����
	 * @param column �ֶ���
	 * @param value ���ҵ�����
	 * @return T ����Ψһֵ(uniqueResult),���ز��ҵĽ������Ϊָ���ı������
	 */
	public T findByColumn(Class T,String column,Object value);
	public void save(T... entity);
	public void saveOrUpdate(T ... entity);
	public void saveOrUpdate(Object entity);
	public T findByColumn(String hql,Map parm);
	public void delete(T... entity);
	public T findByColumn(Class T,String hql,Map parm);
	public List<T> getList(T t); 
	public  List getListByPage(String sql,Map<String,String>  map,int start,int row);
	public  List getListByPageByObject(String sql,Map<String,Object>  map,int start,int row);
	public  Long count(String sql,Map<String,Object>  map);
	
	public void delete(T entity);
	
	public int executeUpdate(String hql);
	public int jdbcUpdate(String hql);
	public int jdbcSelectLock(String sql) ;
	public T jdbcSelectLockRetunBean(String sql,Class t) ;
	
	public void executeSearch(T t) throws InterruptedException;
	public Page getSearch(Class t,int page,String search,String...  column ) throws ParseException;
	public int updateHql(String sql) ;

	public List<T> getListByName(String hql);

	List getListByCollection(String sql, Map map, int start, int row);
	public T findByColumn(Class T,String hql);

	
}

