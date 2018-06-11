package com;

public class Student {

	String name;
	
	bag bag;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		String a  = new String ("a") ;
		String b  = new String ("a") ;
		String c  ="a" ;
		System.out.println(b == a);
		System.out.println(b == c);
		System.out.println(b.equals(c));
//		System.out.println(b.vu);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bag == null) ? 0 : bag.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (bag == null) {
			if (other.bag != null)
				return false;
		} else if (!bag.equals(other.bag))
			return false;
		
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		
		return true;
	}

	

	
	
}



class bag{
	
}
