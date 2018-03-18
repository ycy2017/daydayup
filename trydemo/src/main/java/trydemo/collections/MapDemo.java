package trydemo.collections;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class MapDemo {

	public static void main(String[] args) {
		
		Object ob =new Object();
		
		Map<String,String> map = new HashMap<String,String>();
		Map<String,String> Linkedmap = new LinkedHashMap<String,String>();
		Linkedmap.put("apple", "苹果");
		Linkedmap.put("watermelon", "西瓜");
		Linkedmap.put("banana", "香蕉");
		Linkedmap.put("peach", "桃子");
		Map<String,String> treemap = new TreeMap<String,String>();
		
		for(Entry<String, String> entry:Linkedmap.entrySet()){
			System.out.println(entry.getKey());
		}
		
//		Collections.s
	}
	
}
