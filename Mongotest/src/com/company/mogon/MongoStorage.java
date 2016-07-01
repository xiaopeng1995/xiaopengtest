package com.company.mogon;

import com.company.entity.*;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import org.apache.commons.configuration.AbstractConfiguration;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.ascending;

/**
 * Created by xiaopeng on 2016/6/29.
 */
public class MongoStorage {
    protected MongoClient client;
    protected MongoDatabase database;
    public void init(AbstractConfiguration config) {
        // MongoClient
        List<ServerAddress> addresses = parseAddresses(config.getString("mongo.address"));
       List<MongoCredential> credentials = parseCredentials(
                config.getString("mongo.userName"),
                "admin",
                config.getString("mongo.password"));
        if (addresses.size() == 1) {
            this.client = new MongoClient(addresses.get(0), credentials);
        } else {
            this.client = new MongoClient(addresses, credentials);
        }
        this.database = this.client.getDatabase(config.getString("mongo.database"));

        // indexes
        this.database.getCollection("User").createIndex(ascending("name"), new IndexOptions().unique(true));
    }
    public void destroy() {
        if (this.client != null) this.client.close();
    }

    private ServerAddress parseAddress(String address) {
        int idx = address.indexOf(':');
        return (idx == -1) ?
                new ServerAddress(address) :
                new ServerAddress(address.substring(0, idx), Integer.parseInt(address.substring(idx + 1)));
    }
    private List<ServerAddress> parseAddresses(String addresses) {
        List<ServerAddress> result = new ArrayList<>();
        String[] addrs = addresses.split(" *, *");
        for (String addr : addrs) {
            result.add(parseAddress(addr));
        }
        return result;
    }
    private List<MongoCredential> parseCredentials(String userName, String database, String password) {
        List<MongoCredential> result = new ArrayList<>();
        result.add(MongoCredential.createCredential(userName, database, password.toCharArray()));
        return result;
    }
/*查询*/
    public List<User> getusersname(ObjectId productId) {
        List<User> xp = new ArrayList<>();
        this.database.getCollection("User")
                .find(eq("_id", productId))
                .forEach((Consumer<Document>) ds ->
                        xp.add(findUser(ds)));
        return xp;
    }
    protected User findUser(Document ds) {
        User user=new User();
        user.setId(ds.getObjectId("_id"));
        user.setName(ds.getString("name"));
        user.setPwd(ds.getString("name"));
        user.setGrade(ds.getInteger("grade"));
        user.setExp(ds.getInteger("exp"));
        user.setGamemoney(ds.getInteger("gamemoney"));
        user.setGold(ds.getInteger("gold"));
        return  user;
    }
    /**Username是否存在*/
    public Boolean getuserbyName(String name) {
        Document d = this.database.getCollection("User")
                .find(eq("name", name)) .projection(include("_id")).first();
    return d!=null;
    }
    /*Userinfo   by  id*/
    public Document Userinfo(Document id) {
        //.projection(include("_id"))获取_id这个字段
        Document d = this.database.getCollection("User")
                .find(eq("_id", id.getObjectId("_id"))) .first();
        return d;
    }
    /**UserNpwd是否正确*/
    public List<User> getuserbyNpwd(User user) {
        PwdMd5 pwdMd5=new PwdMd5();
        List<User> xp = new ArrayList<>();
        BasicDBObject query = new BasicDBObject();
        query.put("name",user.getName());
        query.put("pwd",pwdMd5.toMD5(user.getPwd()));//给密码MD5加密
         database.getCollection("User").find(query).forEach((Consumer<Document>) ds ->
                xp.add(findUser(ds)));
        return xp;
    }
    /**增加User*/
    public User addaa(User user)
    {
        PwdMd5 pwdMd5=new PwdMd5();
        Document d =new Document();;
        d.append("name",user.getName());
        d.append("pwd",pwdMd5.toMD5(user.getPwd()));//给密码MD5加密
        d.append("grade",1);
        d.append("exp",0);
        d.append("gamemoney",0);
        d.append("gold",100);
        d.append("insert_at", new Date());
        this.database.getCollection("User").insertOne(d);
        user.setId(d.getObjectId("_id"));
        return user;
    }
    /*修改用户*/
    public  boolean UpdateUser(Document d)
    {
        PwdMd5 pwdMd5=new PwdMd5();
        d.append("pwd",d.getString("pwd"));//给密码MD5加密
        d.append("updated_at", new Date());
       return this.database.getCollection("User").updateOne(eq("_id",d.getObjectId("_id")),new Document().append("$set",d)).getMatchedCount()>0;
    }
}
