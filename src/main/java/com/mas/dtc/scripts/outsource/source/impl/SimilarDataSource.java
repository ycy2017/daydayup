package com.mas.dtc.scripts.outsource.source.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ctrip.search.dictionary.KeyWordEntity;
import com.ctrip.search.dictionary.NameSimilarityEntity;
import com.ctrip.search.dictionary.Similarity;
import com.mas.dtc.scripts.outsource.source.IDataSource;

public class SimilarDataSource implements IDataSource {

	Set<String> set;

	public static IDataSource getInstance(){
		return new SimilarDataSource();
	}
	
	public SimilarDataSource() {
		init();
	}

	/**
	 * 初始化
	 */
	private void init() {
		String file2 = "D:\\Users\\yincy\\Desktop\\src.txt";
		set = getName2(file2);
	}

	public Set<String> getName2(String fileDec) {
		File file = new File(fileDec);
		Set<String> set = new HashSet<String>();
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
				set.add(keyword.trim());
				i++;
				if (i % 10000 == 0) {
					System.out.println("导入name " + i + " 次");
				}

			}
			System.out.println("导入name " + i + " 个");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
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
		return set;
	}

	@Override
	public boolean checkCommand(String type) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Map<String, Object> getDataFromDataSource(Map<String, Object> ids, String type) {
		Object obj = ids.get("key");
		Map<String, Object> map = new HashMap<>();
		if (obj != null) {
			String name = String.valueOf(obj);

			KeyWordEntity kw = new KeyWordEntity();
			kw.setKeyWord(name);
			List<NameSimilarityEntity> nameSimilarList = new ArrayList<NameSimilarityEntity>();
			for (String s2 : this.set) {
				double similarity = Similarity.sim(name, s2);
				if (similarity >= 0.9) {
					NameSimilarityEntity nse = new NameSimilarityEntity();
					nse.setName(s2);
					nse.setSimilar(similarity);
					nameSimilarList.add(nse);
				}
			}
			kw.setList(nameSimilarList);
			map.put("result", kw);
		}
		return map;
	}

	@Override
	public boolean setDataSourceField(String sourceMetaStr) {
		// TODO Auto-generated method stub
		return true;
	}

	public void play() {
		List<String> list1 = null;
		List<KeyWordEntity> keywordList = null;
		Set<String> set2 = null;
		for (String s1 : list1) {
		}
	}

}
