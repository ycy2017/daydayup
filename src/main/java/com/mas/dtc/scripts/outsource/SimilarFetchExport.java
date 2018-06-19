package com.mas.dtc.scripts.outsource;

import java.util.Date;

import com.mas.dtc.scripts.outsource.fileprocess.BaseFileProcess;
import com.mas.dtc.scripts.outsource.source.impl.SimilarDataSource;

public class SimilarFetchExport {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		args = new String[] { "D:\\Users\\yincy\\Desktop\\key.txt", "TD_tag_night", "mapping" };
		BaseFileProcess bp = BaseFileProcess.getProcess(args, SimilarDataSource.getInstance());
		Date start = new Date();
		if (bp != null) {
			bp.doRun(args);
		}
		Date end = new Date();
//		线程数是2个   5000条/1469 099ms
		
		//243520
		System.out.println("cost time : "+(end.getTime() - start.getTime()));
	}

}
