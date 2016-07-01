package com.company.mongoDao;

import com.company.UI.UIGameFunction;
import com.company.UI.UIgamelis;
import com.company.entity.User;
import com.company.mogon.MongoStorage;
import org.bson.Document;

import java.util.Scanner;

/**
 * Created by xiaopeng on 2016/6/30.
 */
public class Addall {
    MongoStorage mogo = new MongoStorage();
    /*
    *addUser
     */
    public User addUser(MongoStorage mogo){
        User user=new User();
        System.out.println("请输入需要注册的用户名");
        Scanner name = new Scanner(System.in);
        String str = name.next();
        if (mogo.getuserbyName(str))
        {
            System.out.println("账户存在，请重新注册");
            addUser(mogo);
        }
        System.out.println("请输入密码");
        Scanner pwd1 = new Scanner(System.in);
        String pwd = pwd1.next();
        System.out.println("请重复密码");
        Scanner pwd2 = new Scanner(System.in);
        String qpwd = pwd2.next();
        if (!pwd.equals(qpwd))
        {
            System.out.println("两次密码不一样，请重新注册");
            addUser(mogo);
        }

       else
        {
            user.setName(str);
            user.setPwd(pwd);
            mogo.addaa(user);//Add Succeed！
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~注册成功！");
            UIgamelis uIgamelis=new UIgamelis();
            uIgamelis.gamels(mogo);
        }
        return user;
    }
     /*
    *addOthers
     */
}
