package com.ctrip.search.dictionary.temporary;

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
import com.ctrip.search.dictionary.util.DBUtil;

public class TemporaryController {
	
	String DBKEY = "";
	
	static String SQL =  "select productname,locationcityids, poiids,poinames,sales " + 
			" from gl_activitysearch "
			+ " where isenable='T' and sourcetype=0 and productName not like '%-机票%' and distributionChannelid = 5 " + 
			" and productname like '%一日游%' ";
	
	
	static String CITY_SQL =  " select city,cityname_mapbar,lon,lat from city_mapbar ";
	
	
	
	public static void main(String[] args) {
		String ids = ",like,";
		String [] idArr = ids.split(Separator,-1);
		System.out.println(idArr.length);
		TemporaryController c = new TemporaryController();
//		Map<String, ActivityResultEntity>  map = c.fillMap();
//		System.out.println(map.size());
//		for(Entry entry:map.entrySet()){
//			System.out.println(entry.getValue());
//		}
		Map<Integer, CityEntity>  map1 = c.getCity();
		for(Entry entry:map1.entrySet()){
			System.out.println(entry.getValue());
		}
		System.out.println(map1.size());
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
							map.put(key, new ActivityResultEntity(id, poiid, sales));
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
	
}
