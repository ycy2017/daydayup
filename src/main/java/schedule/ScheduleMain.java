package schedule;

import schedule.task.DatabasesTask;

public class ScheduleMain {

	public static void main(String[] args) {
		
		AbstractTask [] atTaskArr = {new DatabasesTask()}; 
		TaskSchedule xc = new TaskSchedule(atTaskArr);
		new Thread(xc).start();
		
	}
	
}
