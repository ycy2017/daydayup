package com.ctrip.search.dictionary;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class Similarity {

	private Similarity() {
	}

	public static double sim(String string1, String string2){
		Map<Character, int[]> vectorMap = new HashMap<Character, int[]>();
		int[] tempArray = null;
		char[] string1Arr =  string1.toCharArray();
		char[] string2Arr = string2.toCharArray();
		// 先判断是否包含
		boolean  is = contain(string1Arr,string2Arr);
		if(!is){
			return 0;
		}
		for (Character character1 : string1Arr) {
			if (vectorMap.containsKey(character1)) {
				vectorMap.get(character1)[0]++;
			} else {
				tempArray = new int[2];
				tempArray[0] = 1;
				tempArray[1] = 0;
				vectorMap.put(character1, tempArray);
			}
		}
		for (Character character2 : string2Arr) {
			if (vectorMap.containsKey(character2)) {
				vectorMap.get(character2)[1]++;
			} else {
				tempArray = new int[2];
				tempArray[0] = 0;
				tempArray[1] = 1;
				vectorMap.put(character2, tempArray);
			}
		}
		double result = 0;
		result = pointMulti(vectorMap) / sqrtMulti(vectorMap);
		return result;
	}

	/**
	 * 包含返回true
	 * @param string1Arr
	 * @param string2Arr
	 * @return
	 */
	private static boolean contain(char[] string1Arr, char[] string2Arr) {
		for(char char1:string1Arr){
			for(char char2:string2Arr){
				if(char1==char2){
					return true;
				}
			}
		}
		return false;
	}

	private static double sqrtMulti(Map<Character, int[]> paramMap) {
		double result = 0;
		result = squares(paramMap);
		result = Math.sqrt(result);
		return result;
	}

	// 求平方和
	private static double squares(Map<Character, int[]> paramMap) {
		double result1 = 0;
		double result2 = 0;
		Set<Character> keySet = paramMap.keySet();
		for (Character character : keySet) {
			int temp[] = paramMap.get(character);
			result1 += (temp[0] * temp[0]);
			result2 += (temp[1] * temp[1]);
		}
		return result1 * result2;
	}

	// 点乘法
	private static double pointMulti(Map<Character, int[]> paramMap) {
		double result = 0;
		Set<Character> keySet = paramMap.keySet();
		for (Character character : keySet) {
			int temp[] = paramMap.get(character);
			result += (temp[0] * temp[1]);
		}
		return result;
	}

	public static void main(String[] args) {
		String s1 = "i中华艺术宫";
		String s2 = "安徽省安庆市大观区十里乡五里工业园";
		String s3 = "like";
		double similarity =  Similarity.sim(s1.trim(), s2);
		System.out.println(similarity);
		
		Pattern p2 = Pattern.compile("[\\u4E00-\\u9FBF]+");
		boolean result = p2.matcher(s3).find();
		System.out.println("是否含有中文:"+result);
		if (result) {
			//包含中文
			System.out.println(result);
		}
		System.out.println((char)32);
		System.out.println((char)32+"ha ");
		 System.out.println(2000001 / 10000);
	}

}
