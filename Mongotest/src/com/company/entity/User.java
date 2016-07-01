package com.company.entity;

import org.bson.types.ObjectId;

/**
 * Created by xiaopeng on 2016/6/30.
 */
public class User {
    private ObjectId id;
    private String name;
    private String pwd;
    private int grade;
    private int exp;
    private int  gamemoney;
    private int gold;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getGamemoney() {
        return gamemoney;
    }

    public void setGamemoney(int gamemoney) {
        this.gamemoney = gamemoney;
    }
}
