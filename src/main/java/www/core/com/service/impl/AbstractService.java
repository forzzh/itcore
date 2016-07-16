package www.core.com.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import www.core.com.dao.BaseDAO;
import www.core.com.service.BaseService;

@Service
@Transactional
public class AbstractService<T> extends BaseService {

	@Resource(name = "hibernateDAO")
	BaseDAO<T> hibernateDAO;
	@Autowired
	EhCacheCacheManager cacheManager;

	public BaseDAO<T> getHibernateDAO() {
		return hibernateDAO;
	}

	public void setHibernateDAO(BaseDAO<T> hibernateDAO) {
		this.hibernateDAO = hibernateDAO;
	}

	@Transactional(readOnly = true)
	public List getAutoListByPage(String sql, Map<String, String> map, int start, int row) {

		return hibernateDAO.getListByPage(sql, map, start, row);
	}

	@Transactional(readOnly = true)
	public List getAll(Class T) {
		// TODO Auto-generated method stub
		return hibernateDAO.getAll(T);
	}

	@Transactional(readOnly = true)
	public Long count(String sql, Map<String, Object> map) {
		return hibernateDAO.count(sql, map);
	}

	@Transactional(readOnly = true)
	public T findByColumn(Class T, String column, Object value) {
		// TODO Auto-generated method stub
		return hibernateDAO.findByColumn(T, column, value);
	}

	@Transactional(readOnly = true)
	public T findByColumn(String hql, Map parm) {
		return hibernateDAO.findByColumn(hql, parm);
	}

	public void save(T... entity) {
		// TODO Auto-generated method stub
		hibernateDAO.save(entity);
	}

	public void saveOrUpdate(T... entity) {
		// TODO Auto-generated method stub
		hibernateDAO.saveOrUpdate(entity);
	}

	public T getById(Class t, Integer id) {
		// TODO Auto-generated method stub
		return hibernateDAO.findByColumn(t, "id", id);
	}

	public ValueWrapper getCache(String key) {
		return cacheManager.getCache("serviceCache").get(key);
	}

	public void put(String key, Object value) {
		cacheManager.getCache("serviceCache").put(key, value);
	}
	public void updateStatus(String entity,Integer id,String status){
		hibernateDAO.updateHql("update "+entity+" set status='"+status+"' where id="+id);
	}
}
