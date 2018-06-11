package com.mas.dtc.scripts.outsource.fileprocess.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;

import com.mas.dtc.scripts.outsource.fileprocess.BaseFileProcess;
import com.mas.dtc.scripts.outsource.source.IDataSource;



/**
 * 使用csv导出数据结果
 * @author Mathsys
 *
 */
public class ProcessCsv extends BaseFileProcess  {

	/**
	 * csv写对象
	 */
	private CSVPrinter csvPrinter;
	
	/**
	 * 日志
	 */
	private static final Logger LOG = Logger.getLogger(ProcessCsv.class);

	public ProcessCsv(IDataSource bds){
		this.dataSource = bds;
	}
	
	@Override
	public void initTableHead(String inFilePath) {
		
		BufferedReader br = null;
		CSVParser csvFileParser = null;
		// 创建CSVFormat读取表头 (1)不忽视空行 (2)自动trim除去手尾空格
		CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader().withIgnoreEmptyLines(false).withTrim();
		try {
			// 初始化FileReader object
			br = new BufferedReader(new InputStreamReader(new FileInputStream(inFilePath), "utf-8"));// 解决乱码问题
			// 初始化 CSVParser object
			csvFileParser = new CSVParser(br, csvFileFormat);
			Map<String, Integer> headerMap = csvFileParser.getHeaderMap();
			LOG.info(" header " + headerMap);
			boolean flag = true;
			for (Entry<String, Integer> entry : headerMap.entrySet()) {
				String key = entry.getKey().trim();
				if ("|".equals(key)) {
					this.index = entry.getValue();
					flag = false;
//					continue;
				}
				if (flag) {
					// 加载参数表头
					this.ids.add(key);
				} else {
					// 加载标签表头
					this.tagHead.add(key);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("csv文件读取异常");
		} finally {
			try {
				br.close();
				csvFileParser.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void getDataToExport(String inFilePath, String outFilePathString, String type) {
		// 读
		BufferedReader br = null;
		CSVParser csvReadParser = null;
		// 写
		PrintWriter fileWriter = null;
		
		// 表头
		String[] headersArr = getHeaders().toArray(new String[getHeaders().size()]); 
		try {
			// 初始化FileReader object
			br = new BufferedReader(new InputStreamReader(new FileInputStream(inFilePath), "utf-8"));
			// 读取csv文件操作(1).withHeader(headersArr)这个方法添加后,遍历会跳过表头 (2)自动trim除去首尾空格
			CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(headersArr).withSkipHeaderRecord()
					.withIgnoreEmptyLines(false).withTrim();
			// 初始化 CSVParser object
			csvReadParser = new CSVParser(br, csvFileFormat);
			// 写入csv文件操作
			CSVFormat writeCsvFormat = CSVFormat.DEFAULT.withHeader(headersArr);
		    fileWriter = new PrintWriter(outFilePathString, "utf-8");
			this.csvPrinter = new CSVPrinter(fileWriter, writeCsvFormat);

			// 遍历
			Iterator<CSVRecord> CSVRecordIterator = csvReadParser.iterator();
			while (CSVRecordIterator.hasNext()) {
				CSVRecord CsvRecord = CSVRecordIterator.next();
				Map<String, Object> ids = new LinkedHashMap<String, Object>();
				// 构建接口调用参数ids
				for (int i = 0; i < this.ids.size(); i++) {
					String idskey = this.ids.get(i);
					String idsvalue = null;
					if(CsvRecord.isSet(idskey)){
						//取值前判断CsvRecord是否有值,否则对应的colum没有value会报错
					    idsvalue = CsvRecord.get(idskey);
					}					
					if (idsvalue != null && !"".equals(idsvalue)) {
						// 单元格没有值,不需要构建该参数
						ids.put(idskey, idsvalue);
					}
				}
				
				
				if (ids.size() > 0) {
					// 参数合法调用接口   
					this.idsAccount++;
				     doFetechDataAndWrite(ids, type);
				}else{
					LOG.warn(ids + "is invalid");
				}
				
			}
			
			//当线程跑完之后再关闭资源
			closeSource();
			
		} catch (Exception e) {
//			e.printStackTrace();
			LOG.error("csv文件读写异常");
		} finally {
			
			try {
				if (csvReadParser != null) {
					csvReadParser.close();
				}
			} catch (IOException e) {
				LOG.error("close stream exception ", e);
			}


			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				LOG.error("close stream exception ", e);
			}
			
			
		}
	}

	@Override
	public void closeIO() {
		
				LOG.info("datafetch done, read row total " + ",exportPath : " + this.outFilePath);
				try {
					this.csvPrinter.close();
				} catch (IOException e) {
					LOG.error(" csvPrinter close failure ");
					LOG.error(e.getMessage());
				}
		
	}
	
	@Override
	public void doWriteToFile(Map<String, Object> ids, Map<String, Object> srcMap,Object [] other) {
		List<String> values= srcMaptoCSV( ids,srcMap);
		// 按行填写值
		try {
			this.csvPrinter.printRecord(values);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 写数据
	 * 
	 * @param csvPrinter commoncsv的对象
	 * @param ids 调用参数
	 * @param result 结果
	 * @return 插入到行的集合
	 */
	private List<String> srcMaptoCSV( Map<String, Object> ids,Map<String, Object> result) {
		
		List<String> values = new ArrayList<>();
		
		for(String idsStr:this.ids){
			if(ids != null && ids.get(idsStr)!=null){
				String id= String.valueOf(ids.get(idsStr));
				values.add(id);
			}else{
				values.add("");
			}
		}
		for(String tagHeadStr:this.tagHead){
			if(result != null && result.get(tagHeadStr) !=null){
				String tag= String.valueOf(result.get(tagHeadStr));
				values.add(tag);
			}else{
				values.add("");
			}
		}
		return values;
	}
	
	
}
