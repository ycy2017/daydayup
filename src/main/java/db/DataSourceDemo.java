package db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;


/**
 * dbcp
 * 
 * @author Administrator
 *
 */
public class DataSourceDemo {

	static BasicDataSource dataSource = new BasicDataSource();
	
	static {
		  //2.为数据源实例指定必须的属性  
	    dataSource.setUsername("root");  
	    dataSource.setPassword("123123");  
	    dataSource.setUrl("jdbc:mysql://localhost:3306/ycy");  
	    dataSource.setDriverClassName("com.mysql.jdbc.Driver");  
	     //3。指定数据源的一些可选的属性  
	    //1)指定数据库连接池中初始化连接数的个数  
	    dataSource.setInitialSize(5);  
	    //2)指定最大的连接数:同一时刻同时向数据库申请的连接数  
	    //最大空闲数，放洪峰过后，连接池中的连接过多，  
	    dataSource.setMaxActive(5);  
	    //3)指定最小连接数:数据库空闲状态下所需要保留的最小连接数  
	    //防止当洪峰到来时，再次申请连接引起的性能开销；  
	    dataSource.setMinIdle(2);  
	    //4)最长等待时间:等待数据库连接的最长时间，单位为毫秒，超出将抛出异常  
	    dataSource.setMaxWait(1000*5); 
	}

	

	public static void main(String[] args) {
		
		/*
		 * 测试当最大是5个连接数时,又不释放连接时,而且最大等待时间是5s时, dbcp的异常情况
		 */
		for(int i=0;i<100;i++){
			System.out.println(i);
			getConnection();
		}
		
	}

	public static Connection getConnection(){
		Connection con;
		try {
			con = dataSource.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return con;
	}
	
	
	
}
