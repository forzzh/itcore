package www.core.com.service;
import www.core.com.pojo.FocusTag;
import www.core.com.pojo.Label;
import www.core.com.pojo.Message;
public interface IFocusTagService {
	public FocusTag foucusLabel(Integer userid,Label   label );
	public void saveFoucusLabel(Integer userid,Label   label );
	
	public Message dofocus(Integer userid ,Integer labelid);
}
