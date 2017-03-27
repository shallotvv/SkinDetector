package com.vvxc.skindetector.model;

import com.vvxc.skindetector.Bean.UserSignupBean;

/**
 * Created by vvxc on 2017/3/10.
 */
public interface SignupModel {

    /**
     * 提交用户注册的表单
     * @param user
     * @param onPostCompleteListener
     */
    void postUserInfo(UserSignupBean user,OnPostCompleteListener onPostCompleteListener);

    interface OnPostCompleteListener{
        void onSuccess(String token);
        void onFail(String state);
    }
}
