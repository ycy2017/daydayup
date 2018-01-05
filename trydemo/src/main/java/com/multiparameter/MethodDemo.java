package com.multiparameter;

import java.util.Date;

public class MethodDemo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		new MethodDemo().getPar("1",2,new String[]{"a","asd"},123,123);
		
		
	}

	public void getPar(String a,Object ... parameters){
		
		System.out.println(parameters);
		for(Object obj:parameters){
			System.out.println(obj);
		}
		
//		System.out.println(parameters[11]);
	}
	
	
}
