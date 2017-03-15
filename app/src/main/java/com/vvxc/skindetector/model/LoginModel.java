package com.vvxc.skindetector.model;

import com.vvxc.skindetector.Bean.UserLoginBean;

/**
 * Created by vvxc on 2017/3/10.
 */
public interface LoginModel {
    /**
     * 发送post请求到服务端验证用户登录信息
     * @param onPostCompleteListener 制定post请求完成时调用的方法
     * @param user 用户d登录信息
     */
    void postUserInfo(UserLoginBean user,OnPostCompleteListener onPostCompleteListener);

    interface  OnPostCompleteListener{
        void onPostSuccess();
        void onPostFail();
    }
}
