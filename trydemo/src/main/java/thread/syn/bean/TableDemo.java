package thread.syn.bean;

public class TableDemo implements Runnable {

	/**
	 * 20豆子
	 */
	public static int beanNum;
	
	public TableDemo(int beanNum){
		this.beanNum=beanNum;
	}
	
	@Override
	public void run() {
	
		//每个线程拿一个豆子
		while(this.beanNum!=0){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//			this.beanNum--;
			System.out.println(Thread.currentThread().getName()+"拿了一个豆子,桌上还有");
		}
	}
	
	public static void main(String[] args) {
		
		Runnable table = new TableDemo(20);
		Thread th1 = new Thread(table);
//		Thread th2 = new Thread(table);
		th1.start();
//		th2.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TableDemo.beanNum=0;
		
	}
	
}
