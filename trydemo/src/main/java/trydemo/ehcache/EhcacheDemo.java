package trydemo.ehcache;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheDemo {

	public static void main(String[] args) {
//		System.err.println(this.getClass().getResource("/config/ehcache/ehcache-test.xml"));
		CacheManager manager = CacheManager.create();  
		//key:根据此值获取缓存的value，不可重复，value值需要缓存的数据  
		Element element = new Element("site", "like");  
		//cacheName:指ehcache-test.xml配置文件中的缓存名称 name="xxx"中的值  
		net.sf.ehcache.Cache cache = manager.getCache("locketCache");  
		cache.put(element);  
		System.out.println(cache.get("site"));
	}
	
}
