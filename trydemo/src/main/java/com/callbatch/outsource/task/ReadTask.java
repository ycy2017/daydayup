package com.callbatch.outsource.task;

import java.util.Map;
import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.callbatch.outsource.source.IDataSource;




/**
 * 任务载体,继承Callable
 * @author Mathsys
 *
 */
public class ReadTask implements Callable<FutureData>{

	/**
	 * 日志
	 */
	protected static final Logger LOG = Logger.getLogger(ReadTask.class);
	
	/**
	 * 数据来源
	 */
	public IDataSource ds ;
	/**
	 * 参数
	 */
	public Map<String,Object> ids;
	/**
	 * 接口类型
	 */
	public String type;
	/**
	 * 接口类型
	 */
	public Object[] other;
	
	/**
	 * 
	 * @param ds 数据来源
	 * @param ids
	 * @param type
	 */
	public ReadTask(IDataSource ds,Map<String,Object> ids,String type){
		this.ds = ds;
		this.ids = ids;
		this.type = type;
	}
	
	/**
	 * 
	 * @param dataSource
	 * @param ids2
	 * @param type2
	 * @param other
	 */
	public ReadTask(IDataSource dataSource, Map<String, Object> ids2, String type2, Object[] other) {
		this.ds = dataSource;
		this.ids = ids2;
		this.type = type2;
		this.other = other;
	}

	@Override
	public FutureData call() throws Exception {
		
		Map<String, Object> dataFromOutSource = this.ds.getDataFromDataSource(this.ids, type);
		FutureData data = new FutureData(this.ids,dataFromOutSource,this.other);
		LOG.info(type + ": transform " + data.toString());
				
		return data;
	}

}

