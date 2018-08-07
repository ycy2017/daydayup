package com.ctrip.search.dictionary.temporary.util;

public class StringContainsUtils {

	public static boolean contains(String  Src ,String matcher) {
		

		if(Src == null || matcher==null || matcher.length() <= 0) {
			return false;
		}
		
		if(Src.indexOf(matcher)==-1) {
			return false;
		}
		
		String [] srcArrr= Src.split(",");
		
		for(String pathid:srcArrr) {
			if(pathid.trim().equals(matcher)) {
				return true;
			}
		}
		return false;
	}
	
	public static void main(String[] args) {
		
		System.out.println(StringContainsUtils.contains("11, 100066, 110000, 120001", "120001"));
		
	}
	
}
