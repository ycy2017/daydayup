package test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.service.MoneyService;
import com.vo.UserEntity;

public class TestSpring {
		
	@Test
	public void test(){
		ApplicationContext  ac= new ClassPathXmlApplicationContext("applicationContext.xml","springmvc.xml");
		MoneyService ms = (MoneyService) ac.getBean("moneyServiceImpl");
		try {
			ms.transfer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	/**
	 * spring的scope默认使用单利
	 * prototype指每次获取spring 会实例化一个对象
	 */
	@Test
	public void test2(){
		
		ClassPathXmlApplicationContext  ac= new ClassPathXmlApplicationContext("applicationContext.xml","springmvc.xml");
		UserEntity ms1 = (UserEntity) ac.getBean("UserEntity");
		UserEntity ms2 = (UserEntity) ac.getBean("UserEntity");
	
//		ac.destroy();
		ac.close();
	}
	
	
}
