package com.company.UI;

import com.company.mogon.MongoStorage;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xiaopeng on 2016/6/30.
 */
public class UImain {
    public  void timer1(MongoStorage mogo) {
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~欢迎来到梦想世界~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("游戏系统加载中..");
        System.out.println("所需进度：20%..40%..60%..80%..100%");
        System.out.print("当前进度：");
        Timer timer = new Timer();
        UIgamelis gamelis=new UIgamelis();
        timer.schedule(new TimerTask() {
            int a=0;
            public void run(){
                a++;
                if (a<25) {
                    System.out.print("*");
                }
                if(a==25){
                    timer.cancel();
                    gamelis.gamels(mogo);
                }
            }
        }, 0,100);// 设定指定的时间time,此处为2000毫秒
    }
}
