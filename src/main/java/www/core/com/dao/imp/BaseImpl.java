
package www.core.com.dao.imp;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.util.Version;
import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.springframework.orm.hibernate4.HibernateTemplate;

import www.core.com.dao.BaseDAO;
import www.core.com.pojo.BaseCode;
import www.core.com.utils.Page;

import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;

public class BaseImpl<T> implements BaseDAO {
	private static final Logger logger = Logger.getLogger(BaseDAO.class);

	SessionFactory sessionFactory;
	HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	private Session getCurrentSession() {
		
		Session session =sessionFactory.getCurrentSession();
		session.setFlushMode(FlushMode.ALWAYS);
		session.flush();
		return session;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public List getAll(Class T) {
		// TODO Auto-generated method stub
		List<Class> result = getCurrentSession().createQuery("from " + T.getName()).list();
		return result;
	}
	public void updateSearch(Object t) throws InterruptedException {
		// TODO Auto-generated method stub
		sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		// fullTextSession.index(t);
		fullTextSession.setFlushMode(FlushMode.MANUAL);
		fullTextSession.setCacheMode(CacheMode.IGNORE);
		fullTextSession.createIndexer().startAndWait();
		// fullTextSession.index(t);
		
		hibernateTemplate.update(t);
		// getCurrentSession().save(t);
		if(t!=null){
			this.getCurrentSession().evict(t);
		}
		
	}
	@Override
	public T findByColumn(Class T, String column, Object where) {
		// TODO Auto-generated method stub
		if (column == null) {
			column = "id";
		}
		Session session = getCurrentSession();
		logger.debug("basedao:from " + T.getSimpleName() + " where " + column + "=" + ":" + column);
		Query query = session.createQuery("from " + T.getSimpleName() + " where " + column + "=" + ":" + column);
		query.setParameter(column, where);
		
		T entity = (T) query.uniqueResult();
		if(entity!=null){
			this.getCurrentSession().evict(entity);
		}
		return entity;
	}

	
	@Override
	public T findByColumn(Class T,String hql) {
		// TODO Auto-generated method stub
		
		Session session = getCurrentSession();
		Query query = session.createSQLQuery(hql).addEntity(T);
		
		T entity = (T) query.uniqueResult();
		if(entity!=null){
			this.getCurrentSession().evict(entity);
		}
		return entity;
	}
	@Override
	public void save(Object... entity) {
		// TODO Auto-generated method stub
		for (Object object : entity) {
			getCurrentSession().save(object);
		}

	}

	public void saveOrUpdate(BaseCode... entity) {

		for (BaseCode baseCode : entity) {
			if (baseCode.getId() == null)
				getCurrentSession().save(baseCode);
			else
				getCurrentSession().update(baseCode);
		}

	}

	@Override
	public List getList(Object t) {
		String hql = " from " + t.getClass().getSimpleName();
		return getCurrentSession().createQuery(hql).list();
	}

	@Override
	public List getListByCollection(String sql, Map map, int start, int row) {
		Query query = getCurrentSession().createQuery(sql);
		if (map != null) {
			for (Object object : map.keySet()) {
				Object obj = map.get(object);
				query.setParameterList(object.toString(), (Collection) obj);
			}
		}

		if (start != -1) {
			System.out.println("row============="+row+"start============"+start);
			query.setMaxResults(row);
			query.setFirstResult(start);
		}

		return query.list();
	}
	
	@Override
	public List getListByPage(String sql, Map map, int start, int row) {
		Query query = getCurrentSession().createQuery(sql);
		if (map != null) {
			for (Object object : map.keySet()) {
				Object obj = map.get(object);
				if (obj.getClass().isArray()) {
					query.setParameterList(object.toString(), (Object[]) obj);
				} else {
					query.setParameter(object.toString(), obj);
				}
			}
		}

		if (start != -1) {
			query.setMaxResults(row);
			query.setFirstResult(start);
		}

		return query.list();
	}

	@Override
	public List getListByPageByObject(String sql, Map map, int start, int row) {
		// TODO Auto-generated method stub
		return this.getListByPage(sql, map, start, row);
	}

	@Override
	public void delete(Object... entity) {
		for (Object t : entity) {
			getCurrentSession().delete(t);
		}
	}

	@Override
	public Object findByColumn(String hql, Map parm) {

		Session session = getCurrentSession();
		Query query = getCurrentSession().createQuery(hql).setFirstResult(0).setMaxResults(1);
		if (parm != null) {
			Object result = null;
			for (Object key : parm.keySet()) {
				query.setParameter(key.toString(), parm.get(key));

			}
		}
		Object T =query.uniqueResult();
		if(T!=null){
			this.getCurrentSession().evict(T);
		}
		return T;
	}

	@Override
	public Object findByColumn(Class T, String hql, Map parm) {
		// TODO Auto-generated method stub
		Session session = getCurrentSession();
		Query query = getCurrentSession().createQuery(hql).setFirstResult(0).setMaxResults(1);
		if (parm != null) {
			for (Object key : parm.keySet()) {
				query.setParameter(key.toString(), parm.get(key));
			}
		}
		return query.uniqueResult();
	}

	@Override
	public Long count(String sql, Map map) {
		Query query = getCurrentSession().createQuery(sql);
		if (map != null) {
			for (Object object : map.keySet()) {
				query.setParameter(object.toString(), map.get(object));
			}
		}

		return (Long) query.uniqueResult();
	}

	@Override
	public void delete(Object entity) {
		getCurrentSession().delete(entity);
	}

	@Override
	public int executeUpdate(String hql) {
		return getCurrentSession().createQuery(hql).executeUpdate();
	}

	@Override
	public void saveOrUpdate(Object... entity) {
		for (Object object : entity) {
			getCurrentSession().saveOrUpdate(object);
		}

	}

	@Override
	public int jdbcUpdate(String sql) {
		// TODO Auto-generated method stub

		return getCurrentSession().createSQLQuery(sql).executeUpdate();
	}
	
	@Override
	public int updateHql(String sql) {
		// TODO Auto-generated method stub

		return getCurrentSession().createQuery(sql).executeUpdate();
	}

	@Override
	public int jdbcSelectLock(String sql) {
		// TODO Auto-generated method stub
		getCurrentSession().createSQLQuery(sql).list();
		return 0;
	}

	@Override
	public T jdbcSelectLockRetunBean(String sql, Class t) {

		// TODO Auto-generated method stub
		Object object = getCurrentSession().createSQLQuery(sql).addEntity(t).uniqueResult();
		return (T) object;
	}

	@Override
	public void executeSearch(Object t) throws InterruptedException {
		// TODO Auto-generated method stub
		sessionFactory = this.getHibernateTemplate().getSessionFactory();
		Session session = sessionFactory.openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		// fullTextSession.index(t);
		fullTextSession.setFlushMode(FlushMode.MANUAL);
		fullTextSession.setCacheMode(CacheMode.IGNORE);
		fullTextSession.createIndexer().startAndWait();
		// fullTextSession.index(t);
		hibernateTemplate.save(t);
		// getCurrentSession().save(t);
		if(t!=null){
			this.getCurrentSession().evict(t);
		}
		
	}

	@Override
	public Page getSearch(Class t, int page, String search, String[] column) throws ParseException {
		// TODO Auto-generated method stub
		FullTextSession fullTextSession = Search.getFullTextSession(getCurrentSession());

		Object[] resultData = new Object[] { 3 };
		Term term = null;  
		BooleanQuery booleanQuery = new BooleanQuery();
		
		
		term =new Term("status", "delete");
		TermQuery termQuery = new TermQuery(term);  
		booleanQuery.add(termQuery, Occur.MUST_NOT);
		
		
		WildcardQuery wildcardQuery =null;
		for (int i = 0; i < resultData.length; i++) {
			if(resultData[i] == null){
				continue;
			}
			term =new Term(column[i], search+"*");
			wildcardQuery = new WildcardQuery(term);
			booleanQuery.add(wildcardQuery, Occur.MUST);
		}
		FullTextQuery fullTextQuery =fullTextSession.createFullTextQuery(booleanQuery, t);
		
		
//		QueryBuilder queryBuilder = fullTextSession.getSearchFactory().buildQueryBuilder().forEntity(t).get();
//		// 在这里修改要查询的字段，比如可以改成introduce
//		org.apache.lucene.search.Query luceneQuery = queryBuilder.keyword().fuzzy() .withThreshold( .8f )  
//		        .withPrefixLength( 1 )  .onFields(column).boostedTo(3).matching(search)
//				.createQuery();
//		FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(luceneQuery, t);
		fullTextQuery.setFirstResult((page - 1));
		fullTextQuery.setMaxResults(10);
		List result = fullTextQuery.list();
		Page pageUtils = new Page(fullTextQuery.getResultSize(), result);
		return pageUtils;

	}

	@Override
	public void saveOrUpdate(Object entity) {

		getCurrentSession().saveOrUpdate(entity);
		if(entity!=null){
			this.getCurrentSession().evict(entity);
		}
	}

	@Override
	public List getListByName(String hql) {
		Query query = getCurrentSession().createQuery(hql);
		return query.list();
	}
}
