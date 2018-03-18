package trydemo.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class invokeDemo {

	
	public static void main(String[] args) {
	
		A a =new A();
		try {
			Method method = a.getClass().getMethod("doWork", null);
			method.invoke(a,null );
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}
	
	
}

class A {
	
	public void doWork(){
		System.out.println("doWork");
	}
	
}