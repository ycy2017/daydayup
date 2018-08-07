package db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.dbcp.BasicDataSource;

import com.mysql.jdbc.Statement;


/**
 * dbcp
 * 
 * @author Administrator
 *
 */
public class DataSourceDemo2 {

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
	    
	    //conn 使用失效, 如果超过这个时间,connection报错
	    dataSource.setRemoveAbandoned(true);
	    dataSource.setRemoveAbandonedTimeout(1);
	    dataSource.setLogAbandoned(true);  
//	    如果开启"removeAbandoned",那么连接在被认为泄露时可能被池回收. 这个机制在
//	    (getNumIdle() < 2) and (getNumActive() > getMaxActive() - 3)时被触发
	    
	}

	

	public static void main(String[] args) {
		
		//调用5次
		for(int i =0; i<6;i++){
			Thread t1 = new Thread(){
				public void run(){
					getRes();
				}
			};
			t1.start();
		}
			
		while(true){
			try {
				Thread.sleep(1000);
				System.out.println("================================");
				System.out.println( " 移除时间 "+ dataSource.getRemoveAbandonedTimeout());	
				System.out.println("空闲数量: "   +   dataSource.getNumIdle());
				System.out.println("活跃线程 : "   +  dataSource.getNumActive());
				System.out.println("最大连接线程 : "   +  dataSource.getMaxActive());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void getRes(){
		Connection con;
		try {
			con = dataSource.getConnection();
			String sql = " select curdate() ";
			
		
			try {
				Long start = System.currentTimeMillis();
				//模拟从建立con 到 使用con的时间
				Thread.sleep(1000);
				System.out.println(System.currentTimeMillis() - start);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			con.close();
			java.sql.Statement st = con.createStatement();
			ResultSet rst= st.executeQuery(sql);
			while(rst.next()){
				System.out.println(rst.getString(1));
			}
			while(true){
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	
}
