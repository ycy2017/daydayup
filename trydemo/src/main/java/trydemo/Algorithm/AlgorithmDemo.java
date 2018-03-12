package trydemo.Algorithm;

import java.util.Arrays;

public class AlgorithmDemo {
	
	
	public static void main(String[] args) {
		new AlgorithmDemo().maopao();
		new AlgorithmDemo().kuaipai();
		new AlgorithmDemo().hebingArr();
		new AlgorithmDemo().erfen();
	}	
	
	/**
	 * 冒泡
	 */
	public void maopao(){
		int [] arr = {4777776,667,7777,4444,555555,33333,8765432};
	
		for(int j =1;j<arr.length;j++){
			for(int k = 0;k<arr.length-j;k++){
				if(arr[k]>arr[k+1]){
					int temp  = arr[k];
					arr[k] = arr[k+1];
					arr[k+1] = temp;
				}
			}
		}
		
		System.out.println(Arrays.toString(arr));
	}
	
	public void kuaipai(){
		int [] arr = {33,1,123123,12,3,4,5,324,5,4,56,75,3,21};
		
		for(int i = 1;i<arr.length;i++){
			
			int max =arr[0];
			int maxindex=0 ; 
			for(int j=0;j<arr.length+1-i;j++){
				if(arr[j]>max){
					max = arr[j];
					maxindex = j;
				}
			}
			arr[maxindex]=arr[arr.length-i];
			arr[arr.length-i] = max;
		}
		System.out.println(Arrays.toString(arr));
	}
	
	
	public void erfen(){
		int target = 32;
		int [] arr = {1,2,4,32,33, 56, 75, 324, 123123};
		int min = 0;
		int max = arr.length-1;
		boolean flag = false;
		
		while(true){
			int b = (min+max)/2;
			if(target > arr[b]){
				min = b+1;
			}else if(target<arr[b]){
				max = b;
			}else if(arr[b]==target){
				flag =true;
				System.out.println(b);
				break;
			}
			
			if(min == max){
				break;
			}
			
		}
		
		System.out.println(flag);
	}
	
	public void hebingArr(){
		int [] arr = { 75, 324, 1231234};
		int [] arr2 = { 33333, 555555, 4777776, 8765432};
		int [] newArr = new int[arr.length+arr2.length];
		int i = 0;
		int j = 0;
		int k = 0;
		int min = 0;
		while(i<arr.length && j<arr2.length){
			if(arr[i]<arr2[j]){
				min = arr[i++];
			}else{
				min = arr2[j++];
			}
			newArr[k] = min;
			k++;
		}
		
		System.out.println(i);
		System.out.println(j);
		
		if(i==arr.length){
			
		}
		if(j==arr2.length){
			
		}
		System.out.println(Arrays.toString(newArr));
	}
	
}
