package com.vvxc.skindetector.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.vvxc.skindetector.Bean.UserInfoBean;
import com.vvxc.skindetector.Bean.UserLoginBean;
import com.vvxc.skindetector.MyApplication;
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
        //如果填写格式不对，则让用户重新填写
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
                if (isViewAttached()){
                    getView().hideLoginDialog();
                    LoginActivity activity= (LoginActivity) getView();
                    SharedPreferences sharedPreferences=activity.getSharedPreferences("user", Context.MODE_PRIVATE);
                    new UserSharePreference().saveToken(user.getToken(),sharedPreferences);
                    //结束登录页面，并标记主界面的登录状态为true
                    MyApplication application= (MyApplication) ((LoginActivity) getView()).getApplication();
                    application.setUserInfo(user);
                    getView().goNextContext(user);
                }
            }

            @Override
            public void onPostFail() {
                if (isViewAttached()){
                    getView().hideLoginDialog();
                    getView().showLoginFail();
                }
            }
        });
    }
}
