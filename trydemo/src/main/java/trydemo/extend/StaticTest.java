package trydemo.extend;

public class StaticTest {

	public static void main(String[] args) {
//		staticFunction();
	}

	static {
		System.out.println("1");
	}
	
	static StaticTest st = new StaticTest();
	
	{
		System.out.println("2");
	}

	StaticTest() {
		System.out.println("3");
		System.out.println("a=" + a + ",b=" + b);
	}

	public static void staticFunction() {
		System.out.println("4");
	}

	int a ;
	static int b = 112;

}
