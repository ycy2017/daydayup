package com.mas.dtc.scripts.outsource.task;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;

import com.mas.dtc.scripts.outsource.fileprocess.BaseFileProcess;

/**
 * 
 * 
 * 写线程
 * 
 * @author Mathsys
 *
 */
public class WriteTask implements Runnable {

	/**
	 * 日志
	 */
	protected static final Logger LOG = Logger.getLogger(WriteTask.class);
	
	BaseFileProcess bfp;
	Future<FutureData> future;
	
	public WriteTask(BaseFileProcess bfp,Future<FutureData> future){
		this.bfp = bfp;
		this.future = future;
	}
	
	@Override
	public void run() {
		//进入循环体,孵化"未来结果"future
		while(true){
			if(this.future.isDone()){
				try {
					//"未来结果"的get方法是阻塞线程
					FutureData  data = future.get();
					LOG.info(" \"future\" is Done " + data.toString());
					bfp.doWriteToFile(data.getIds(), data.getData(),data.getOther());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
				//获取结果后,退出循环
				break;
			}else {
				
				try {
					LOG.warn(" \"future\" is working ,wait 1 seconds ... ");
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		
		}
		
		
		
	}

	
	
}
