package com.ctrip.search.dictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PipedWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.dbcp.BasicDataSource;

public class KeywordScript {

	private static BasicDataSource bs = null;

	static {
		bs = new BasicDataSource();
		bs.setDriverClassName("com.mysql.jdbc.Driver");
		bs.setUrl("jdbc:mysql://localhost:3306/htlsearchsrdb");
		bs.setUsername("root");
		bs.setPassword("");
		bs.setMaxActive(200);// 设置最大并发数
		bs.setInitialSize(30);// 数据库初始化时，创建的连接个数
		bs.setMinIdle(50);// 最小空闲连接数
		bs.setMaxIdle(200);// 数据库最大连接数
		bs.setMaxWait(1000);
		bs.setMinEvictableIdleTimeMillis(60 * 1000);// 空闲连接60秒中后释放
		bs.setTimeBetweenEvictionRunsMillis(5 * 60 * 1000);// 5分钟检测一次是否有死掉的线程
		bs.setTestOnBorrow(true);
	}

	public KeywordScript() {

	}

	public static void main(String[] args) {
		KeywordScript kw = new KeywordScript();
		String file2 = "d:\\Users\\yincy\\Desktop\\b\\0000000";
		Set<String> nameList = kw.getName2(file2);

		String file1 = "d:\\Users\\yincy\\Desktop\\b\\0000000";
		List<String> keywordList = kw.getKeyWord(file1);

		String outfile = "d:\\Users\\yincy\\Desktop\\b\\";
		/* List<KeyWordEntity> res = */
		kw.similar(outfile, keywordList, nameList);

		while (true) {

		}

	}

	/**
	 * 从数据库获取
	 * 
	 * @return
	 */
	public Set<String> getName() {
		Set<String> set = new HashSet<String>();
		try {
			Connection con = bs.getConnection();
			Statement statement = con.createStatement();
			// 要执行的SQL语句
			String sql = "select name from hotel_markland_longtail  ";
			ResultSet rs = statement.executeQuery(sql);
			int i = 0;
			while (rs.next()) {
				// 获取stuid这列数据
				String name = rs.getString("name");
				if (name != null) {
					set.add(name.trim());
				}
				// 输出结果

				i++;
				if (i % 10000 == 0)
					System.out.println("导入地标 " + i + " 次");
			}
			System.out.println("导入地标 " + set.size() + " 个");
			rs.close();
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return set;
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

	Pattern p1 = Pattern.compile(".*[a-zA-z].*");
	Pattern p2 = Pattern.compile("[\\u4E00-\\u9FBF]+");

	public List<String> getKeyWord(String fileDec) {
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

	public void similar(String outFile, List<String> keyWord, Set<String> set2) {
		// List<KeyWordEntity> keywordList = new ArrayList<KeyWordEntity>();
		String outFileName = String.valueOf(System.currentTimeMillis() + ".txt");
		PrintWriter bw = null;
		try {
			FileOutputStream outfile = new FileOutputStream(outFile + outFileName);
			bw = new PrintWriter(new OutputStreamWriter(outfile, "utf-8"));
			long start = System.currentTimeMillis();
			int i = 0 ;
			for (String s1 : keyWord) {
 				long start1 = System.currentTimeMillis();
				System.out.println("start ...");
				boolean flag = true;
				i++;
				for (String s2 : set2) {
					double similarity = Similarity.sim(s1, s2);
					if (similarity >= 0.9) {
//						flag = false;
						bw.println(s1 + "|\t|" + s2 + "|\t|" + similarity);
					}
				}
				System.out.println( s1 + " end ..." + (System.currentTimeMillis() - start1));
				/*if (flag){
					bw.println(s1+  "|\t|" + null + "|\t|" + 0);
				}*/
				if (i % 10000 == 0){
					bw.flush();
				}
			}
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
		}

	}



	// return keywordList;

	public void play() {
		List<String> list1 = null;
		List<KeyWordEntity> keywordList = null;
		Set<String> set2 = null;
		for(String s1:list1){
			KeyWordEntity kw = new KeyWordEntity();
			kw.setKeyWord(s1);
			List<NameSimilarityEntity> nameSimilarList = new ArrayList<NameSimilarityEntity>();
			for (String s2 : set2) {
				double similarity = Similarity.sim(s1, s2);
				if (similarity >= 0.9) {
					NameSimilarityEntity nse = new NameSimilarityEntity();
					nse.setName(s2);
					nse.setSimilar(similarity);
					nameSimilarList.add(nse);
				}
			}
			kw.setList(nameSimilarList);
			keywordList.add(kw);
		}
	}

	
}
/*
 * public void outPut( String outfile,List<KeyWordEntity> list){ Long fileName =
 * System.currentTimeMillis(); for(KeyWordEntity kwe:list){
 * List<NameSimilarityEntity> nameList = kwe.getList(); for(){
 * 
 * } }
 * 
 * }
 */
