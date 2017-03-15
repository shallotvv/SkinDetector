package com.vvxc.skindetector.view.activity;

/**
 * Created by vvxc on 2017/3/10.
 */
public interface SignupView {
    void showSignupFail();
    void showDialog();
    void hideDialog();
    void showNameInvalid();
    void showEmailInvalid();
    void showTeleInvalid();
    void showPasswordInvalid();
    void showReEnterInvalid();
    void goNextContext();

}
