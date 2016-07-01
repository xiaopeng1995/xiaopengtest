package com.company.UI;

import com.company.mogon.MongoStorage;
import com.company.work.Userwork;
import org.bson.Document;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by xiaopeng on 2016/6/28.
 */
public class UIchou {
    Userwork userwork=new Userwork();
    UIGameFunction uiGameFunction=new UIGameFunction();
    Cdk cdk=new Cdk();
    public void c(Document ds,MongoStorage mogo){
        System.out.println("\n欢迎来到抽奖系统");
        System.out.println("正在抽奖请稍后..");
        System.out.println("所需进度：20%..40%..60%..80%..100%");
        System.out.print("当前进度：");
        Timer timer = new Timer();
       UIgamelis gamelis=new UIgamelis();
        timer.schedule(new TimerTask() {
            int a=0;
            public void run() {
                a++;
                if (a<25) {
                    System.out.print("*");
                }
                if(a==25){

                    if(cdk.RandomNum()<9)
                    {
                        if(cdk.RandomNum()<3)
                        {
                            System.out.println("\n您获得10000点经验！");
                            int [] info=userwork.UserworkGrade(ds.getInteger("exp")+(10000),ds.getInteger("grade"));
                            ds.put("grade",info[0]);
                            ds.put("exp",info[1]);
                            mogo.UpdateUser(ds);
                        }
                        if(cdk.RandomNum()>=3&&cdk.RandomNum()<7)
                        {
                            System.out.println("\n您获得1000文钱！");
                            ds.put("gamemoney",ds.getInteger("gamemoney")+1000);
                            mogo.UpdateUser(ds);
                        }
                        if(cdk.RandomNum()=7)
                        {
                            System.out.println("\n您获得200金币！");
                            ds.put("gold",ds.getInteger("gold")+200);
                            mogo.UpdateUser(ds);
                        }
                        else
                        {
                            System.out.println("\n您获得50金币！");
                            ds.put("gold",ds.getInteger("gold")+50);
                            mogo.UpdateUser(ds);
                        }

                    }
                    else
                    {
                        System.out.println("\nVery lucky!");
                        System.out.println("中");
                        System.out.println("奖");
                        System.out.println("了");
                        System.out.println("！");
                        System.out.println("\n恭喜您获得100元移动充值卡");
                        System.out.println("卡号是："+cdk.getCdk());
                        System.out.println("密码是："+cdk.getCdkpwd());
                        System.out.println("|");
                        System.out.println("|");
                        System.out.println("|");
                        System.out.println("|");
                        System.out.println("|");
                    }
                    uiGameFunction.UIGameFunctionmain(mogo,ds);
                    timer.cancel();
                }
            }
        }, 0,100);// 设定指定的时间time,此处为2000毫秒


    }
}
