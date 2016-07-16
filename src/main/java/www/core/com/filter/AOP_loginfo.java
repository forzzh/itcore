package www.core.com.filter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import www.core.com.pojo.Message;
import www.core.com.pojo.User;
import www.core.com.service.IUserService;
import www.core.com.utils.CORER;


@Aspect  
@Component  
public class AOP_loginfo {
	
	@Autowired
	IUserService userService;
	/**  
     * 所有带RequestMapping注解的方法  
     */  
    private final static String el = "@annotation(org.springframework.web.bind.annotation.RequestMapping)";  
  
    @Before(el)  
    public void before() {
    	
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	HttpSession session = request.getSession();
    	User user = (User) session.getAttribute(CORER.SESSION_USER);
    	Cookie[] cookies = request.getCookies();
    	if (cookies != null && user == null) {
    		String email = null;
			String password = null;
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if ("email".equals(name)) {
					email = cookie.getValue();
				}
				if ("password".equals(name)) {
					password = cookie.getValue();
				}
			}
			user = new User();
			user.setEmail(email);
			user.setPassword(password);
			Message<User> loginMsg = userService.login(user);
			if (loginMsg.isStatus()) {
				user = loginMsg.getData();
				request.getSession().setAttribute(CORER.SESSION_USER, user);
			}
    	}
    }  
  
    @After(el)  
    public void after() {
        System.out.println("执行之后");  
    }  
  
    @Around(el)  
    public Object around(ProceedingJoinPoint p) {  
        for (Object obj : p.getArgs()) {  
            System.out.println("参数:" + obj);  
        }  
        Object ob = null;  
        try {  
            System.out.println("around前");  
            ob = p.proceed();  
            System.out.println("around后");  
        } catch (Throwable e) {  
            e.printStackTrace();  
        }  
  
        return ob;  
    }  
      
    @AfterThrowing(value = el, throwing="e")  
    public void throwing(Exception e){  
        System.out.println("出异常了"+e);  
    }  
}
