package trydemo.thread.syn.volatie;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * volatile目前不能解决的问题
 * 可以使用AtomicInteger解决
 * @author Mathsys
 *
 */
public class Demo {

	public static void main(String[] args) {
		
		CountDownLatch cd = new CountDownLatch(9000);
		
		for(int i=1;i<=9000;i++){
			new Thread(new A(cd)).start();
			System.out.println("i="+i);
		}
		try {
			cd.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(A.num);
		
	}

}

class A implements Runnable{

	//1、volatile 不能使"变量自增操作"的线程安全
//    volatile static  int num = 0;
	
	//2、使用原子,既可以解决"变量自增操作"
	static  AtomicInteger  num = new AtomicInteger(0);
    
	static CountDownLatch cd;
	public A(CountDownLatch cd){
		this.cd = cd;
	}
	
	@Override
	public void run() {
		//一条命令, 但是其实他是复合操作
		//int a = num;
		//int b = a+1;
		//int num = b;
		//当num=b时,其他线程已经对num
	//		num++;
	//		num = num + 1;
		
		//原子性的num++,通过循环CAS方式
		String threadName = Thread.currentThread().getName();
		System.out.println(threadName + ":"+ num.getAndIncrement() );
		cd.countDown();
	}
	
}

class B implements Runnable{

	public B(){
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
}