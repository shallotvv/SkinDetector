package com.vvxc.skindetector.view.activity;

/**
 * Created by vvxc on 2017/3/10.
 */
public interface LoginView {
    /**
     * 所有LoginActivity需要进行的ui操作
     */
    void showLoginDialog();
    void hideLoginDialog();
    void goNextContext();
    void showLoginFail();
    void showTeleInvaid();
    void showPassWordInvaid();
}
