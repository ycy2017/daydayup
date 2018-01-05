package thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Map<String, String> map = new HashMap<String, String>();
		int cyclic = 5000;
		//栅栏让所有的线程相互等待,直到大家都等到了一起了,才继续执行
		CyclicBarrier cb = new CyclicBarrier(cyclic);
		//闭锁
		CountDownLatch cd  = new CountDownLatch(cyclic);
		for (int i = 0; i < cyclic; i++) {
			MapWork wor = new MapWork(map, cb , cd);
			new Thread(wor).start();
		}
		
		//阻塞,直到闭锁被打开
		try {
			cd.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//等待所有的线程执行完之后,才开始打印
		System.out.println(map);
		
	}

}

class MapWork implements Runnable {

	private Map<String, String> map;
	private CyclicBarrier cb;
	private CountDownLatch cd;
	
	public MapWork(Map<String, String> map, CyclicBarrier cb,CountDownLatch cd) {
		this.map = map;
		this.cb = cb;
		this.cd = cd;
	}

	@Override
	public void run() {

		// 准备
		try {
			// 线程的栅栏
			System.out.println(Thread.currentThread().getName() + "prepare ... ");
			cb.await();
			this.map.put("Key", "Value");
			System.out.println(Thread.currentThread().getName() + " finished ");
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (BrokenBarrierException e) {
			e.printStackTrace();
		}finally{
			//闭锁减1,当闭锁为0时,闭锁被打开了
			cd.countDown();
		}

		
	}

}
