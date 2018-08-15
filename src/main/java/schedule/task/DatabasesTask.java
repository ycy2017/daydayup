package schedule.task;

import java.util.Date;

import schedule.AbstractTask;



/**
 * ��ʱִ�����ݿ�Ų�����
 * @author Administrator
 *
 */
public class DatabasesTask extends AbstractTask{

	private volatile Date startDate = new Date();
	private volatile Date endDate = new Date();
	
	@Override
	public void excutorTask() {
		startDate = new Date(System.currentTimeMillis());
		System.out.println("start ... " + startDate);
		try {
			//����ִ��5��
			Thread.sleep(5000);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		endDate = new Date(System.currentTimeMillis());
		System.out.println("end ... " + endDate );
	}

	@Override
	public boolean isTimeOver() {
		Long currentDate = System.currentTimeMillis();
		Long cost = currentDate - this.endDate.getTime();
//		System.out.println("��ǰʱ��"+new Date(currentDate)+"���ѵ�ʱ��"+ cost);
		System.out.println("���ѵ�ʱ��"+ cost);
		//12��Сʱ
		Long time = 12 * 3600 * 1000L; 
		Long time1 = 5 * 1000L; 
		if(cost>time1){
			return true;
		}
		return false;
	}
	
	
	
}
