package com.vvxc.skindetector.model;

import com.vvxc.skindetector.Bean.UserSignupBean;

/**
 * Created by vvxc on 2017/3/10.
 */
public class SignupModelImpl implements SignupModel {
    @Override
    public void postUserInfo(UserSignupBean user,OnPostCompleteListener onPostCompleteListener) {

        onPostCompleteListener.onSuccess();

    }
}
