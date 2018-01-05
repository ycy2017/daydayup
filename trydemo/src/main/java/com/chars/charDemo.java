package com.chars;

import java.nio.charset.Charset;

public class charDemo {
	public static void main(String[] args) {
		
		 Charset charset_1 = Charset.forName("UtF-8");
		 Charset charset_2 = Charset.forName("UTF-8");
		 
		 
		 System.out.println(charset_1);
		 System.out.println(charset_2);
		
	}
}
