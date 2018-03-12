package com.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MoneyDAO {

	
	public Integer getMoney(String account);
	/**
	 * 
	 */
	public void outMoney(@Param("money")Integer money,@Param("name")String name);
	public void inMoney(Integer money,String account);
	
	public void createAccount(Integer money,String user);
}
