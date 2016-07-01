package com.company.UI;

import com.company.entity.User;
import com.company.mogon.MongoStorage;
import org.bson.Document;

import java.util.Scanner;

/**
 * Created by xiaopeng on 2016/7/1.
 */
public class UIGameFunction {
    UIgamelis uIgamelis=new UIgamelis();
    public void UIGameFunctionmain(MongoStorage mogo,Document ds)
    {
        ds=mogo.Userinfo(ds);
        System.out.println("欢迎您"+ds.getString("name")+"来到魔幻巨作~~幻想世界~~");
                /*User Iofor*/
        System.out.println("您的信息如下：");
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"
                + "您当前等级：" +ds.getInteger("grade")+"级\t"
                + "您当前的经验为"+ds.getInteger("exp")+"点，升级需要"+ds.getInteger("grade")*76*ds.getInteger("grade")+"点\t"
                + "您当前游戏币："+ds.getInteger("gamemoney")+ "文\t"
                + "您的金币"+ds.getInteger("gold") +"个\n\n"
                + "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                /*Geme Function Choose*/
        System.out.println("已经准备好的游戏功能有：");
        System.out.println("1：野外刷怪\t\t2：副本~妖魔塔~\t3:每日一抽（有机会赢取100元移动手机充值卡)\t4：更多功能...（请期待）");
        System.out.println("请输入序号开始游戏");
        Scanner s = new Scanner(System.in);
        String GemeFunctionChoose = s.next();
        if(GemeFunctionChoose.equals("1"))//1：野外刷怪
        {
            UImonster uImonster=new UImonster();
            uImonster.show(mogo,ds);
        }
        else if (GemeFunctionChoose.equals("2"))//2：副本~妖魔塔~
        {
            System.out.println("功能正在开发中！请重新选择哦");
            UIGameFunctionmain(mogo,ds);
        }
        else if (GemeFunctionChoose.equals("3"))//3:每日一抽
        {
            UIchou uIchou=new UIchou();
            uIchou.c(ds,mogo);
        }
        else {
            System.out.println("输入错误请重新选择");
            UIGameFunctionmain(mogo,ds);
        }
    }
}
