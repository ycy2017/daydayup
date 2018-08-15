package collections.map;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.ctrip.search.dictionary.temporary.entity.PoiEntity;

/**
 * 测试在同一次迭代循环中 多次删除
 * @author yincy
 *
 */
public class RemoveMapDemo {

	public static void main(String[] args) {
		
		Map<Integer,PoiEntity> poiMap = BigMapDemo.getPoi();
		Iterator<Entry<Integer, PoiEntity>> it = poiMap.entrySet().iterator();
		
		while(it.hasNext()) {
			Entry<Integer, PoiEntity> entry = it.next();
			PoiEntity pe = entry.getValue();
			if(entry.getKey() == 2) {
				it.remove();
				/*
				 * 调用多次remove就报错
				 */
//				it.remove();
//				it.remove();
			}
		}
		
		for(Entry<Integer, PoiEntity> entry  : poiMap.entrySet()) {
			System.out.println(entry.getValue());
		}
		
	}
	
}
