package trydemo.thread;

public class NotifyAndWaitDemo implements Runnable{
	Object ob ;
	public NotifyAndWaitDemo(Object ob){
		this.ob = ob;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		synchronized(this.ob){
			System.out.println(Thread.currentThread().getName()+"wait......");
			try {
//				this.ob.wait();
				//设置允许等待的最大时间
				this.ob.wait(10000);
				System.out.println(Thread.currentThread().getName()+"do ");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}

//	private class Weak{
//		
//		
//		
//	}
	
	public static void main(String[] args) {
		
		Object ob =new Object();
		NotifyAndWaitDemo NotifyAndWaitDemo = new NotifyAndWaitDemo(ob);
		
		for(int i=0;i<10;i++){
			Thread th = new Thread(NotifyAndWaitDemo);
			th.start();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		synchronized(ob){
		System.out.println("唤醒所有的线程");
			ob.notifyAll();
		}
		
		
		
	}
	
	
}
