package trydemo.thread.syn.bean;

public class AccountBean implements Runnable{

	/**
	 * 是否还有豆子豆子
	 */
	public static int beanNum = 0;
	
	
	@Override
	public void run() {
	
		//beanNum为false时,线程一直拿豆子
		while(0 == beanNum){
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

		th1.start();
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		Thread th2 = new Thread(){
			public void run(){
				AccountBean.beanNum=AccountBean.beanNum+1;
				System.out.println(AccountBean.beanNum);
			}
		};
		th2.start();
		
		
	}

}
