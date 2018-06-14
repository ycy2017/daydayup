package com.mas.dtc.scripts.outsource.fileprocess.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.ctrip.search.dictionary.KeyWordEntity;
import com.ctrip.search.dictionary.NameSimilarityEntity;
import com.mas.dtc.scripts.outsource.fileprocess.BaseFileProcess;
import com.mas.dtc.scripts.outsource.source.IDataSource;

public class ProcessLine  extends BaseFileProcess{

	List<String> key ;
	
	PrintWriter bw ;
	
	
	public ProcessLine(IDataSource bds) {
		this.dataSource = bds;
	}

	@Override
	public void initTableHead(String inFilePath) {
		// TODO Auto-generated method stub
		
		try {
			this.key = getKeyWord( inFilePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	Pattern p1 = Pattern.compile(".*[a-zA-z].*");
	Pattern p2 = Pattern.compile("[\\u4E00-\\u9FBF]+");

	/**
	 * 初始化规则
	 * @param fileDec
	 * @return
	 * @throws Exception 
	 */
	public List<String> getKeyWord(String fileDec) throws Exception {
		File file = new File(fileDec);
		List<String> list = new LinkedList<String>();
		if (!file.exists()) {
		}
		if (!file.isFile()) {
			System.out.println("输入正确的文件路径!!!!!");
		}
		FileInputStream fis = null;
		BufferedReader br = null;
		int i = 0;
		try {
			fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis, "UTF-8"));
			while (br.ready()) {
				String keyword = br.readLine();
				/*
				 * 1/首尾空格 2/含有中文的地址,把中间空格也去除掉 3/40字符以下
				 */
				keyword = keyword.trim();
				boolean result = p2.matcher(keyword).find();
				if (result) {
					// 包含中文
				}
				if (keyword != null && keyword.length() <= 40) {
					list.add(keyword.trim());
				}
				i++;
				if (i % 10000 == 0) {
					System.out.println("导入keyword " + i + " 次");
				}
			}
			System.out.println("导入keyword " + i + " 个");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw  new Exception();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (fis != null) {
					fis.close();
				}
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	@Override
	public void getDataToExport(String inFilePath, String outFilePath, String type) {
	/*	String outFileName = String.valueOf(System.currentTimeMillis() + ".txt");
		PrintWriter bw = null;
		try {
			FileOutputStream outfile = new FileOutputStream(outFilePath + outFileName);
			bw = new PrintWriter(new OutputStreamWriter(outfile, "utf-8"));
			long start = System.currentTimeMillis();
			System.out.println("start ...");
			
			System.out.println("end ..." + (System.currentTimeMillis() - start));
			bw.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				bw.flush();
				bw.close();
			}
		}*/
		try {
			/*
			 * 初始化流
			 */
			String outFileName = String.valueOf(System.currentTimeMillis() + ".txt");
			FileOutputStream outfile = new FileOutputStream(outFilePath + outFileName);
			this.bw = new PrintWriter(new OutputStreamWriter(outfile, "utf-8"));

			long start1 = System.currentTimeMillis();
			System.out.println("start ..." + start1);
			int j=0;
			for (String s1 : this.key) {
					//读取数据
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("key", s1);
					doFetechDataAndWrite(map, type);
					j++;
					if(j%10000==0) {
						bw.flush();
					}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	}

	@Override
	public void doWriteToFile(Map<String, Object> ids, Map<String, Object> srcMap, Object[] objects) {
		if(srcMap!=null && srcMap.get("result")!=null) {
			KeyWordEntity kw = (KeyWordEntity)srcMap.get("result");
			String keyword = kw.getKeyWord();
			List<NameSimilarityEntity> list = kw.getList();
			for(NameSimilarityEntity res : list) {
				bw.println(keyword+"|\t|"+res.getName() + "|\t|"+res.getSimilar());
			}
		}
		
	}

	@Override
	public void closeIO() {
		
		if(this.bw!=null) {
			bw.flush();
			bw.close();
		}
		
	}

}
