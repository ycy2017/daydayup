package collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListDemo {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println((222.3332 -222.33)>0 );
		List<Float> list = new ArrayList<Float>() {
			{
				add(new Float( 12.2));
				add(new Float(222.33));
				add(new Float(222.33));
				add(new Float(12.2));
				add(new Float(222.33));
				add(new Float(12.2));
				add(new Float(222.33));
				add(new Float(12.2));
				add(new Float(222.3332));
				add(new Float(12.2));
			}
		};
		
		Collections.sort(list,new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				// TODO Auto-generated method stub
				float f1 = (float) o1;
				float f2 = (float) o2;
				return (int) (f2 - f1);
			}
		});
		
		for(Float f:list ) {
			System.out.println(f);
		}
		
		
		
	}

}
