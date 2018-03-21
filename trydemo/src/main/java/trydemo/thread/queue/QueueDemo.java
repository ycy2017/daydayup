package trydemo.thread.queue;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class QueueDemo {
	public static void main(String[] args) {

		List<String> list = new LinkedList<String>();

		list.add("a");
		list.add("b");
		list.add("c");

		Queue<String> queue = new LinkedList<String>();
		queue.add("a");
		queue.add("b");
		queue.add("c");
		queue.peek();
		//拿,并不移除
		System.out.println(queue.peek());
		//拿,并且移除
		System.out.println(queue.poll());
		System.out.println(queue);
		
		//线程安全的map
		Map<String,String> map = new ConcurrentHashMap<String,String>();
		
		//有界的队列,初始化时,必需声明队列大小
		Queue<String> queue2 = new ArrayBlockingQueue<String>(2);
		queue2.add("1");
		queue2.add("2");
		queue2.add("3");
		
		//无界的队列
		Queue<String> queue3 = new LinkedBlockingQueue<String>();
		
		
		
	}
}
