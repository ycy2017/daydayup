package com.ctrip.search.dictionary.temporary;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import com.ctrip.search.dictionary.temporary.entity.ActivityResultEntity;
import com.ctrip.search.dictionary.temporary.entity.DistrictMappingEntity;
import com.ctrip.search.dictionary.temporary.entity.PoiEntity;
import com.ctrip.search.dictionary.temporary.util.StringContainsUtils;

/**
 * 获取出发城市的旅游景点
 * @author yincy
 *
 */
public class OrderController {



	Map<String, ActivityResultEntity> map;
	Map<Integer, PoiEntity> poiMap;
	
	public static void main(String[] args) {
		
	}
	
	/**
	 * 原始数据
	 * @param map
	 */
	public OrderController(Map<String, ActivityResultEntity> map,Map<Integer, PoiEntity> getPoiMap) {
		this.map = map;
		this.poiMap = getPoiMap;
	}
	
	/**
	 * 查询出发地旅游景点
	 * @param map
	 * @param getPoiMap
	 */
	public Map<Integer, List<ActivityResultEntity>> get() {
		
		Set<Integer> set = new HashSet<Integer>();
		//吧出发城市的districtid拿出来
		for(Entry<String, ActivityResultEntity> entry:map.entrySet()) {
			ActivityResultEntity activityResultEntity = entry.getValue();
			set.add(activityResultEntity.getDistrictId());
			System.out.println(activityResultEntity.getDistrictId());
		}
		System.out.println("提取了:"+set.size());
		
		//出发城市对应的景点
		Map<Integer, List<ActivityResultEntity>> citypoiMap = new HashMap<Integer, List<ActivityResultEntity>>();
		Long startTime = System.currentTimeMillis();
	
		for (Integer districtid : set) {
			for (Entry<Integer, PoiEntity> entry : poiMap.entrySet()) {
				PoiEntity poiEntity = entry.getValue();
				String districtpathid = poiEntity.getDistrictpathid();
				if (StringContainsUtils.contains(districtpathid, String.valueOf(districtid))) {
					// disrictid在poi中查询到
					List<ActivityResultEntity> list = citypoiMap.get(districtid);
					if (list == null) {
						// (Integer poiid,String poiName,String districtidPathid,Float comment_rating)
						List<ActivityResultEntity> newlist = new ArrayList<ActivityResultEntity>();
						newlist.add(new ActivityResultEntity(poiEntity.getPoiid(), poiEntity.getName(),
								poiEntity.getDistrictpathid(), poiEntity.getRating() * poiEntity.getCommentcount()));
						citypoiMap.put(districtid, newlist);
					}else {
						list.add(new ActivityResultEntity(
								poiEntity.getPoiid(), 
								poiEntity.getName(),
								poiEntity.getDistrictpathid(), 
								poiEntity.getRating() * poiEntity.getCommentcount()));
					}
				}
			}
		}
		System.out.println( "find cost :" +( System.currentTimeMillis() - startTime ) );
		
		return citypoiMap ;
		
	}
	
	/**
	 * 查询出发地旅游景点
	 * @param map
	 * @param getPoiMap
	 */
	public Map<Integer, List<Float>> getDistrictRatComment() {
		
		Set<Integer> set = new HashSet<Integer>();
		//吧出发城市的districtid拿出来
		for(Entry<String, ActivityResultEntity> entry:map.entrySet()) {
			ActivityResultEntity activityResultEntity = entry.getValue();
			set.add(activityResultEntity.getDistrictId());
			System.out.println(activityResultEntity.getDistrictId());
		}
		System.out.println("提取了:"+set.size());
		
		//出发城市对应的景点
		Map<Integer, List<Float>> citypoiMap = new HashMap<Integer, List<Float>>();
		Long startTime = System.currentTimeMillis();
	
		for (Integer districtid : set) {
			for (Entry<Integer, PoiEntity> entry : poiMap.entrySet()) {
				PoiEntity poiEntity = entry.getValue();
				String districtpathid = poiEntity.getDistrictpathid();
				if (StringContainsUtils.contains(districtpathid, String.valueOf(districtid))) {
					// disrictid在poi中查询到
					List<Float> list = citypoiMap.get(districtid);
					if (list == null) {
						// (Integer poiid,String poiName,String districtidPathid,Float comment_rating)
						List<Float> newlist = new ArrayList<Float>();
						newlist.add(poiEntity.getRating() * poiEntity.getCommentcount());
						citypoiMap.put(districtid, newlist);
					}else {
						list.add(poiEntity.getRating() * poiEntity.getCommentcount());
					}
				}
			}
		}
		System.out.println( "find cost :" +( System.currentTimeMillis() - startTime ) );
		return citypoiMap ;
		
	}
	
	/**
	 * 查询出发地旅游景点
	 * @param map
	 * @param getPoiMap
	 */
	public Map<Integer, Float> getDistrictRatComment2() {
		
		Set<Integer> set = new HashSet<Integer>();
		//吧出发城市的districtid拿出来
		for(Entry<String, ActivityResultEntity> entry:map.entrySet()) {
			ActivityResultEntity activityResultEntity = entry.getValue();
			set.add(activityResultEntity.getDistrictId());
			System.out.println(activityResultEntity.getDistrictId());
		}
		System.out.println("提取了 (排序很慢的):"+set.size());
		
		//出发城市对应的景点
		Map<Integer, Float> citypoiMap = new HashMap<Integer, Float>();
		Long startTime = System.currentTimeMillis();
	
		for (Integer districtid : set) {
			List<Float> floatList = new ArrayList<Float>();
			for (Entry<Integer, PoiEntity> entry : poiMap.entrySet()) {
				PoiEntity poiEntity = entry.getValue();
				String districtpathid = poiEntity.getDistrictpathid();
				if (StringContainsUtils.contains(districtpathid, String.valueOf(districtid))) {
					// disrictid在poi中查询到
					floatList.add(poiEntity.getRating() * poiEntity.getCommentcount());
				}
			}
			Collections.sort(floatList,new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					// TODO Auto-generated method stub
					float f1 = (float) o1;
					float f2 = (float) o2;
					return (int) (f2 - f1);
				}
			});
			
			Float l = (float) 0; 
			if(floatList.size()>=10) {
				l = floatList.get(9);
			}
			
			citypoiMap.put(districtid, l);
			
		}
		System.out.println( "find cost :" +( System.currentTimeMillis() - startTime ) );
		return citypoiMap ;
		
	}
	
	
	public void outCSV(Map<Integer, List<ActivityResultEntity>>  map,String outFilePath) {
		String [] headersArr = {"poiid","poiname","districtidpathid","comment_rating","districtId"};
		CSVFormat writeCsvFormat = CSVFormat.DEFAULT.withHeader(headersArr);
		PrintWriter fileWriter = null;
		CSVPrinter  csvPrinter = null;
	    try {
	    	//不需要指定utf_8,  使用csv自带的编码格式
			fileWriter = new PrintWriter(outFilePath,"utf-8");
		    csvPrinter = new CSVPrinter(fileWriter, writeCsvFormat);
			for(Entry<Integer, List<ActivityResultEntity>> entry:map.entrySet()){
				Integer districtid = entry.getKey();
				List<ActivityResultEntity> resEntity= entry.getValue();
				for(ActivityResultEntity Result: resEntity) {
					List<Object> values = new ArrayList<Object>();
					
					values.add(Result.getPoiid());
					values.add(Result.getPoiName());
					values.add(Result.getDistrictidPathid());
					values.add(Result.getComment_rating());
					values.add(districtid);
					
					csvPrinter.printRecord(values);
				}
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(csvPrinter!=null)csvPrinter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  
	}
	
	
	public void outCSV2(Map<Integer, List<Float>>  map,String outFilePath) {
		String [] headersArr = {"comment_rating","districtId"};
		CSVFormat writeCsvFormat = CSVFormat.DEFAULT.withHeader(headersArr);
		PrintWriter fileWriter = null;
		CSVPrinter  csvPrinter = null;
	    try {
	    	//不需要指定utf_8,  使用csv自带的编码格式
			fileWriter = new PrintWriter(outFilePath,"utf-8");
		    csvPrinter = new CSVPrinter(fileWriter, writeCsvFormat);
			for(Entry<Integer, List<Float>> entry:map.entrySet()){
				Integer districtid = entry.getKey();
				List<Float> resEntity= entry.getValue();
				for(Float Result: resEntity) {
					List<Object> values = new ArrayList<Object>();
					
					values.add(Result);
					values.add(districtid);
					
					csvPrinter.printRecord(values);
				}
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(csvPrinter!=null)csvPrinter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  
	}
	
	
	public void outCSV3(Map<Integer, Float>  map,String outFilePath) {
		String [] headersArr = {"comment_rating","districtId"};
		CSVFormat writeCsvFormat = CSVFormat.DEFAULT.withHeader(headersArr);
		PrintWriter fileWriter = null;
		CSVPrinter  csvPrinter = null;
	    try {
	    	//不需要指定utf_8,  使用csv自带的编码格式
			fileWriter = new PrintWriter(outFilePath,"utf-8");
		    csvPrinter = new CSVPrinter(fileWriter, writeCsvFormat);
			for(Entry<Integer, Float> entry:map.entrySet()){
				Integer id = entry.getKey();
				Float f= entry.getValue();
				List<Object> values = new ArrayList<Object>();
				values.add(f);
				values.add(id);
				csvPrinter.printRecord(values);
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if(csvPrinter!=null)csvPrinter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	  
	}
	
}
