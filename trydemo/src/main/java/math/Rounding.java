package math;

/**
 * 四舍五入
 * @author Mathsys
 *
 */
public class Rounding {

		public static void main(String[] args) {
			
			Object obj = new Object();
			
			double i = 2.47;
			//
			System.out.println(Math.floor(i));
			System.out.println(Math.ceil(i));
			System.out.println(Math.round(i));
			 i = -1.6;
			System.out.println(Math.floor(i));
			System.out.println(Math.ceil(i));
			System.out.println(Math.round(i));
			
			
			Integer i01 = 59;
			int i02 = 59;
			Integer i03 =Integer.valueOf(59);
			Integer i04 = new Integer(59);
			
			
			System.out.println(i01 == i02);
			System.out.println(i01 == i03);
			System.out.println(i02 == i04);
			System.out.println(i03 == i04);
			
		}
		
}
