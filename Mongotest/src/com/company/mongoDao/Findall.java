package com.company.mongoDao;

import com.company.UI.UIGameFunction;
import com.company.UI.UIgamelis;
import com.company.entity.User;
import com.company.mogon.MongoStorage;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Scanner;

/**
 * Created by xiaopeng on 2016/6/30.
 */
public class Findall {
    /*
    * findUser
    * */    UIgamelis uIgamelis=new UIgamelis();
            public User findUserByID(MongoStorage mogo)
            {
                List<User> xp=mogo.getusersname(new ObjectId("5774a29181e313a3986a47b7"));//findUserByID
                return xp.get(0);
            }
            public User findUserByNpwd(MongoStorage mogo)
            {
                /*****************Login*****************************/
                User user=new User();
                System.out.println("请输入用户名");
                Scanner name = new Scanner(System.in);
                String str = name.next();
                System.out.println("请输入密码");
                Scanner pwd1 = new Scanner(System.in);
                String pwd = pwd1.next();
                user.setName(str);
                user.setPwd(pwd);
                /*****************Login judge*****************************/
        /*
        * If  loser
        * */
                if (mogo.getuserbyNpwd(user).size()==0)
                {
                    System.out.println("密码错误！请重新选择登陆！");
                    uIgamelis.gamels(mogo);
                }
        /*
        * If succeed
        * */
                else
                {
                    user=mogo.getuserbyNpwd(user).get(0);//Find Userinfo
                    Document document=new Document();
                    document.put("_id",user.getId());
                    document.put("pwd",user.getPwd());
                    document.put("name",user.getName());
                    document.put("gold",user.getGold());
                    document.put("gamemoney",user.getGamemoney());
                    document.put("exp",user.getExp());
                    document.put("grade",user.getGrade());
                    UIGameFunction uiGameFunction=new UIGameFunction();
                    uiGameFunction.UIGameFunctionmain(mogo,document);
                }
                return user;
            }

    /*
    * findOthers
    * */
}
