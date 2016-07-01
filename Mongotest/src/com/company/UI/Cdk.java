package com.company.UI; /**
 * Created by xiaopeng on 2016/6/28.
 */
/**
 * Created by xiaopeng on 2016/6/20.
 */
public class Cdk {
    public String getCdk(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String zimu = "";
        for (int i = 0; i < 4; i++) {
            char zimu1 = chars.charAt((int) (Math.random() * 26));

            zimu += zimu1 + "";
        }
        zimu += "-";
        for (int i = 0; i < 6; i++) {
            String q = (int) (Math.random() * 10) + "";
            zimu += q;
        }
        zimu += "-";
        for (int i = 0; i < 4; i++) {
            char zimu1 = chars.charAt((int) (Math.random() * 26));

            zimu += zimu1 + "";
        }
        zimu += "-";
        for (int i = 0; i < 6; i++) {
            String q = (int) (Math.random() * 10) + "";
            zimu += q;
        }
        return zimu;
    }
    public String getCdkpwd(){

        String zimu = "";
        for (int i = 0; i < 6; i++) {
            String q = (int) (Math.random() * 10) + "";
            zimu += q;
        }
        zimu += "";
        for (int i = 0; i < 6; i++) {
            String q = (int) (Math.random() * 10) + "";
            zimu += q;
        }
        return zimu;
    }
    public String getshuangseqiu(){
        String zimu="";
        /*红球*/
        for (int i = 0; i < 6; i++) {
            /* for (int j = 0; j < 1; j++) {
                 String q = (int) (Math.random() * 4) + "";
                 zimu += q;
             }*/
            for (int j = 0; j < 1; j++) {
                String q = (int) (Math.random() * 34) + "";
                zimu += q;
            }
            zimu += " ";

        }
          /*蓝球*/
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 1; j++) {
                String q = (int) (Math.random() * 2) + "";
                zimu += q;
            }
            for (int j = 0; j < 1; j++) {
                String q = (int) (Math.random() * 6) + "";
                zimu += q;
            }
            zimu += " ";
        }
        return zimu;
    }
    public int RandomNum(){
        return  (int) (Math.random() * 10);
    }
    public String getphone(){

        String zimu = "1";
        for (int i = 0; i < 1; i++) {
            int q = (int) (Math.random() * 10);
            if (q<5)
            {
                q=3;
            }
            else
            {
                q=8;
            }

            zimu += q+"";
        }
        for (int i = 0; i < 1; i++) {
            String q = (int) (Math.random() * 10) + "";
            zimu += q;
        }
        zimu += " ";
        for (int i = 0; i < 4; i++) {
            String q = (int) (Math.random() * 10) + "";
            zimu += q;
        }
        zimu += " ";
        for (int i = 0; i < 4; i++) {
            String q = (int) (Math.random() * 10) + "";
            zimu += q;
        }
        return zimu;
    }
}