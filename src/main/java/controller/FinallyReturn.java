package controller;

public class FinallyReturn {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		FinallyReturn fr = new FinallyReturn();
		
		System.out.println(fr.getInt());
		System.out.println(fr.getStudent());
	}

	
	
	/**
	 * 基本类型
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
	 * 引用类型
	 * @return
	 */
	public Student getStudent(){
		Student s = new Student();
		s.setName("小小");
		try{
			return s;
		}catch(Exception e){
			return s;
		}finally{
			s.setName("被我Finally修改了...");;
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