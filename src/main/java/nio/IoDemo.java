package nio;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class IoDemo {

	public static void main(String[] args) {
		
		
//		InputStream is = new  InputStream();
//		BufferedWriter br = new BufferedWriter();
		
		File  file =new File("C:\\Users\\Administrator\\Desktop\\io.txt");
		try {
			FileInputStream fis = new FileInputStream(file);
			InputStreamReader isr = new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(isr);
			
			
			//一行一读
//			while(br.ready()){
//				System.out.println(br.readLine());
//			}
			
			//一个字符一个字符去读,
			//读出来的字符转化成int类型~
			while(br.ready()){
				System.out.print(br.read());
			}
			
			
			char [] buffer = new char[100];
			while(br.ready()){
//				System.out.print();
				br.read(buffer);
				for(char c:buffer){
					System.out.print(c);
				}
//				System.out.println();
				
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
