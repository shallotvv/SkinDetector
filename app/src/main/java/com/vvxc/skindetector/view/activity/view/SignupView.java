package com.vvxc.skindetector.view.activity.view;

/**
 * Created by vvxc on 2017/3/10.
 */
public interface SignupView {
    //state:1,成功。 2，出错，3已经存在用户
    void showSignupFail(String state);
    void showDialog();
    void hideDialog();
    void showNameInvalid();
    void showEmailInvalid();
    void showTeleInvalid();
    void showPasswordInvalid();
    void showReEnterInvalid();
    void goNextContext();

}
