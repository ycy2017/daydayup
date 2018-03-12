package trydemo.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Collection {
	public static void main(String[] args) {

		Integer[] arr = { 213, 124, 12312, 312, 1 };
		Arrays.sort(arr);
		System.out.println(arr);
		System.out.println(Arrays.toString(arr));

		List<Integer> list = new ArrayList<Integer>();
		list.add(22);
		list.add(11);
		list.add(123);
		list.add(1);

		Collections.sort(list);

	}
}
