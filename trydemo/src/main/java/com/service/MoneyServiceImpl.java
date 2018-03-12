package com.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.dao.MoneyDAO;

@Service(value = "moneyServiceImpl")
public class MoneyServiceImpl implements MoneyService {

	@Resource
	MoneyDAO dao;
	
	
	/*
	 * @Transactional 的事务开启 ，或者是基于接口的 或者是基于类的代理被创建。所以在同一个类中一个方法调用另一个方法有事务的方法，事务是不会起作用的。(non-Javadoc)
	 * @see com.service.MoneyService#doWork()
	 */
	public void doWork(){
		
	try{
		System.out.println(transfer());
	}catch(Exception e){
		
	}
	
	}
	
	/**
	 * 转账业务使用事务来做
	 * @return
	 */
	//开启一个事务
	//设置事务的传播特性
	//RollbackFor 回滚
	@Transactional(propagation=Propagation.REQUIRED)
	public boolean transfer() {
		boolean flag = false;
		System.out.println(dao.getMoney("A"));
		System.out.println(dao.getMoney("B"));
		//开始转
		dao.outMoney(10, "A");
		
		
		try{
			int i = 1/0;
		}catch(Exception e){
			System.out.println("RuntimeException");
			throw new RuntimeException();
		}
		
		System.out.println(dao.getMoney("A"));
		System.out.println(dao.getMoney("B"));
		
		flag = true;
		return flag;
	}
	
}
