package com;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class BlockQueueDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SynchronousQueue<String> bq =  new SynchronousQueue<String>();
		
		
		try {
			bq.put("like");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(bq.size());
		
		
	}
	
}





