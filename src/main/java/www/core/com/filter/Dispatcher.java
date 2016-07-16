package www.core.com.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class Dispatcher  extends DispatcherServlet{
	public Dispatcher() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		// TODO Auto-generated method stub
		super.setApplicationContext(applicationContext);
	}
	@Override
	protected void noHandlerFound(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
		// TODO Auto-generated method stub
		super.noHandlerFound(arg0, arg1);
	}
	
	
}
