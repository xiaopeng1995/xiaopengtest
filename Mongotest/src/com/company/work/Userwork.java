package com.company.work;

/**
 * Created by xiaopeng on 2016/7/1.
 */
public class Userwork {
    /*
    *
    * 等级算法
    * */
    public int[] UserworkGrade(int exp,int grade)
    {
        int[] info=new int[2];
        while (exp >= (grade*76*grade)) {
                exp = exp - (grade*76*grade);
                grade++;
        }
        info[0]=grade;
        info[1]=exp;
        return  info;
    }

}
