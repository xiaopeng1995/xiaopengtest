package com.youneek.studenttest;

import com.youneek.bean.Student;

/**
 * Created by 肖鹏 on 2016/6/13.
 */
public class test {
    public static void  main(String[] args){
        Student s=new Student();
        s.setStudentName("Tom");
        s.setStudentClass("No1");
        Student s1=new Student();
        s1.setStudentName("xiaopeng1");
        s1.setStudentClass("1402");
        System.out.print(s.getStudentName()+s1.getStudentName());
    }
}
