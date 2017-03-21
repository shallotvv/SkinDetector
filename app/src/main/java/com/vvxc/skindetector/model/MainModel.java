package com.vvxc.skindetector.model;

import com.vvxc.skindetector.Bean.UserInfoBean;

/**
 * Created by vvxc on 2017/3/21.
 */
public interface MainModel {
    void postToken(String token,OnPostTokenCompleteListener listener);

    interface OnPostTokenCompleteListener{
        void onSuccess(UserInfoBean user);
        void onFail();
    }


}
