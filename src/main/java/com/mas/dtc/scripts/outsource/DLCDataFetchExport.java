package com.mas.dtc.scripts.outsource;

import java.util.Date;

import com.mas.dtc.scripts.outsource.fileprocess.BaseFileProcess;
//import com.mas.dtc.scripts.outsource.source.impl.DCLDataSource;




/**
 * 外部数据源调用工具
 *  (1)访问外部数据源
 *  (2)导出excel表
 *  

 * @author Mathartsys
 */
public class DLCDataFetchExport {
	
		
		/**
		 * 主入口
		 *	示例
		 *  java -jar prj_mas_dtc_2016 C:\Users\Mathsys\Desktop\td白天和夜晚活动区域匹配.csv TD_tag_night mapping
		 *  (1)C:\Users\Mathsys\Desktop\td白天和夜晚活动区域匹配.csv (filepath位)：文本模版的路径，
		 *	(2)TD_tag_night (sourcemeta位)：指定那个接口，这里指调用TD的夜晚活动区域接口
		 *	(3)mapping (type位)：目前的枚举值有（“mapping”、“origin”）“origin”指调用原始层数据，“mapping”指调用dlc转化后的数据。
		 *
		 * @param args 命令行
		 */
		public static void main(String[] args) {
			
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\td.xlsx", "TD_tag_night", "mapping" };
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\tdnight.csv", "TD_tag_night", "mapping" };
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\td.xlsx", "TD_tag_night", "mapping" };
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\tdday.csv", "TD_tag_day", "mapping" };
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\TDapp.csv", "TD_tag_app", "mapping" };
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\JG4S.csv", "JG_tag_4S_1.1", "mapping" };
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\tdnight.csv", "TD_tag_night", "mapping" };
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\night.xlsx", "TD_tag_night", "mapping" };
			
			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\WW.xlsx", "WW_tag", "origin" };
//			args = new String[] { "C:\\Users\\Mathsys\\Desktop\\ww_20.csv", "WW_tag", "origin" };
			
			/*BaseFileProcess bp = BaseFileProcess.getProcess(args,DCLDataSource.getInstance());
			Date start = new Date();
			if(bp!=null){
				bp.doRun(args);
			}
			Date end = new Date();
			//243520
			System.out.println("cost time : "+(end.getTime() - start.getTime()));*/
		
	}
	
	
}
