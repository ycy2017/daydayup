package com.callbatch.outsource.source;

import java.util.Map;


/**
 * 数据来源基层类
 * @author Mathsys
 *
 */
public interface  IDataSource {

	
	/**
	 * 检验参数
	 * 
	 * @param type 类型
	 * @return boolean
	 */
	public  boolean checkCommand(String type);
	
	/**
	 * 
	 * 调用接口
	 * 
	 * @param ids 调用接口的入参
	 * @param type origin:调用原始层数据,mapping:调用dlc转化后的数据
	 * @return 接口返回的数据
	 */
	public  Map<String, Object> getDataFromDataSource(Map<String, Object> ids, String type);

	/**
	 * 根据sourcemeta位
	 * @param sourceMetaStr 接口标识
	 * @return boolean
	 */
	public  boolean setDataSourceField(String sourceMetaStr);
	
}
