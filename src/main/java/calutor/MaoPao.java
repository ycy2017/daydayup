package calutor;

import java.util.Arrays;

public class MaoPao {

	public static void main(String[] args) {
		
		//冒泡
//		int []  arr =  {12,3,56,123,1,42,124241,111,2,1};
//		
//		for(int j= 0;j<arr.length-1;j++){
//			for(int k = 0;k<arr.length-1-j;k++ ){
//				int a = arr[k];
//				int b = arr[k+1];
//				if(a>=b){
//					arr[k] = b;
//					arr[k+1] = a;
//				}
//			}
//		}
//		
//		
//		System.out.println(Arrays.asList(arr));
//		System.out.println(arr);
//		for(int lll:arr){
//			System.out.println(lll);
//		}
		
		//快拍
		int []  arrK =  {132,1};
		
		for(int j = 0;j< arrK.length;j++){
			
			int max = arrK[0];
			int maxindex = 0;
			for(int k =0;k<arrK.length-j;k++){
				
				if(max<arrK[k]){
					//取这一轮的最大值
					max  = arrK[k];
					//最大值下标
					maxindex = k;
				}
			}
			//互换位置
			int temp = arrK[maxindex];
			arrK[maxindex] = arrK[arrK.length-1-j];
			arrK[arrK.length-1-j] = temp;
		}
		
		for(int lll:arrK){
			System.out.println(lll);
		}
		
		
	}
	
}
