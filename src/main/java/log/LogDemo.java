package log;

import org.apache.log4j.Logger;

public class LogDemo {

	private static final Logger LOGGER = Logger.getLogger(LogDemo.class);
	
	/**
	 * 使用log4j
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		LOGGER.info("LIKE");
		LOGGER.debug("debug");
		LOGGER.error("error");
	}

}
