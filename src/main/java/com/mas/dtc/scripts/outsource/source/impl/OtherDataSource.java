package com.mas.dtc.scripts.outsource.source.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mas.dtc.scripts.outsource.CommandField;
import com.mas.dtc.scripts.outsource.source.IDataSource;
import com.mas.dtc.util.baidu.BatchMatchBaiDuUtil;

/**
 * 百度接口调用
 * 主要是通用工具类调用外部接口
 * @author Mathsys
 *
 */
public class OtherDataSource implements IDataSource {

	/**
	 * 日志
	 */
	protected static final Logger LOG = Logger.getLogger(OtherDataSource.class);
	
	/**
	 * 返回一个实例
	 * @return 实例
	 */
	public static IDataSource getInstance(){
		return new OtherDataSource();
	}
	
	@Override
	public boolean checkCommand(String type) {
		boolean flag = true;
		// 检验sourceMeta参数
		if (!CommandField.BAIDUADRESS_TYPE.equals(type) && !CommandField.BAIDULOCATION_TYPE.equals(type)) {
			LOG.error("\"type\" error,should contains \"adress\" or \"location\"  ");
			flag = false;
		}
		return flag;
	}

	@Override
	public Map<String, Object> getDataFromDataSource(Map<String, Object> ids, String type) {
		// 用来存放结果的集合
		Map<String, Object> srcMap = new HashMap<String, Object>();
		Map<String, Object> results = null;
		if(CommandField.BAIDUADRESS_TYPE.equals(type)){
			//百度地图经纬度转地址
			 results = BatchMatchBaiDuUtil.transLocationToAdressFetch(ids);
		}else if(CommandField.BAIDULOCATION_TYPE.equals(type)){
			//百度地图地址转经纬度
			 results = BatchMatchBaiDuUtil.transAdressToLocationFetch(ids);
			
		}else{
			//todo
			//可以添加其他数据源
		}
		if (results != null && results.size()>0 ) {
			srcMap.putAll(results);
		}
		return srcMap;
	}

	@Override
	public boolean setDataSourceField(String sourceMetaStr) {
		//
		if("baidu".equalsIgnoreCase(sourceMetaStr)){
			return true;
		}
		return false;
	}

}
