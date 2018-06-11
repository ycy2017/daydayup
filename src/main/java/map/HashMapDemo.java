package map;

import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Country  china = new Country("China", 1000l);
		Country  jap = new Country("Jap", 1000l);
		Country  usa = new Country("USA", 1000l);
		
		Map<Country,String> map = new HashMap<Country,String>();
		
		map.put(china, "±±¾©");
		map.put(jap,"tokyo");
		map.put(usa, "newyerk");
		
		System.out.println(map);
		
	}

}

class Country{
	
	private String countryName;
	private long pop;
	
	
	public Country(String name,Long pop){
		this.countryName = name;
		this.pop = pop;
	}


	@Override
	public String toString() {
		return "Country [countryName=" + countryName + ", pop=" + pop + "]";
	}
	
	
	
	
}