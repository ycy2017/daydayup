package trydemo.thread.threadpool;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CallableDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		BlockingQueue<Runnable> bd = new LinkedBlockingQueue<Runnable>() ; 
		
		//使用Executors工厂方法创建一个线程池
//		ExecutorService threadPool = Executors.newFixedThreadPool(10);
		
		//
		ExecutorService threadPool = new ThreadPoolExecutor(5,5, 0L, TimeUnit.MILLISECONDS, bd);
		
		List< Future<Data> > futureList = new ArrayList< Future<Data>>();
		Date start = new Date() ;
		
		/*
		 * 一个任务执行3秒,如果是单个线程总共 耗时30秒,时间如果开启十个线程,时间会小于30秒
		 */
		for(int i = 1;i<=10;i++){
			
			 AccountData task= new AccountData(i);
			 Future<Data>  futre= threadPool.submit(task);
//			 Future<Data>  futre= threadPool.execute(task);
			 futureList.add(futre);
			 
		}
		
		System.out.println("其他任务执行中.......");
		
		for(Future<Data>  futre :futureList){
			try {
				Data data = futre.get();
				System.out.println(data);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		Date end = new Date() ;
		
		//时间会小于30秒
		System.out.println(end.getTime() - start.getTime());
		threadPool.shutdown();
		
	}
	
}

/**
 * 一个任务是3秒钟,使用并发可以节约时间
 * @author Mathsys
 *
 */
class AccountData implements Callable<Data>{
	
	private int taskId ;
	
	public AccountData(int i){
		this.taskId = i;
	}
	
	public Data call() throws Exception {
		
		//模拟线程处理的任务
		Thread.sleep(3000);
		Data  data = new Data();
		data.setRes(this.taskId);
		return data;
	}
	
} 

/**
 * Callable的"未来"结果
 * 
 * @author Mathsys
 *
 */
class Data{
	
	String res = "任务已完成";
	public String getRes(){
		return this.res;
	}
	public void setRes(int i){
		this.res = i+"号"+this.res;
	}
	
	@Override
	public String toString(){
		return this.res.toString();
	}
}

