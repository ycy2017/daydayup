package com.mas.dtc.util.baidu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * 经纬度-地理位置信息互转接口
 *
 * @author arrietty
 *
 */
public class LocationUtils {

	/**
	 * 日志
	 */
	public static final Logger LOG = Logger.getLogger(LocationUtils.class);

	/**
	 * AK,需要从百度接口申请
	 */
//	public final static String KEY = "FUVOHdju6IfRzVGGvxUUGBcTNmXfHfad";
	public final static String KEY = "KNuNdXHrZQBZmGpFQGmlp7QwHYiMnGoR";
	
	/**
	 * 访问地址
	 */
	public final static String URL = "http://api.map.baidu.com/geocoder/v2/";
	
	/**
	 * 坐标体系
	 */
	public static final String CODETYPR = "gcj02ll";
	
	/**
	 * @param lat 纬度
	 * @param lng 经度
	 * @return Map<String, String>
	 */
	public static Map<String, String> getLocation(String lat, String lng) {
		String url = URL + "?ak=" + KEY + "&location=" + lat + "," + lng + "&coordtype=" + CODETYPR
				+ "&output=json&pois=1";
		LOG.info("getLocation url " + url);
		Map<String, Object> apiResult = getAppResult(url);
		Map<String, String> returnResult = new LinkedHashMap<>();
		if (apiResult != null && apiResult.get("result") instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) apiResult.get("result");
			// 附件最近的三个商圈
			Object business = result.get("business");
			//结构化地址
			Object formattedAddress = result.get("formatted_address");
			if (business instanceof String && !"".equals(business)) {
				returnResult.put("business", (String) business);
			}
			if (formattedAddress instanceof String && !"".equals(formattedAddress)) {
				returnResult.put("formatted_address", (String) formattedAddress);
			}
			if (result.get("addressComponent") instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> resultData = (Map<String, Object>) result.get("addressComponent");
				Object province = resultData.get("province");
				Object city = resultData.get("city");
				// 区
				Object district = resultData.get("district");
				// 乡镇
				Object town = resultData.get("town");
				// 街道
				Object street = resultData.get("street");
				
				if (province instanceof String && !"".equals(province)) {
					returnResult.put("province", (String) province);
				}
				if (city instanceof String && !"".equals(city)) {
					returnResult.put("city", (String) city);
				}
				if (district instanceof String && !"".equals(district)) {
					returnResult.put("district", (String) district);
				}
				if (business instanceof String && !"".equals(town)) {
					returnResult.put("town", (String) town);
				}
				if (business instanceof String && !"".equals(street)) {
					returnResult.put("street", (String) street);
				}
			}

			if (returnResult.size() > 0) {
				return returnResult;
			}
		}
		return null;
	}
	
	/**
	 * 根据地址,返回经纬度
	 * @param address 地址
	 * @return {"LAT1":"34.754","LNG1":"113.776"} 
	 */
	public static Map<String, Object> getLocation(String address) {
		//校验address参数
		if(address == null || "".equals(address.trim()) || "null".equals(address.trim()) ){
			return null;
		}
		String url = URL + "?ak=" + KEY + "&address=" + address + "&coordtype=" + CODETYPR + "&output=json";
//		logger.info("getLocation url " + url);
		Map<String, Object> apiResult = getAppResult(url);
		Map<String, Object> returnResult = new LinkedHashMap<>();
		if (apiResult != null && apiResult.get("result") instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> result = (Map<String, Object>) apiResult.get("result");
			Object locationObj = result.get("location");
			if (locationObj instanceof Map) {
				@SuppressWarnings("unchecked")
				Map<String, Object> locationMap = (Map<String, Object>) locationObj;
				returnResult.putAll(locationMap);
			}
			if (returnResult.size() > 0) {
				return returnResult;
			}
		}
		return null;
	}

	 /**
	  * 通过http调用百度接口数据
	  * @param url 访问地址
	  * @return Map<String, Object>
	  */
	public static Map<String, Object> getAppResult(String url) {
		URL myURL = null;
		URLConnection httpsConn = null;
		Map<String, Object> resultData = null;
		try {
			myURL = new URL(url);
		} catch (MalformedURLException e) {
			LOG.error("MalformedURLException " + e.getMessage());
		}
		InputStreamReader insr = null;
		BufferedReader br = null;
		try {
			httpsConn = (URLConnection) myURL.openConnection();
			if (httpsConn != null) {
				httpsConn.setConnectTimeout(500);
				httpsConn.setReadTimeout(500);
				insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
				br = new BufferedReader(insr);
				String data = null;
				StringBuffer bs = new StringBuffer();
				while ((data = br.readLine()) != null) {
					bs.append(data).append(System.getProperty("line.separator"));
				}
				ObjectMapper objectMapper = new ObjectMapper();
				resultData = objectMapper.readValue(bs.toString(), Map.class);
//				LOG.info("resultData " + JsonTool.prettyJson(resultData));
			}
		} catch (IOException e) {
			LOG.error("IOException " + e.getMessage());
			return null;
		} finally {
			try {
				if (insr != null) {
					insr.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
		}
		return resultData;
		
	}
	
	public static void main(String[] args) {
		Date d1=new Date();

		System.out.println("location=>adress"+LocationUtils.getLocation("29.881","121.533")); //30.158,120.277 //29.881,121.533
		System.out.println(LocationUtils.getLocation("广东省深圳市福田区天然居"));

		Date d2=new Date();
		System.out.println(d2.getTime()-d1.getTime());
	}
}