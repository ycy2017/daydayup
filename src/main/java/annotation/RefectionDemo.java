package annotation;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class RefectionDemo extends T<String> {
	
	public RefectionDemo(){
		 Type type = getClass().getGenericSuperclass();
		 //类对象
		 System.out.println("getClass() == " + getClass());  
	     //父类类型，最近的父类
		 System.out.println("type = " + type);
//		 Class<T> entity =  (Class<T>) ((ParameterizedType)type).getActualTypeArguments()[0];  
//		 System.out.println(entity);
		 
		 Class<T> entity =  (Class<T>) ((ParameterizedType)type).getActualTypeArguments()[0];
		 
		 
	}
	
	
public static void main(String[] args) {
		
		RefectionDemo one =  new RefectionDemo();
		
		T T = new T();
//		Class cs  = (Class)T;
		System.out.println();
	}
	
	
	
}


class  T<String>{

	private int index;

	public T(){
		 //类对象
		 System.out.println("父类的getClass() == " + this.getClass());  
	     //父类类型，最近的父类
	}
	
}