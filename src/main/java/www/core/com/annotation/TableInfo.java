package www.core.com.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented//�ĵ�  
@Retention(RetentionPolicy.RUNTIME)//������ʱ���Ի�ȡ  
@Target({ ElementType.TYPE, ElementType.METHOD,ElementType.FIELD })//���õ��࣬�������ӿ��ϵ�
public @interface TableInfo {

	public String defend() default "defend";
	public String listview() default "list";
	public Class packageName() ;
	
	public String search() default "";
	public String[] doMethodName() default "";
	public String[] doMethodURL() default "";
}
