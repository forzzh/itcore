package www.core.com.annotation;
import java.lang.annotation.Documented;  
import java.lang.annotation.ElementType;  
import java.lang.annotation.Retention;  
import java.lang.annotation.RetentionPolicy;  
import java.lang.annotation.Target;

import www.core.com.pojo.BaseCode;  
  

@Documented//�ĵ�  
@Retention(RetentionPolicy.RUNTIME)//������ʱ���Ի�ȡ  
@Target({ ElementType.TYPE, ElementType.METHOD,ElementType.FIELD })//���õ��࣬�������ӿ��ϵ�  
public @interface MethodInfo {
	  public String label() default ""; 
	  public String dataname() default ""; 
	  public String isShow() default "";
	  public String getEnum() default "";
	  public boolean issearch( ) default true;
	  public String idKey () default "";
	  public Class detail() default BaseCode.class;
}
