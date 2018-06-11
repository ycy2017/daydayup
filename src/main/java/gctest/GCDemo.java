package gctest;

public class GCDemo {

	public static GCDemo gc;
	
	@Override
	protected void finalize() throws Throwable {
		// TODO Auto-generated method stub
		super.finalize();
		
		System.out.println("finalize ...");
		gc = this;
		
	}
	
	public static void main(String[] args) {
		
	    gc = new GCDemo();
		gc = null;
		//gc
		System.gc();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(gc);
		
		gc.gc = null;
		System.gc();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(gc.gc);
		
	}
	
}
