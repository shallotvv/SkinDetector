package com.vvxc.skindetector;

import android.app.Application;

import com.vvxc.skindetector.Bean.UserInfoBean;

/**
 * Created by vvxc on 2017/3/27.
 */
public class MyApplication extends Application {

    UserInfoBean userInfoBean;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public UserInfoBean getUserInfo() {
        return userInfoBean;
    }

    public void setUserInfo(UserInfoBean user) {
        this.userInfoBean = user;
    }


}
