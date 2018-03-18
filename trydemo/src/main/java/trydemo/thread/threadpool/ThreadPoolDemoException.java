package trydemo.thread.threadpool;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolDemoException {

	public static void main(String[] args) {
		ThreadPoolExecutor threadpool = new ThreadPoolExecutor(11, 100, 1, TimeUnit.MINUTES, //   
		        new ArrayBlockingQueue<Runnable>(10000)   
		       ) {   
		   
		    protected void afterExecute(Runnable r, Throwable t) {   
		        super.afterExecute(r, t);   
		        if(t!=null&& r!=null){
		        	A a = (A)r;
		        	System.out.println(t.getMessage());
		        	System.out.println(a.getA() +" like");
		        }
		    }   
		};   
		
		threadpool.execute(new A(0));
		try {
			Thread.sleep(10000);
			threadpool.execute(new A(1));
		} catch (InterruptedException e) {
			System.out.println("e");
		}catch(RuntimeException e){
            System.out.println("Exception has been handled!");
        }
		
		Future<Object> future = threadpool.submit(new B(1));
//		try {
//			future.get();
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
	
}


class A implements Runnable{
	private int a ;
	public A(int a){
		this.a = a;
	}
	public int getA(){
		return this.a;
	}
	@Override
	public void run(){
		int i = 1/a;
//		int i  = Integer.parseInt("TT");
		System.out.println("ok......");
	}
}

class B implements Callable<Object>{
	private int a ;
	public B(int a){
		this.a = a;
	}
	@Override
	public Object call(){
//		int i = 1/a;
		int i  = Integer.parseInt("TT");
		System.out.println("ok......");
		return i;
	}
}

