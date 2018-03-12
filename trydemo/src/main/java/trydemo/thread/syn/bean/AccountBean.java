package trydemo.thread.syn.bean;

public class AccountBean implements Runnable{

	/**
	 * 是否还有豆子豆子
	 */
	public static boolean beanNum = false;
	
	
	@Override
	public void run() {
	
		//每个线程拿一个豆子
		while(!beanNum){
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
		
		Runnable table = new AccountBean();
		Thread th1 = new Thread(table);
//		Thread th2 = new Thread(table);
		th1.start();
//		th2.start();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		AccountBean.beanNum=true;
		System.out.println(beanNum);
	}

}
