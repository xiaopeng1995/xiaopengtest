package com.company.UI;

import com.company.entity.User;
import com.company.mogon.MongoStorage;
import com.company.mongoDao.Addall;
import com.company.mongoDao.Findall;

import java.util.Scanner;

/**
 * Created by xiaopeng on 2016/6/28.
 */
public class UIgamelis {
    public void Endshow(MongoStorage mogo)
    {
        System.out.println("是否退出？输入0退出其他重新开始");
        Scanner s = new Scanner(System.in);
        String gemeid = s.next();
        if(gemeid.equals("0"))
        System.out.println("您已经退出，欢迎下次光临！");
        else
            gamels(mogo);

    }
    public void gamels(MongoStorage mogo) {
        UIchou uic=new UIchou();
        User user=new User();
        System.out.println("\n游戏开始");
        /*****************Geme Main Choose judge*****************************/
        System.out.println("1.登陆\t2.注册\t3找回密码（功能待完善）");
        System.out.println("请输入序号开始游戏");
        Scanner scanner = new Scanner(System.in);
        String GemeMainChoose= scanner.next();
        if(GemeMainChoose.equals("1"))
        {
            Findall f=new Findall();
            f.findUserByNpwd(mogo);
        }
        else if (GemeMainChoose.equals("2"))
        {
            Addall addall=new Addall();
            addall.addUser(mogo);
        }
        else if (GemeMainChoose.equals("3"))
        {
            System.out.println("功能正在完善");
            Endshow(mogo);
        }
        else
        {
            System.out.println("输入错误");
            Endshow(mogo);
        }

    }
}
