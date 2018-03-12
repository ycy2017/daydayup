package trydemo.extend;

public class A {

	public A(){
		System.out.println("A");
	}
	
	 public static void main(String[] args) {
			A A =new C();
//			A b =new B();
		} 
	
}

/**
 * (1)抽象类可以有构造方法,但是不能被实例化
 * (2)抽象类可以继承普通的类
 * @author Mathsys
 *
 */
 abstract class B extends A{
	public B(){
		System.out.println("B");
	}
 }
 
 
 class C extends B{
	
 }
 
 