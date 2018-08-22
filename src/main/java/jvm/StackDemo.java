package jvm;

import java.util.ArrayList;
import java.util.List;

public class StackDemo {

    public int getSum(int i) {

        return getSum(--i) + 1;
    }

    public static void main(String[] args) {
//        Exception in thread "main" java.lang.StackOverflowError
//        new StackDemo().getSum(100);

        //Exception in thread "main" java.lang.OutOfMemoryError: Java heap space
        List list = new ArrayList();
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while(true){
            Student student = new Student();
            student.name  = "小小";
            list.add(student);
        }

    }



}

 class Student{
    public String name ;
     public  String inst;
}