package www.core.com.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import com.alibaba.fastjson.JSON;

import www.core.com.pojo.Message;

public class ExceptionClass  extends SimpleMappingExceptionResolver{
	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		 String viewName = determineViewName(ex, request); 
		 //response.reset();
		 Message message = new Message<>();
         message.setMsg("系统异常，请稍后再试");
         message.setStatus(false);
         
		 if (viewName != null) {// JSP格式返回  
			 if(request.getHeader("accept") != null&& request.getHeader("accept").indexOf("json")>=0){
				 
				  try {  
					  
	                	PrintWriter writer = response.getWriter();  
	                   
	                    writer.write(JSON.toJSONString(message)); 
//	                    writer.flush();  
//	                    writer.close();
	                   
	                } catch (IOException e) {  
	                    e.printStackTrace();  
	                } 
	                return null;  
//	                if (!(request.getHeader("accept").indexOf("application/json") > -1 || (request  
//		                    .getHeader("X-Requested-With")!= null && request  
//		                    .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1)))
			 }else  {  
				 
				    Integer statusCode = determineStatusCode(request, viewName);  
	                if (statusCode != null) {  
	                    applyStatusCodeIfPossible(request, response, statusCode);  
	                }  
	                request.setAttribute("msg", message);
	                
	                return new ModelAndView( "redirect:http://"+request.getServerName()+"/500.html" );
			 }
		 }
		return super.doResolveException(request, response, handler, ex);
	}
	private void noHandlerFound() {
		// TODO Auto-generated method stub
		System.out.println(123);
	}
	
}
