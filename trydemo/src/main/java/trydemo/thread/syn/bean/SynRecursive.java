package trydemo.thread.syn.bean;


/**
 * 线程和递归调用
 * @author Mathsys
 *
 */
public class SynRecursive {

	int count=1;
	
	public synchronized void draw(){
		System.out.println(Thread.currentThread().getName()+"draw .... "+(count++));
		draw();
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Thread t = new Thread(){
			public void run(){
				new SynRecursive().draw();
			}
		};

		t.start();
	}

}
