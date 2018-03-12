package trydemo.collections;

import java.util.ArrayList;
import java.util.List;

public class RemoveDemo {
	
	public static void main(String[] args) {
		
		List<String> list = new ArrayList<String>();
		list.add("demo");
		System.out.println(list);
		list.remove("demo");
		System.out.println(list);
		
		//
		List<Student> list2 = new ArrayList<Student>();
		list2.add(new Student("小明"));
		list2.add(new Student("小明"));
		System.out.println(list2.size()+""+list2);
		list2.remove(new Student("小明"));
		System.out.println(list2.size()+""+list2);
		
		
	}
}

class Student{
	private String name;
	
	public Student (String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	@Override
	public boolean equals(Object o){
		if(o == this){
			return true;
		}
		if (o instanceof Student){
			Student s = (Student)o;
			if(this.getName().equals(s.getName())){
				return true;
			}
		}
		return false;
	}
	
}
