package syn;

public class DeadSyn {

	public static void main(String[] args) {
		Object s1 = new Object();
		Object s2 = new Object();
		
		//实现一个死锁
		
		Thread a =new A(s1,s2);
		Thread b =new B(s1,s2);
		a.start();
		b.start();
		
	}
	
	
	
}


class A extends Thread{
	Object s1 ;
	Object s2 ;
	
	public A(Object s1,Object s2){
		this.s1 = s1;
		this.s2 = s2;
	}
	
	public void run(){
		synchronized(s1){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(s2){
				 System.out.println(Thread.currentThread().getName());
			}
		}
	}
	
	
}

class B extends Thread{
	
	
	Object s1 ;
	Object s2 ;
	
	public B(Object s1,Object s2){
		this.s1 = s1;
		this.s2 = s2;
	}
	
	public void run(){
		synchronized(s2){
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(s1){
				 System.out.println(Thread.currentThread().getName());
			}
		}
	}
	
}