package trydemo.pattern;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternDemo {

	
	public static void main(String[] args) {
		// . 代表任意
		// \w 代表  字母 数字 下划线
		// \d 代表数字
		//ahycy@163.com
		String regex = "\\w+"+"@(163|qq)\\.com";
		
		Pattern pattern =  Pattern.compile(regex);
		
		Matcher m = pattern.matcher("lll@163.com");
		System.out.println(m.matches());
		
		Matcher m1 = pattern.matcher("lll@163.com1");
		System.out.println(m1.matches());
		
		
		
		new PatternDemo().IpRegex();
		
		
	}
	
	public void IpRegex(){
		String ip = "240.255.21.1";
		
		//一数
		//二位数
		/*
		 * 三位
		 * 1开头的
		 * 2开头  
		 * 		 200~249  
		 * 		 250~259  
		 */
		
		String regex = "((\\d|\\d\\d|1\\d\\d|2[0-4]\\d|25[0-5])\\.){3}(\\d|\\d\\d|1\\d\\d|2[0-4]\\d|25[0-5])";
		Pattern pattern =  Pattern.compile(regex);
		Matcher m = pattern.matcher(ip);
		System.out.println(m.matches());
	}
	
}
