package trydemo.extend;

public class A {

	public A() {
		System.out.println("A");
	}

	{
		System.out.println("A代码块");
	}
	static {
		System.out.println("A静态块");
	}

	public static void main(String[] args) {
		// A A =new C();
		// B B =new C();
		// A b =new B();
		"el".equals("dad");
	}

}

/**
 * (1)抽象类可以有构造方法,但是不能被实例化 (2)抽象类可以继承普通的类 (3)抽象类可以定义非抽象方法,
 * 
 * @author Mathsys
 *
 */
abstract class B extends A {
	{
		System.out.println("B代码块");
	}
	static {
		System.out.println("B静态块");
	}

	public B() {
		System.out.println("B");
	}
}

class C extends B {

}


interface D{
	
}

interface E extends D{
	
}