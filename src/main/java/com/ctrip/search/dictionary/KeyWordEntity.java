package com.ctrip.search.dictionary;


import java.util.List;

public class KeyWordEntity {
	private String keyWord;
	private List<NameSimilarityEntity> list;
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	public List<NameSimilarityEntity> getList() {
		return list;
	}
	public void setList(List<NameSimilarityEntity> list) {
		this.list = list;
	}
	
	
	
}
