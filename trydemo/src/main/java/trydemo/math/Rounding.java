package trydemo.math;

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
			
			//装箱使用的integer.valueof()
			Integer i01 = 59;
			int i02 = 59;
			
			Integer i03 =Integer.valueOf(59);
			Integer i04 = new Integer(59);
			Integer i05 =Integer.valueOf(59);
			//int 和 integer比较时,integer自动拆箱
			System.out.println(i01 == i02);
			System.out.println(i01 == i03);
			System.out.println(i01 == i04);
			//int 和 integer比较时,integer自动拆箱 true
			System.out.println(i02 == i04);
			System.out.println(i03 == i04);
			//integer.valueof() 获取,integer缓存数组中拿对象,对象从-128到127范围内的integer对象,超出则,新建
			System.out.println(i03 == i05);
			
			Integer.parseInt("1");
			
			//自动拆箱 invalue()
			int i06 = new Integer(2222);
			Integer i07 = new Integer(2222);
			//int 和 Integer比较时,Integer自动拆箱
			System.out.println(i06==i07);
		}
		
}
