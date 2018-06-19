package com.ctrip.search.dictionary.temporary;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.ctrip.search.dictionary.temporary.entity.ActivityResultEntity;
import com.ctrip.search.dictionary.temporary.entity.ActivitySearchEntity;
import com.ctrip.search.dictionary.temporary.entity.CityEntity;
import com.ctrip.search.dictionary.temporary.entity.PoiEntity;
import com.ctrip.search.dictionary.util.DBUtil;

import util.LocationUtils;

public class TemporaryController {
	
	String DBKEY = "";
	
	static String SQL =  "select productname,locationcityids, poiids,poinames,sales " + 
			" from gl_activitysearch "
			+ " where isenable='T' and sourcetype=0 and productName not like '%-机票%' and distributionChannelid = 5 " + 
			" and productname like '%一日游%' ";
	
	
	static String CITY_SQL =  " select city,cityname_mapbar,lon,lat from city_mapbar ";
	
	static String POI_SQL =  " select poiid,name,lon,lat from gs_poi ";
	
	public static void main(String[] args) {
		String ids = ",like,";
		String [] idArr = ids.split(Separator,-1);
		System.out.println(idArr.length);
		TemporaryController c = new TemporaryController();
		Map<String, ActivityResultEntity>  map = c.fillMap();
		System.out.println(map.size());
		for(Entry entry:map.entrySet()){
			System.out.println(entry.getValue());
		}
		
		Map<Integer, CityEntity>  map2 = c.getCity();
		for(Entry entry:map2.entrySet()){
			System.out.println(entry.getValue());
		}
		System.out.println(map2.size());
		
		Map<Integer, PoiEntity>  map3 = c.getPoi();
		for(Entry entry:map3.entrySet()){
			System.out.println(entry.getValue());
		}
		System.out.println(map3.size());
		
		//组装结果
		c.fillRes(map,map2,map3);
		String outFilePath = ""; 
		//输出
		c.out(map,outFilePath);
	}
	
	/**
	 * 组装结果
	 * @param map
	 * @param map2
	 * @param map3
	 */
	private void fillRes(Map<String, ActivityResultEntity> map, Map<Integer, CityEntity> map2,
			Map<Integer, PoiEntity> map3) {
		for(Entry<String, ActivityResultEntity> entry:map.entrySet()){
			ActivityResultEntity activityResult = entry.getValue();
			Integer  cityId = activityResult.getCityId();
			Integer  poiId =  activityResult.getPoiid();
			CityEntity cityEntity = map2.get(cityId);
			PoiEntity poiEntity = map3.get(poiId);
			if(cityEntity != null&&poiEntity != null){
				Float lat1= cityEntity.getLat();
				Float lat2= poiEntity.getLat();
				Float lon1= cityEntity.getLon();
				Float lon2= poiEntity.getLon();
				double distance= LocationUtils.getDistance((double)lat1,(double)lon1,(double)lat2,(double)lon2);
				activityResult.setDistance((float) distance);
			}
			if(cityEntity != null){
				activityResult.setCityName(cityEntity.getCityname_mapbar());
			}
			if(poiEntity != null){
				activityResult.setPoiName(poiEntity.getName());
			}
		}
		
	}

	/**
	 * 获取原始数据
	 * @return
	 */
	public List<ActivitySearchEntity> getSearch(){
		List<ActivitySearchEntity> entities = null;
		Connection con = null;
		try {
			con = DBUtil.getConnection("GlobalSearchDB_W");
			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<ActivitySearchEntity>> rsh = new BeanListHandler<ActivitySearchEntity>(ActivitySearchEntity.class);
			//获取
		    entities = qr.query(con, SQL, rsh);
		    for(ActivitySearchEntity e:entities) {
		    	System.out.println(e);
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
		return entities;
	} 
	
	private static String Separator = ",";
	private static String symbol  = "_";
	
	public Map<String, ActivityResultEntity> fillMap() {
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
					for (String poiid : poiidArr) {
						String key = id + symbol + poiid;
						ActivityResultEntity res = map.get(key);
						if (res != null) {
							Integer oldSaler = res.getSales();
							if (oldSaler != null) {
								Integer newSaler = res.getSales() + sales;
								res.setSales(newSaler);
							} else {
								res.setSales(sales);
							}
						} else {
							try{
								map.put(key, new ActivityResultEntity(Integer.parseInt(id), poiid, sales));
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
	public Map<Integer, CityEntity> getCity() {
		Map<Integer, CityEntity> map = new HashMap<Integer, CityEntity>();
		List<CityEntity> entities = null;
		Connection con = null;
		try {
			con = DBUtil.getConnection("ArchSearchDB_SELECT_1");
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
	
	public Map<Integer, PoiEntity> getPoi(){
		Map<Integer, PoiEntity> map = new HashMap<Integer, PoiEntity>();
		List<PoiEntity> entities = null;
		Connection con = null;
		try {
			con = DBUtil.getConnection("ArchSearchDB_SELECT_1");
			QueryRunner qr = new QueryRunner();
			ResultSetHandler<List<PoiEntity>> rsh = new BeanListHandler<PoiEntity>(PoiEntity.class);
			//获取
		    entities = qr.query(con, POI_SQL, rsh);
		    for(PoiEntity e:entities) {
		    	map.put(e.getPoiid(), e);
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
	
	public void out(Map<String, ActivityResultEntity> map,String outFilePath){
		
		FileOutputStream fo = null;
		PrintWriter pw = null;
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
			sb.append(resEntity.getSales());
			pw.println(sb.toString());
			if(i%100000==0){
				pw.flush();
			}
		}
		
		if(pw!=null){
			pw.flush();
			pw.close();
		}
		
		
	}
	
}
