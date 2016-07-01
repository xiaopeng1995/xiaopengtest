package com.company;


import com.company.UI.UIchou;
import com.company.UI.UIgamelis;
import com.company.UI.UImain;
import com.company.entity.User;
import com.company.entity.aa;
import com.company.mogon.MongoStorage;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bson.types.ObjectId;
import java.util.List;


public class MongoMain {

    public static void main(String[] args) throws Exception {
        MongoStorage mogo = new MongoStorage();
        PropertiesConfiguration mongoConfig;
        mongoConfig = new PropertiesConfiguration("src/com/company/Config/mongo.properties");
        mogo.init(mongoConfig);
        UImain uImain=new UImain();
        uImain.timer1(mogo);


    }
}
