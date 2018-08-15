package schedule;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 
 * @author Administrator
 *
 */
public abstract class AbstractTask implements Runnable {

	/**
	 * ���б�ʶ
	 */
	protected AtomicBoolean wakeStatus = new AtomicBoolean(false);

	/**
	 * ���б�ʶ
	 */
	protected AtomicBoolean token = new AtomicBoolean(true);

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void run() {
		while (true) {
			while (!getwakeStatus()) {
				//自旋
			}
			if (getToken()) {
				// 获取到令牌之后进来
				try {
					excutorTask();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// 释放令牌
					realeseToken();
					//睡
					sleep();
				}
			}
			
			
		}
	}

	public abstract void excutorTask();

	public boolean getwakeStatus() {
		return wakeStatus.get();
	}
	/**
	 *  唤醒
	 * @param isvalid
	 */
	public void wakeUp() {
		this.wakeStatus.set(true);
	}
	/**
	 *  睡
	 * @param isvalid
	 */
	public void sleep() {
		this.wakeStatus.set(false);
	}
	

	public boolean getToken() {
		return token.getAndSet(false);
	}

	public void realeseToken() {
		token.set(true);
	}
	
	public  abstract boolean isTimeOver();
	
}
