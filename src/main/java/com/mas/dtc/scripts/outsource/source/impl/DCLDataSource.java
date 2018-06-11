/*package com.mas.dtc.scripts.outsource.source.impl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mas.dtc.input.DatasourceGateway;
import com.mas.dtc.input.service.base.dataSource.DataFetch;
import com.mas.dtc.log.bean.LogSourceField;
import com.mas.dtc.scripts.outsource.CommandField;
import com.mas.dtc.scripts.outsource.source.IDataSource;
import com.mas.dtc.util.baidu.BatchMatchBaiDuUtil;

*//**
 * 外部数据源接口调用
 * 主要是通用dataFetch调用DLC中已经接入的外部数据源接口
 * @author Mathsys
 *
 *//*
public class DCLDataSource implements IDataSource {

	*//**
	 * 数据源
	 *//*
	protected DataFetch dataFetch;

	*//**
	 * 日志
	 *//*
	protected static final Logger LOG = Logger.getLogger(DCLDataSource.class);

	*//**
	 * 实例
	 * @return 实例
	 *//*
	public static IDataSource getInstance(){
		return new DCLDataSource();
	}
	
	@Override
	public boolean checkCommand(String type) {
		boolean flag = true;
		// 检验sourceMeta参数
		if (!CommandField.MAPPING_TYPE.equals(type) && !CommandField.ORIGIN_TYPE.equals(type)) {
			LOG.error("\"type\" error,should contains \"origin\" or \"mapping\"  ");
			flag = false;
		}
		return flag;
	}

	@Override
	public boolean setDataSourceField(String sourceMetaStr) {
		Map<String, Object> sourceMeta = getsourceMeta(sourceMetaStr);
		DataFetch dataFetch = getDataFetch(sourceMeta);
		if(dataFetch!=null){
			this.dataFetch = dataFetch;
			return true;
		}
		return false;
		
	}
	
	
	@Override
	public Map<String, Object> getDataFromDataSource(Map<String, Object> ids, String type) {
		// 日志
		Map<String, Object> logInfo = new HashMap<String, Object>();
		// 用来存放结果的集合
		Map<String, Object> totalMap = new HashMap<String, Object>();
		Map<String, Object> srcMap = new HashMap<String, Object>();

		if (CommandField.MAPPING_TYPE.equals(type)) {
			// 转化成源始层
			Map<String, Object> results = this.dataFetch.getData(ids, logInfo, true);
			if (results != null && results.get("tags") instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> tags = (Map<String, Object>) results.get("tags");
				srcMap.putAll(tags);
			}
		} else if (CommandField.ORIGIN_TYPE.equals(type)) {
			// 转化成源始层
			Map<String, Object> results  = this.dataFetch.getOriginData(ids, logInfo);
			if (results != null ) {
				srcMap.putAll(results);
			}
		}

		if (srcMap != null && srcMap.size() > 0) {
			totalMap.putAll(srcMap);
		}

		Object judgeResult = logInfo.get(LogSourceField.judgeResult);
		LOG.info("judgeResult :" + judgeResult);
		if (judgeResult != null) {
			// 添加查询状态
			totalMap.put(LogSourceField.judgeResult, judgeResult);
		}

		if (srcMap != null && srcMap.size() > 0) {
			// 地址和经纬度互转(只为TD的白天夜晚常活动区域服务)
			Map<String, Object> sourceMeta = this.dataFetch.getSourceMeta();
			Object category = sourceMeta.get("category");
			Map<String, Object> locationInfo = BatchMatchBaiDuUtil.getOtherServer(srcMap, category);
			if (locationInfo != null && locationInfo.size() > 0) {
				totalMap.putAll(locationInfo);
			}
		}

		return totalMap;
	}

	
	*//**
	 * 获取接口唯一标识
	 * @param sourceMetaStr 接口接口唯一标识
	 * @return 接口唯一标识
	 *//*
	public Map<String, Object> getsourceMeta(String sourceMetaStr) {
		String[] sourceMetaArr = sourceMetaStr.split("_");
		Map<String, Object> sourceMeta = new LinkedHashMap<String, Object>();
		String[] sourceMetaKeyArr = { "source", "type", "category", "version" };
		for (int i = 0; i < sourceMetaArr.length; i++) {
			String sourceMetaKey = sourceMetaKeyArr[i];
			String sourceMetaValue = sourceMetaArr[i];
			sourceMeta.put(sourceMetaKey, sourceMetaValue);
		}
		return sourceMeta;
	}
	
	public DataFetch getDataFetch(Map<String, Object> sourceMeta) {
		@SuppressWarnings("resource")
		ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
		DatasourceGateway datasourceGateway = (DatasourceGateway) ctx.getBean("datasourceGateway");
		if (datasourceGateway != null) {
			DataFetch dataFetch = datasourceGateway.findDataSource(sourceMeta);
			return dataFetch;
		}
		return null;
	}
	
	
}
*/