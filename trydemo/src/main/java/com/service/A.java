package com.service;

import com.vo.UserEntity;

/**
 * 默认的修饰权限比protected要大
 * @author Mathsys
 *
 */
public class A extends UserEntity{
	
	public void doWork(){
		
		int age = super.age;
		/*
		 * protected 只能在本包中使用
		 */
//		String name = super.name;
		
	}
	
	
}
