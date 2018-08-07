package collections;

import java.util.Arrays;

public class ErFenFaDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// int[] a = { -7, -6, -5, -3, -1, 6, 6, 6, 6, 6, 7 };
		int[] a = new int[100000];
		for (int i = 0; i < a.length; i++) {
			a[i] = i; 
//			System.out.println(i);
		}

		
		long start = System.currentTimeMillis();
		System.out.println(getCountByErFen(a));
		System.out.println("二分  cost " + (System.currentTimeMillis() - start));
		
		long start2 = System.currentTimeMillis();
		System.out.println(getCount(a));
		System.out.println("cost " + (System.currentTimeMillis() - start2));
	}

	/**
	 * n2
	 * 
	 * @param a
	 * @return
	 */
	public static int getCount(int[] a) {
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			for (int j = i; j < a.length; j++) {
				if ((a[i] + a[j]) == 0) {
					count++;
				}
			}
		}
		return count;
	}

	/**
	 * 二分法-- nlogn
	 * 
	 * @param a
	 * @return
	 */
	public static int getCountByErFen(int[] a) {
		int count = 0;
		for (int i = 0; i < a.length; i++) {
			// System.out.println(a[i] + "::" + Arrays.binarySearch(a, a[i]));
			if (Arrays.binarySearch(a, a[i]) > 0) {
				count++;
			}
		}
		return count;
	}

}
