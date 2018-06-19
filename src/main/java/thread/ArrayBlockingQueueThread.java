package thread;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 有界队列
 * 
 * @author yincy
 *
 */
public class ArrayBlockingQueueThread {

	public static void main(String[] args) {

		BlockingQueue<Runnable> queue2 = new ArrayBlockingQueue<Runnable>(10);
		/**
		 * 一个线程的线程池子
		 * 队列两个
		 */
		ExecutorService es = new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS,  queue2);

		for(int i=0;i<=10;i++) {
			Task task = new Task( i+"号" );
			System.out.println("已经生产 " + i +"号 任务");
//			es.submit(task);
			es.execute(task);
//			Future<String> future= es.submit(task);
		}
		
		
	}

}

class Task implements Runnable {

	String taskName ;
	
	public Task(String taskName) {
		this.taskName = taskName;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		System.out.println(taskName + " do work ...");
		try {
			//异常
			System.out.println(1/0);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(taskName + " done");
		
	}

	/*@Override
	public String call() throws Exception {
		System.out.println(taskName + "Callable do work ...");
		try {
			//异常
			System.out.println(1/0);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(taskName + "Callable done");

		return null;
	}*/
	
}

