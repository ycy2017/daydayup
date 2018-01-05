package com.callbatch.outsource.fileprocess.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.callbatch.outsource.fileprocess.BaseFileProcess;
import com.callbatch.outsource.source.IDataSource;




/**
 * 使用excel导出数据结果
 * @author Mathsys
 *
 */
public class ProcessExcel extends BaseFileProcess{
	
	private XSSFSheet sheet;
	
	private FileOutputStream fileOutputStream;
	
	private XSSFWorkbook writerworkbook;
	
	public ProcessExcel(IDataSource bds){
		this.dataSource = bds;
	}
	
	@Override
	public void initTableHead(String inFilePath){
		//获取表格头信息
		XSSFWorkbook workBook = null;
		FileInputStream fis = null;
		try {
			File file =new File(inFilePath);
			LOG.info( "=======>" + file.getAbsolutePath()+" is exists: "+file.exists());
		    fis = new FileInputStream(inFilePath);
			workBook = new XSSFWorkbook(fis);
		} catch (FileNotFoundException e) {
			LOG.error("FileNotFound :" + e.getMessage());
		} catch (IOException e) {
			LOG.error("IOException :" + e.getMessage());
		}finally{
			try {	
				if(fis != null){
				fis.close();
				}
			} catch (IOException e) {
				LOG.error("close fail :" + e.getMessage());
			}
		}
		Sheet sheet = workBook.getSheetAt(0);
		//获取表格头
		 Row row = sheet.getRow(0);

		// 获取参数下标
		Iterator<Cell> indexIterator = row.cellIterator();
		while (indexIterator.hasNext()) {
			Cell cell = indexIterator.next();
			String tagHead = cell.getStringCellValue().trim();
			if (HEADSPLIT.equals(tagHead)) {
				break;
			}
			this.index += 1;
		}
		//获取
		 Iterator<Cell> iterator= row.cellIterator();
		 int times = 0 ;
		 while(iterator.hasNext()){
			 Cell cell = iterator.next();
			 if (times < this.index) {
				 String idHead = cell.getStringCellValue().trim();
				 this.ids.add(idHead);
			 }else if (times >= this.index){
				 String tagHead = cell.getStringCellValue().trim();
				 this.tagHead.add(tagHead);
			 }
			 times++;
		 }
	}
	

	@Override
	public void getDataToExport(String inFilePath,String outFilePathString,String type) {

		XSSFWorkbook workbook = null;
		try {
			FileInputStream fis = new FileInputStream(inFilePath);
			workbook = new XSSFWorkbook(fis);
			//写文件线程
			this.writerworkbook = new XSSFWorkbook();
			this.sheet = writerworkbook.createSheet("new");
			//给writerworkbook写表头
			
			doHeader(this.sheet.createRow(0));
			this.outFilePath = outFilePathString;
			
			
		} catch (FileNotFoundException e) {
			LOG.error("not found file " + e.getMessage());
		} catch (IOException e) {
			LOG.error("IO error " + e.getMessage());
		}
		Sheet sheet = workbook.getSheetAt(0);
		 // 获取Row
//		int lastRowNum = sheet.getPhysicalNumberOfRows();
		// // 存放参数
		Map<String, Object> parameter = null;
		// 异常单元格序号
		int errorRowIndex = 0;
		try {
			Iterator<Row> rowIterator = sheet.iterator();
			// 迭代行
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				
				if (errorRowIndex == 0) {
					errorRowIndex ++;
					// 表格头不读取
					continue;
				}
				parameter = new LinkedHashMap<String, Object>();
				Iterator<Cell> cellIterator = row.iterator();
				int cellIndex = 0;
				// 迭代列
				while (cellIterator.hasNext()) {
					if (cellIndex >= this.index) {
						// 到参数列终止
						break;
					}
					// 获取参数key
					String key = this.ids.get(cellIndex);
					cellIndex++;
					Cell cell = cellIterator.next();
					cell.setCellType(Cell.CELL_TYPE_STRING);
					String value = cell.getStringCellValue();
					if (!"".equals(value.trim())) {
						// 单元格没有值,不需要插入对应参数
						parameter.put(key.trim(), value.trim());
					}
				}
				
				// 入参参数
				LOG.info(parameter);
				if (parameter.size() > 0) {
					// 并发获取数据把数据放到写队列中
					doFetechDataAndWrite(parameter,type,errorRowIndex);
				}
				//目前读取到第几行了
				errorRowIndex++;
			}

		
			
		} catch (Exception e) {
			LOG.error(e.getMessage() + " row  at " + errorRowIndex + ":" + parameter);
		}
		
		// 导出excel
		try {
			this.fileOutputStream = new FileOutputStream(outFilePathString);
			LOG.info("datafetch done, read row total " + errorRowIndex + ",exportPath :" + outFilePathString);
		} catch (FileNotFoundException e) {
			LOG.error("create file error  " + e.getMessage());
		} 

		//当线程跑完之后再关闭资源
		closeSource();
		
	}

	/**
	 * 写表头
	 * @param xssfRow 
	 */
	private void doHeader(XSSFRow xssfRow) {
		List<String> head = super.getHeaders();
		for(int i = 0;i<head.size();i++){
			// 从index 开始写
			Cell cell = xssfRow.createCell(i);
			// 获取标签层次
			String mapPath = head.get(i);
			cell.setCellValue(String.valueOf(mapPath));
		}
	}
	
	@Override
	public void doWriteToFile(Map<String, Object> ids, Map<String, Object> srcMap, Object[] objects) {
		
		if(objects.length > 0 && objects[0] instanceof Integer){
			//rowNmber 
			int rowNmber = (int) objects[0];
			Row row = this.sheet.createRow(rowNmber);
			srcMap.putAll(ids);
			doWriterRow(srcMap, row );
		}
		
	}

	@Override
	public void closeIO() {
	
		// 导出excel
		try {
			this.writerworkbook.write(fileOutputStream);
			LOG.info("datafetch done, read row total " + "" + ",exportPath :" + this.outFilePath);
		} catch (FileNotFoundException e) {
			LOG.error("create file error  " + e.getMessage());
		} catch (IOException e) {
			LOG.error("create file io error  " + e.getMessage());
		} finally {
			
				try {
					if (writerworkbook != null) {
						this.writerworkbook.close();
					}
				} catch (IOException e1) {
					LOG.error("IO close error  " + e1.getMessage());
				}
				
				try {
					if (fileOutputStream != null) {
						this.fileOutputStream.close();
					}
				} catch (IOException e1) {
					LOG.error("IO close error  " + e1.getMessage());
				}
				
				
				
		}
		
	}

	/**
	 * 
	 * 按行填写数据到excel
	 * 
	 * @param result 查询结果
	 * @param row 要写值的行
	 */
	private void doWriterRow(Map<String, Object> result, Row row) {
		
			List<String> head = super.getHeaders();
			for(int i = 0;i<head.size();i++){
				// 从index 开始写
				Cell cell = row.createCell(i);
				// 获取标签层次
				String mapPath = head.get(i);
				Object value = getValueByKey(result, mapPath);
				if (cell != null && value != null) {
					cell.setCellValue(String.valueOf(value));
				}
			}
			
	}

	
	
	
}
