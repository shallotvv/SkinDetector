package com.vvxc.skindetector.model;

/**
 * Created by vvxc on 2017/3/27.
 */
public interface PersonalModel {
    void logout(String token,OnLogoutListener listener);

    interface OnLogoutListener{
        void onSuccess();
        void onFail();
    }
}
