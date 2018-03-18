package trydemo.thread;

public class SynExtendsDemo {

	public static void main(String[] args) {
		
		
		final A a = new B();
		
		Thread t1 =new Thread(){
			public void run(){
				a.doWork();
			}
		};
		Thread t2 =new Thread(){
			public void run(){
				a.doWork();
			}
		};
		
		
		t1.start();
		t2.start();
		
	}
	
}


class A  {

	public synchronized void doWork(){
		System.out.println(".......");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

class B extends A{
	
	@Override
	public void doWork(){
		System.out.println(".......");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}