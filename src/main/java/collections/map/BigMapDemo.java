package collections.map;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import collections.PoiEntity;



public class BigMapDemo {

	public static void main(String[] args) {
		
//		System.out.println(120.19325111);
		
		/**
		 * 100w 数据,5个字节 占用多少空间
		 */
		Map<Integer,PoiEntity> poiMap = getPoi();
		for(Entry poi:poiMap.entrySet()){
			System.out.println(poi.getValue());
		}
		System.out.println(poiMap.size());
		

		
	}

	public static Map<Integer,PoiEntity>  getPoi(){
		Map<Integer,PoiEntity> map = new HashMap<Integer,PoiEntity>();
		for(int i = 1;i<11;i++){
			//map.put(i, new PoiEntity(i,"上海"+i,(float) 120.19325,(float)30.250309));
		}
		return map;
	}
	
	public static Map<Integer,PoiEntity>  getCity(){
		Map<Integer,PoiEntity> map = new HashMap<Integer,PoiEntity>();
		for(int i = 1;i<1000000;i++){
			//map.put(i, new PoiEntity(i,"上海"+i,(float) 120.19325,(float)30.250309));
		}
		return map;
	}
	
}
