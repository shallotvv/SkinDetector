package com.vvxc.skindetector;

/**
 * Created by vvxc on 2017/3/10.
 */
public class Constants {
    public static final String BaseUrl = "http://192.168.191.1:8080/SkinDectector/";

    public static final String RegisterUrl = "account/register";
    public static final String LoginUrl =  "user";
    public static final String UserInfoUrl =  "/users/";
    public static final String UpdateUserInfoUrl =  "/users/";
    public static final String UpLoadSkinDateUrl =  "/skindata/";
    public static final String DownLoadSkinDateUrl =  "/skindata/";

    public static  final int DENY=0;
    public static  final int NOMAL=1;
    public static  final int OIL=2;
    public static  final int DRY=3;
    public static  final int SENSTIVE=4;
    public static  final int COMBINATION=5;

    public static  final int BOY=0;
    public static  final int GIRL=1;


    public static  final String TIP_COLD ="滋润保湿,皮肤容易缺水,用保湿型霜类化妆品,使用润唇膏.";
    public static  final String TIP_MIDDLE ="控油化妆,建议使用露质面霜打底,水质无油粉底霜,透明粉饼,粉质胭脂.";
}
