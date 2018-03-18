package trydemo.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemo {

	public static void main(String[] args) {
		
		ExecutorService cachepool = Executors.newCachedThreadPool();
		
		ExecutorService pool = Executors.newFixedThreadPool(1);
		
		Executors.newSingleThreadExecutor();
		
		ScheduledExecutorService  schpool = Executors.newScheduledThreadPool(1);
		
//		schpool.execute(new Worker());
		Worker  w = new Worker();
		schpool.scheduleWithFixedDelay(w, 3,3, TimeUnit.SECONDS);
		
	}
	
}


class Worker implements Runnable{

	@Override
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(Thread.currentThread().getName() + "do worke");
	}
	
}