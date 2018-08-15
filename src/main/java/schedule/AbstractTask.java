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
				//ʱ��û��,����
			}
			if (getToken()) {
				// ����ֻ��һ��,ͬһʱ��ֻ����һ������ִ��
				try {
					excutorTask();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					// �ͷ�����
					realeseToken();
					//��������ѹر�
					wakeUp(false);
				}
			}
			
			
		}
	}

	public abstract void excutorTask();

	public boolean getwakeStatus() {
		return wakeStatus.get();
	}
	/**
	 * ���� true
	 * @param isvalid
	 */
	public void wakeUp(boolean isvalid) {
		this.wakeStatus.set(isvalid);
	}

	public boolean getToken() {
		return token.getAndSet(false);
	}

	public void realeseToken() {
		token.set(true);
	}
	
	public  abstract boolean isTimeOver();
	
}
