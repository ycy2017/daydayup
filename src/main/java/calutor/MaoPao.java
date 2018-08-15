package calutor;

import java.util.Arrays;

public class MaoPao {

	public static void main(String[] args) {
		
		//ð��
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
		
		//����
		int []  arrK =  {132,1};
		
		for(int j = 0;j< arrK.length;j++){
			
			int max = arrK[0];
			int maxindex = 0;
			for(int k =0;k<arrK.length-j;k++){
				
				if(max<arrK[k]){
					//ȡ��һ�ֵ����ֵ
					max  = arrK[k];
					//���ֵ�±�
					maxindex = k;
				}
			}
			//����λ��
			int temp = arrK[maxindex];
			arrK[maxindex] = arrK[arrK.length-1-j];
			arrK[arrK.length-1-j] = temp;
		}
		
		for(int lll:arrK){
			System.out.println(lll);
		}
		
		
	}
	
}
