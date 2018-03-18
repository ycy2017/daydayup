package trydemo.invoke;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvokeDemo {

	
	public static void main(String[] args) {
	
		A a =new A();
		try {
			
			Method method = a.getClass().getMethod("doWork", null);
			method.invoke(a,null );
			
			//2
			Class invokeDemoClass = Class.forName("trydemo.invoke.A");
			A id= (A) invokeDemoClass.newInstance();
			id.doWork();
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		
	}
	
	
}

class A {
	
	public void doWork(){
		System.out.println("doWork");
	}
	
}