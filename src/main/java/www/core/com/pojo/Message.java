package www.core.com.pojo;

import java.util.List;

public class Message<T> {
	private String msg;
	private T data;
	private boolean status;
	private List<T> list;
	private Object basic;
	private User user ;
	private TopicContent topicContent;
	public void setTopicContent(TopicContent topicContent) {
		this.topicContent = topicContent;
	}
	public TopicContent getTopicContent() {
		return topicContent;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public User getUser() {
		return user;
	}
	public Object getBasic() {
		return basic;
	}
	public void setBasic(Object basic) {
		this.basic = basic;
	}
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	
}
