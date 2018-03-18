package trydemo.system;

public class SystemDemo {

	public static void main(String[] args) {
		
		System.out.println(System.getProperty("sun.boot.class.path"));
		System.out.println(System.getProperty("java.ext.dirs"));
		System.out.println(System.getProperty("java.class.path"));
		
		
		 //获取系统/应用类加载器  
        ClassLoader appClassLoader = ClassLoader.getSystemClassLoader();  
        System.out.println("系统/应用类加载器：" + appClassLoader);  
        //获取系统/应用类加载器的父类加载器，得到扩展类加载器  
        ClassLoader extcClassLoader = appClassLoader.getParent();  
        System.out.println("扩展类加载器" + extcClassLoader);  
        System.out.println("扩展类加载器的加载路径：" + System.getProperty("java.ext.dirs"));  
        //获取扩展类加载器的父加载器，但因根类加载器并不是用Java实现的所以不能获取  
        System.out.println("扩展类的父类加载器：" + extcClassLoader.getParent());
		
	}
	
}
