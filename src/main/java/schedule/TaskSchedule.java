package schedule;

/**
 * 
 * @author Administrator
 *
 */
public class TaskSchedule  implements Runnable{

	AbstractTask [] atTaskArr ;
	
	public TaskSchedule(AbstractTask [] atTaskArr){
		this.atTaskArr = atTaskArr;
	}
	
	public void run() {
		for(AbstractTask at : atTaskArr){
		//����
			Thread th = new Thread(at);
			th.start();
		}
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (AbstractTask at : atTaskArr) {
				//��ȡִ������ı�ʶ
				if(at.isTimeOver()){
					at.wakeUp(true);
				}else{
					at.wakeUp(false);
				}
			}
			
		}
		
		
	}
	
}
