package com.ctrip.search.dictionary.temporary;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ctrip.search.dictionary.temporary.entity.ActivityResultEntity;
import com.ctrip.search.dictionary.temporary.entity.ActivitySearchEntity;
import com.ctrip.search.dictionary.temporary.entity.CityEntity;
import com.ctrip.search.dictionary.temporary.entity.DistrictMappingEntity;
import com.ctrip.search.dictionary.temporary.entity.PoiEntity;
//import com.ctrip.search.dictionary.util.DBUtil;

import db.DataSourceDemo;


public class TemporaryController {
	
	String DBKEY = "";
	
//	static String SQL =  "select productname,locationcityids, poiids,poinames,sales " + 
//			" from gl_activitysearch "
//			+ " where isenable='T' and sourcetype=0 and productName not like '%-机票%' and distributionChannelid = 5 " + 
//			" and productname like '%一日游%' ";
	
	static String SQL =  " select productname,locationcityids, poiids,poinames,sales from gl_activitysearch  ";
	
//	static String SQL = " select * from gl_activitysearch WHERE  locationcityids = 21358  and sales>0 ";
	
	
	static String CITY_SQL =  " select city,cityname_mapbar,lon,lat from city_mapbar ";
	
	static String POI_SQL =  " select poiid,name,glat,glon,districtid,poitype,cityId,districtName,districtpathid,commentcount,rating  from gs_poi where   poitype='sight' or poitype='SIGHT' ";
	
	static String MAPPING_CITY_SQL = " select districtid,hotelcityid,hotelcityname from gl_district_mapping where districttype = 'CITY' ";
	
	
	static Map<String, ActivityResultEntity>  fillMap;
	static Map<Integer, CityEntity>  getCityMap;
	static Map<Integer, PoiEntity>  getPoiMap;
	static Map<Integer, DistrictMappingEntity>  getDistrictMappingByDistrictIdMap;
	static Map<Integer, DistrictMappingEntity>  getDistrictMappingByHotelcityIdMap;
	
	static {
//		fillMap = fillMap();
		getCityMap = getCity();
		getPoiMap = getPoi();
		getDistrictMappingByDistrictIdMap = getDistrictMappingByDistrictId();
		getDistrictMappingByHotelcityIdMap = getDistrictMappingByHotelcityId();
	}
	
	public static void main(String[] args) {
		TemporaryController c = new TemporaryController();
		Long startTime = System.currentTimeMillis();
		Map<String, ActivityResultEntity>  map = c.fillMap();
		System.out.println("组合 :" + map.size());
		
		//组装结果
		Long startFillTime = System.currentTimeMillis();
		c.fillRes(map,
				getCityMap,
				getPoiMap,
				getDistrictMappingByDistrictIdMap,
				getDistrictMappingByHotelcityIdMap);
		
		System.out.println("fill cost:" + (System.currentTimeMillis()-startFillTime));
		//输出
//		c.out(map,outFilePath);
		Long csvstart = System.currentTimeMillis();
		String outFilePath = "D:\\Users\\yincy\\Desktop\\a\\poi_13.csv"; 
		c.outCSV(map,outFilePath);
		System.out.println("export csv cost:" + (System.currentTimeMillis()-csvstart));
		System.out.println("cost:" + (System.currentTimeMillis()-startTime));
		
		String outFilePath2 = "D:\\Users\\yincy\\Desktop\\b\\rating_5.csv"; 
		
		OrderController oc = new OrderController(map, getPoiMap);
//		Map<Integer, List<ActivityResultEntity>> resmap = oc.get();
//		oc.outCSV(resmap, outFilePath2);
//		Map<Integer, List<Float>> resmap = oc.getDistrictRatComment();
//		oc.outCSV2(resmap, outFilePath2);
		Map<Integer, Float> resmap = oc.getDistrictRatComment2();
		oc.outCSV3(resmap, outFilePath2);

		String outFilePath3 = "D:\\Users\\yincy\\Desktop\\c\\poi_12_top10.csv";
		filter(map,resmap);
		c.outCSV(map,outFilePath3);
	}
	
	//过滤top10
	private static void filter(Map<String, ActivityResultEntity> map, Map<Integer, Float> resmap) {
		// TODO Auto-generated method stub
		Iterator<Map.Entry<String, ActivityResultEntity>> it = map.entrySet().iterator();
		
		while (it.hasNext()) {
			Entry<String, ActivityResultEntity> entry = it.next();
			ActivityResultEntity activityResult = entry.getValue();
			//districtid可能为null
			Integer districtId = activityResult.getDistrictId();
			Float  rating = activityResult.getComment_rating();
			boolean flag = false;
			if(districtId==null) {
				
			}else {
				Float comparerating = resmap.get(districtId);
				if(comparerating>rating) {
					flag = true;
				}
			}
			
			if(flag) {
				it.remove();
			}
			
		}
	}

	/**
	 * 组装结果
	 * @param map
	 * @param map2
	 * @param map3
	 * @param map4 
	 */
	private void fillRes(Map<String, ActivityResultEntity> map, Map<Integer, CityEntity> map2,
			Map<Integer, PoiEntity> map3, Map<Integer, DistrictMappingEntity> map4,Map<Integer, DistrictMappingEntity> map5) {
		
		Iterator<Map.Entry<String, ActivityResultEntity>> it = map.entrySet().iterator();
		
		while (it.hasNext()) {
			Entry<String, ActivityResultEntity> entry = it.next();
			ActivityResultEntity activityResult = entry.getValue();
			Integer cityId = activityResult.getCityId();
			Integer poiId = activityResult.getPoiid();
			CityEntity cityEntity = map2.get(cityId);
			PoiEntity poiEntity = map3.get(poiId);
			DistrictMappingEntity districtmapping = map5.get(cityId);
			//移除标志
			boolean flag = false;
			
			Integer poidistrictId = null;
			DistrictMappingEntity  poidistrictmapping = null ; 
			if(poiEntity ==null || poiEntity==null) {
				flag = true;
			}else {
				poidistrictId = poiEntity.getDistrictId();
				poidistrictmapping = map4.get(poidistrictId);
				//剔除规则
				flag = rule(activityResult, cityEntity, poiEntity,poidistrictmapping);
			}
			
			if(flag) {
				it.remove();
			}else{
				if (cityEntity != null && poiEntity != null) {
					Float lat1 = cityEntity.getLat();
					Float lat2 = poiEntity.getGlat();
					Float lon1 = cityEntity.getLon();
					Float lon2 = poiEntity.getGlon();
					double distance = DistanceUtils.getDistance((double) lat1, (double) lon1, (double) lat2,
							(double) lon2);
					// 距离
					activityResult.setDistance((float) distance);
				}
				//出发地城市districtid
				if(districtmapping!=null) {
					activityResult.setDistrictId(districtmapping.getDistrictid());
				}
				// 出发地城市名
				activityResult.setCityName(cityEntity.getCityname_mapbar());
				// 目的地景点名
				activityResult.setPoiName(poiEntity.getName());
				activityResult.setPoiDistrictId(poidistrictId);
				activityResult.setPoiCityId(poidistrictmapping.getHotelcityid());
				// 目的地方城市名
				activityResult.setPoiCityName(poidistrictmapping.getHotelcityname());
				// 添加districtidpathid
				activityResult.setDistrictidPathid(poiEntity.getDistrictpathid());
				// 添加评分
				activityResult.setComment_rating(poiEntity.getCommentcount()*poiEntity.getRating());
				//剔除规则
				flag = rule2(activityResult);
				if(flag) {
					it.remove();
				}
			}
		}
	}
	
	
	public void replaceSpace(String districtpathid) {
		
	}

	
	/**
	 * 剔除规则   距离 次数 销量
	 * @param activityResult
	 * @return
	 */
	private boolean rule2(ActivityResultEntity activityResult) {
		
		Float distance = activityResult.getDistance();
		Integer sales = activityResult.getSales();
		Integer count = activityResult.getCount();
		
		if(distance==null||sales==null||count==null||distance>300||sales<2||count<2) {
			return true;
		}
		
		return false;
	}

	/**
	 * 剔除规则
	 * @param activity
	 * @param cityEntity
	 * @param poiEntity
	 * @param map4
	 * @return
	 */
	public boolean rule(ActivityResultEntity activityResult, CityEntity cityEntity, PoiEntity poiEntity,
			DistrictMappingEntity districtMappingEntity) {
		if (activityResult == null || cityEntity == null || poiEntity == null || districtMappingEntity == null) {
			// 任意一个为null, 则不保存 ( 没有城市信息 /poi产品没有数据)
			return true;
		}
		// 如果poiid=0 移除
		if ((activityResult.getPoiid() == 0 || activityResult.getPoiid() == null)) {
			return true;
		}
		Integer cityId = activityResult.getCityId();
		// 如果出发城市和目的地的cityId同一个城市 移除
		if ((cityId == null || cityId.equals(poiEntity.getCityId()))) {
			return true;
		}
		// 目的地districtid = 0  移除
		if (poiEntity.getDistrictId() == 0 || poiEntity.getDistrictId() == null) {
			return true;
		}

		Integer hotelcityid = districtMappingEntity.getHotelcityid();
		// 出发地cityid 和 poi 通过 gl_district_mapping查询的 hotelcityid 相同 移除 
		if (hotelcityid == null || hotelcityid == 0 || hotelcityid.equals(activityResult.getCityId())) {
			return true;
		}

		return false;
	}
	
	/**
	 * 获取原始数据
	 * @return
	 */
	public static List<ActivitySearchEntity> getSearch(){
		List<ActivitySearchEntity> entities = null;
		Connection con = null;
		try {
//			con = DBUtil.getConnection("GlobalSearchDB_W");
			con = DataSourceDemo.getConnection();
			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<ActivitySearchEntity>> rsh = new BeanListHandler<ActivitySearchEntity>(ActivitySearchEntity.class);
			//获取
		    entities = qr.query(con, SQL, rsh);
		    for(ActivitySearchEntity e:entities) {
		    	System.out.println(e);
		    }
		    System.out.println(entities.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				 try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return entities;
	} 
	
	private static String Separator = ",";
	private static String symbol  = "_";
	
	public  Map<String, ActivityResultEntity> fillMap() {
		Map<String, ActivityResultEntity> map = new HashMap<String, ActivityResultEntity>();
		List<ActivitySearchEntity> list = getSearch();
		for (ActivitySearchEntity activitySearchEntity : list) {
			String ids = activitySearchEntity.getLocationcityids();
			String poiids = activitySearchEntity.getPoiids();
			Integer sales = activitySearchEntity.getSales();
			if (ids != null || poiids != null) {
				String[] idArr = ids.split(Separator, -1);
				String[] poiidArr = poiids.split(Separator, -1);

				for (String id : idArr) {
					if("0".equals(id)) {
						//cityid为0
						continue;
					}
					for (String poiid : poiidArr) {
						//poiids为空字符
						if(poiid.trim().length()<=0) {
							continue;
						}
						String key = id + symbol + poiid;
						ActivityResultEntity res = map.get(key);
						if (res != null) {
							Integer oldSaler = res.getSales();
							if (oldSaler != null) {
								Integer newSaler = oldSaler + sales;
								//销量
								res.setSales(newSaler);
							} else {
								res.setSales(sales);
							}
							//次数
							res.setCount(res.getCount()+1);
						} else {
							try{
								map.put(key, new ActivityResultEntity(Integer.parseInt(id), Integer.parseInt(poiid), sales,1));
							}catch(Exception e){
								e.printStackTrace();
							}
							
						}
					}
				}

			}
		}
		return map;
	}

	/**
	 * 获取城市
	 * @return
	 */
	public static Map<Integer, CityEntity> getCity() {
		Map<Integer, CityEntity> map = new HashMap<Integer, CityEntity>();
		List<CityEntity> entities = null;
		Connection con = null;
		try {
//			con = DBUtil.getConnection("ArchSearchDB_SELECT_1");
			con = DataSourceDemo.getConnection();
			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<CityEntity>> rsh = new BeanListHandler<CityEntity>(CityEntity.class);
			//获取
		    entities = qr.query(con, CITY_SQL, rsh);
		    for(CityEntity e:entities) {
		    	map.put(e.getCity(), e );
		    }
		    System.out.println(entities.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con!=null) {
				 try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	public static Map<Integer, PoiEntity> getPoi(){
		Map<Integer, PoiEntity> map = new HashMap<Integer, PoiEntity>();
		List<PoiEntity> entities = null;
		Connection con = null;
		try {
//			con = DBUtil.getConnection("ArchSearchDB_SELECT_1");
			con = DataSourceDemo.getConnection();
			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<PoiEntity>> rsh = new BeanListHandler<PoiEntity>(PoiEntity.class);
			//获取
		    entities = qr.query(con, POI_SQL, rsh);
		    for(PoiEntity e:entities) {
		    	map.put(e.getPoiid(), e);
		    }
		    System.out.println(entities.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				 try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	public static Map<Integer, DistrictMappingEntity> getDistrictMappingByDistrictId(){
		Map<Integer, DistrictMappingEntity> map = new HashMap<Integer, DistrictMappingEntity>();
		List<DistrictMappingEntity> entities = null;
		Connection con = null;
		try {
//			con = DBUtil.getConnection("ArchSearchDB_SELECT_1");
			con = DataSourceDemo.getConnection();
			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<DistrictMappingEntity>> rsh = new BeanListHandler<DistrictMappingEntity>(DistrictMappingEntity.class);
			//获取
		    entities = qr.query(con, MAPPING_CITY_SQL, rsh);
		    for(DistrictMappingEntity e:entities) {
		    	map.put(e.getDistrictid(), e);
		    }
		    System.out.println(entities.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				 try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	public static Map<Integer, DistrictMappingEntity> getDistrictMappingByHotelcityId(){
		Map<Integer, DistrictMappingEntity> map = new HashMap<Integer, DistrictMappingEntity>();
		List<DistrictMappingEntity> entities = null;
		Connection con = null;
		try {
//			con = DBUtil.getConnection("ArchSearchDB_SELECT_1");
			con = DataSourceDemo.getConnection();
			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<DistrictMappingEntity>> rsh = new BeanListHandler<DistrictMappingEntity>(DistrictMappingEntity.class);
			//获取
		    entities = qr.query(con, MAPPING_CITY_SQL, rsh);
		    for(DistrictMappingEntity e:entities) {
		    	map.put(e.getHotelcityid(), e);
		    }
		    System.out.println(entities.size());
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(con!=null) {
				 try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}
	
	public void out(Map<String, ActivityResultEntity> map,String outFilePath){
		
		FileOutputStream fo = null;
		PrintWriter pw = null;
		try {
			fo = new FileOutputStream(outFilePath + "");
			pw = new PrintWriter(new OutputStreamWriter(fo,"utf-8")); 
			int i = 1;
			for(Entry<String, ActivityResultEntity> entry:map.entrySet()){
				i++;
				ActivityResultEntity resEntity= entry.getValue();
				StringBuilder sb = new StringBuilder();
				sb.append(resEntity.getCityId()+"|\t|");
				sb.append(resEntity.getCityName()+"|\t|");
				sb.append(resEntity.getPoiid()+"|\t|");
				sb.append(resEntity.getPoiName()+"|\t|");
				sb.append(resEntity.getPoiCityId()+"|\t|");
				sb.append(resEntity.getPoiCityName()+"|\t|");
				sb.append(resEntity.getCount()+"|\t|");
				sb.append(resEntity.getSales()+"|\t|");
				sb.append(resEntity.getDistance());
				pw.println(sb.toString());
				if(i%100000==0){
					pw.flush();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(pw!=null){
				pw.flush();
				pw.close();
			}
		}
	}
	
	public void outCSV(Map<String, ActivityResultEntity> map,String outFilePath) {
		String [] headersArr = {"districtId","CityId","cityName（城市名）","POIID","POIName（景点名）","景点districtId","景点cityid","景点城市名称","次数","销量","距离","districtidpathid","comment*rating"};
		CSVFormat writeCsvFormat = CSVFormat.DEFAULT.withHeader(headersArr);
		PrintWriter fileWriter = null;
		CSVPrinter  csvPrinter = null;
	    try {
	    	//不需要指定utf_8,  使用csv自带的编码格式
			fileWriter = new PrintWriter(outFilePath,"utf-8");
		    csvPrinter = new CSVPrinter(fileWriter, writeCsvFormat);
			for(Entry<String, ActivityResultEntity> entry:map.entrySet()){
				List<Object> values = new ArrayList<Object>();
				ActivityResultEntity resEntity= entry.getValue();
				
				values.add(resEntity.getDistrictId());
				values.add(resEntity.getCityId());
				values.add(resEntity.getCityName());
				values.add(resEntity.getPoiid());
				values.add(resEntity.getPoiName());
				values.add(resEntity.getPoiDistrictId());
				values.add(resEntity.getPoiCityId());
				values.add(resEntity.getPoiCityName());
				values.add(resEntity.getCount());
				values.add(resEntity.getSales());
				values.add(resEntity.getDistance());
				values.add(resEntity.getDistrictidPathid());
				values.add(resEntity.getComment_rating());
				
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
