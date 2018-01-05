package com.callbatch.outsource.task;

import java.util.Arrays;
import java.util.Map;

/**
 * 返回结果封装类
 * @author Mathsys
 *
 */
public class FutureData {

	private  Map<String, Object> data;
	private Map<String, Object> ids;
	private Object[] other;
	public FutureData(Map<String, Object> ids,Map<String, Object> data) {
		this.ids = ids;
		this.data = data;
	}

	
	public FutureData(Map<String, Object> ids2, Map<String, Object> dataFromOutSource, Object[] other) {
		this.ids = ids2;
		this.data = dataFromOutSource;
		this.other = other;
	}

	public void setData(Map<String, Object> data){
		this.data = data;
	}
	
	public Map<String, Object> getData(){
		return this.data;
	}

	public Map<String, Object> getIds() {
		return ids;
	}

	public void setIds(Map<String, Object> ids) {
		this.ids = ids;
	}


	public Object[] getOther() {
		return other;
	}

	public void setOther(Object[] other) {
		this.other = other;
	}


	@Override
	public String toString() {
		return "FutureData [data=" + data + ", ids=" + ids + ", other=" + Arrays.toString(other) + "]";
	}

}
