package com.vvxc.skindetector.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Bean.UserLoginBean;
import com.vvxc.skindetector.model.LoginModel;
import com.vvxc.skindetector.model.LoginModelImpl;
import com.vvxc.skindetector.model.UserSharePreference;
import com.vvxc.skindetector.util.JudgeUserInfoUtil;
import com.vvxc.skindetector.view.activity.LoginActivity;
import com.vvxc.skindetector.view.activity.LoginView;

/**
 * Created by vvxc on 2017/3/10.
 */
public class LoginPresenter extends BasePresenter<LoginView>{

    private LoginModel loginModel=new LoginModelImpl();

    public void login(UserLoginBean user){
        JudgeUserInfoUtil juUtil=new JudgeUserInfoUtil();
        int judgeType=juUtil.judge(user);
        if (JudgeUserInfoUtil.TELEPHONE_INVALID==judgeType){
            getView().showTeleInvaid();
            return;
        }
        if (JudgeUserInfoUtil.PASSWORD_INVALID==judgeType){
            getView().showPassWordInvaid();
            return;
        }


        getView().showLoginDialog();
        loginModel.postUserInfo(user, new LoginModel.OnPostCompleteListener() {
            @Override
            public void onPostSuccess(UserInfoBean user) {
                getView().hideLoginDialog();
                LoginActivity activity= (LoginActivity) getView();
                SharedPreferences sharedPreferences=activity.getSharedPreferences("user", Context.MODE_PRIVATE);
                new UserSharePreference().saveToken(user.getToken(),sharedPreferences);
                //结束登录页面，并标记主界面的登录状态为true
                getView().goNextContext(user);
            }

            @Override
            public void onPostFail() {
                getView().hideLoginDialog();
                getView().showLoginFail();
            }
        });
    }
}
