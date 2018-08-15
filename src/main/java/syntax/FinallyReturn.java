package syntax;

public class FinallyReturn {

	public static void main(String[] args) {
		

		FinallyReturn fr = new FinallyReturn();
		
		System.out.println(fr.getInt());
		System.out.println(fr.getString());
		System.out.println(fr.getStudent());
	}

	
	
	/**
	 * 返回的是基本类型
	 * @return
	 */
	public int getInt(){
		int i = 0 ;
		try{
			return i;
		}catch(Exception e){
			return i;
		}finally{
			i=1;
		}
	}
	
	/**
	 * 返回的是基本类型
	 * @return
	 */
	public String getString(){
		String  i = "a" ;
		try{
			return i;
		}catch(Exception e){
			return i;
		}finally{
			i = "Finally a";
		}
	}
	
	/**
	 * 返回的是引用类型
	 * @return
	 */
	public Student getStudent(){
		Student s = new Student();
		s.setName("СС");
		try{
			return s;
		}catch(Exception e){
			return s;
		}finally{
			s.setName("Finally CC");;
		}
	}
	
}

class Student{
	String name;
	protected int age;
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public String toString(){
		return name + ":" + age;
	}
	
}