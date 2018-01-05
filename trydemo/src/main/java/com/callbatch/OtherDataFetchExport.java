package com.callbatch;

import java.util.Date;

import com.callbatch.outsource.fileprocess.BaseFileProcess;
import com.callbatch.outsource.source.impl.OtherDataSource;

/**
 * 
 * 百度地图等接口调用工具
 * 
 * @author Mathsys
 *
 */
public class OtherDataFetchExport {
	
	/**
	 * 主入口
	 *	示例
	 *  java -jar prj_mas_dtc_2016 C:\Users\Mathsys\Desktop\td白天和夜晚活动区域匹配.csv baidu locaiton
	 *  (1)C:\\Users\\Mathsys\\Desktop\\tdnight.csv (filepath位)：文本模版的路径，
	 *	(2)baidu (sourcemeta位)：指定那个接口，这里指调用百度接口
	 *	(3)mapping (type位)：目前的枚举值有（“location”、“adress”）“location”指调用原始层数据，“adress”指调用dlc转化后的数据。
	 *
	 * @param args 命令行
	 */
	public static void main(String[] args) {
		
		args = new String[] { "C:\\Users\\Mathsys\\Desktop\\baidu_location.csv", "baidu", "location" };
		
		BaseFileProcess bp = BaseFileProcess.getProcess(args, OtherDataSource.getInstance());
		Date start = new Date();
		if (bp != null) {
			bp.doRun(args);
		}
		Date end = new Date();
		//130174
		System.out.println("COST TIME : " + (end.getTime()  - start.getTime()));
	}
	
}
