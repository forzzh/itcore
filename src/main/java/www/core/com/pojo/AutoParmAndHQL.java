package www.core.com.pojo;

import java.util.Map;

public class AutoParmAndHQL {

	private Map<String, Object> map;
	private String query = null;
	
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	private String hql;
	public void setHql(String hql) {
		this.hql = hql;
	}
	public String getHql() {
		return hql;
	}
}
