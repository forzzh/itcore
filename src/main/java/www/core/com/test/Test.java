package www.core.com.test;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import www.core.com.annotation.MethodInfo;
import www.core.com.pojo.User;



public class Test {

	/**
	 * @param args
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws NoSuchFieldException 
	 * @throws IntrospectionException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, NoSuchFieldException, IntrospectionException, ClassNotFoundException {
		// TODO Auto-generated method stub
		  Class<?> cls = Class.forName(User.class.getName());
          Field[] fields = cls.getDeclaredFields();
          
          for (Field field : fields) {
              if(field.isAnnotationPresent(MethodInfo.class)==true){
            	  MethodInfo name = field.getAnnotation(MethodInfo.class);
                  System.out.println(name.dataname());
                  System.out.println(name.label());
                  System.out.println(name.isShow());
              }
             
          }
	}

}
