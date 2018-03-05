package hash;

import java.util.HashMap;
import java.util.Map;

public class A {
		
	private String context;
	
	public A(String a){
		this.context = a;
	}
	
	public static void main(String[] args) {
		A a = new A("china");
		A b =new A("中国");
		System.out.println(a.hashCode()+":"+b.hashCode());
		
		Map<Object,String> map = new HashMap<Object,String>();
		
		map.put(b, "北京");
		map.put(a, "北京");
		System.out.println(map);
	}

	@Override
	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((context == null) ? 0 : context.hashCode());
		/*
		 *1、 hashcode相同,equals不同时,Entry这个实例在map中以链表的方式存储
		 */
		int result = 1;
		
		
		
		return result;
	}

	@Override
	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		A other = (A) obj;
//		if (context == null) {
//			if (other.context != null)
//				return false;
//		} else if (!context.equals(other.context))
//			return false;
		
		/*
		 * 
		 *2、 hashcode相同,equals相同时,Entry这个实例在会被覆盖
		 */
		return true;
	}

	@Override
	public String toString() {
		return "A [context=" + context + "]";
	}
	
	
	
}
