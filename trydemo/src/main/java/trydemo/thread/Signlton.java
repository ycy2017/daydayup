package trydemo.thread;

import java.util.Calendar;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import trydemo.thread.OrderSort.ThreadA;

public class Signlton {

	private Signlton(){
		
	}
	
	private static Signlton s= null;
	
	
	public static Signlton getInstance (){
		
		if(s==null){
			//高性能双重校验
			
			synchronized(Signlton.class){
				if(s==null){
					 s = new Signlton();
				}
		
			}
			
		}
		return s;
	}
	
	
	public static void main(String[] args) {
		Double a= null;
		Integer b=null;
		
		Object aO = a;
		Object bO = b;
		System.out.println(aO==bO);
		
		Calendar.getInstance();
		
		final CyclicBarrier cb = new CyclicBarrier(1000);
		 for(int i=0;i<1000;i++){
			 Thread threada = new Thread(){
				  public void run(){
					  try {
						cb.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
					  System.out.println(Signlton.getInstance().hashCode());
				  }
			 };
			 threada.start();
		 }
		
	}
	
}
