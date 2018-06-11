package com.mas.dtc.util.baidu;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import com.mas.dtc.util.baidu.LocationUtils;
/**
 * 地址和经纬度互转(只为TD的白天夜晚常活动区域服务)
 * 
 * 根据输入的map集合实现地址和经纬度互转
 * 
 * (1)经纬度转化地址,得到地址和商圈两类标签
 * TD_LATLNG_DAY_ADRESS1,TD_LATLNG_DAY_BUSINESS1
 * (2)地址转化经纬度,得到纬度和经度标签两类标签
 * LAT1,LNG1
 * 
 * @author Mathartsys
 *
 */
public class BatchMatchBaiDuUtil {

	/**
	 * 日志
	 */
	protected static Logger LOG = Logger.getLogger(BatchMatchBaiDuUtil.class);
	
	/**
	 * 入口
	 * @param result  原始数据
	 * @param category 接口种类
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getOtherServer(Map<String, Object> result,Object category){
		
		if(category!=null){
			Map<String, Object> matchResult = null;
			if("day".equals(category)){
				matchResult = getLoctionOrAdress(result,"adress");
			}else if ("night".equals(category)){
				matchResult = getLoctionOrAdress(result,"location");
			}
			if(matchResult != null && matchResult.size() >0){
				LOG.info("locationInfo " + matchResult);
				return matchResult;
			}
		}
		
		return null;
	}
	
	
	/**
	 * 主要入口
	 * @param result 
	 * @param type 转换类型
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> getLoctionOrAdress(Map<String, Object> result,String type){

		Map<String, Object> matchResults = new HashMap<String, Object>();
		Map<String, Object> matchResult = new HashMap<String, Object>();
		if("location".equals(type)){
			matchResult = transAdressToLocationFetch(result);
		}else if ("adress".equals(type)){
			matchResult = transLocationToAdressFetch(result);
		}
		if(matchResult != null && matchResult.size() >0){
			matchResults.putAll(matchResult);
		}
		return matchResults;
	}
	

	/**
	 * 地址转经纬度
	 * @param result 入参
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> transAdressToLocationFetch(Map<String, Object> result){
		Map<String, Object> matchResult = new HashMap<String, Object>();
		for(Entry<String,Object> entry:result.entrySet()){
			String key = entry.getKey();
			if(entry.getValue()!=null){
				Map<String, Object> tag = transAdressToLocation(entry.getValue(),key);
				if(tag != null && tag.size() > 0){
					matchResult.putAll(tag);
				}
			}
		}
		return matchResult;
	}
	
	/**
	 * 经纬度转地址
	 * @param result 
	 * @return Map<String, Object>
	 */
	public static Map<String, Object> transLocationToAdressFetch(Map<String, Object> result){
		Map<String, Object> matchResult = new HashMap<String, Object>();
		
		for(Entry<String,Object> entry:result.entrySet()){
			String key = entry.getKey();
			if(entry.getValue()!=null){
				Map<String, Object> tag = transLocationToAdress(entry.getValue(),key);
				if(tag != null && tag.size() > 0){
					matchResult.putAll(tag);
				}
			}
		}
		return matchResult;
		
	}
	
	/**
	 * 
	 * 经纬度转换成地址
	 * 
	 * @param locationObj 经纬度
	 * @param index 参数名称
	 * @return {"TD_LATLNG_DAY_ADRESS":"河北省秦皇岛市抚宁县G102(京哈线)","TD_LATLNG_DAY_BUSINESS":"松桂园,伍家岭,芙蓉广场"} 
	 * 
	 */
	private static Map<String, Object> transLocationToAdress(Object locationObj, String index) {
		Map<String, Object> transResult = new HashMap<String, Object>();
		String adress = String.valueOf(locationObj);
		String [] latLngArr = adress.split(",");
		if(latLngArr.length != 2 ){
			return null;
		}
		Map<String, String> adressMap =  LocationUtils.getLocation(latLngArr[0], latLngArr[1]);
		if (adressMap != null) {
			String formattedAddress = adressMap.get("formatted_address");
			String business = adressMap.get("business");
			if (formattedAddress != null) {
				transResult.put( index + "_ADRESS" , formattedAddress);
			}
			if (business != null) {
				transResult.put(index + "_BUSINESS" , business);
			}
			return transResult;
		}
		return null;
	}
	
	/**
	 * 
	 * 地址转换成经纬度
	 * 
	 * @param adressObj 地址
	 * @param index 参数名称
	 * @return  {"TD_LATLNG_DAY_ADRESS1_LAT":"34.754","TD_LATLNG_DAY_ADRESS1_LNG":"113.776"} 
	 */
	private static Map<String, Object> transAdressToLocation(Object adressObj, String index) {
		Map<String, Object> transResult = new HashMap<String, Object>();
		Map<String, Object> locationMap = null;
		String adress = String.valueOf(adressObj);
		locationMap = LocationUtils.getLocation(adress);
		if (locationMap != null) {
			transResult.put(index+"_LAT" , locationMap.get("lat"));
			transResult.put(index+"_LNG" , locationMap.get("lng"));
			return transResult;
		}
		return null;
	}
	
}
